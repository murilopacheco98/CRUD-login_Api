package com.growdev.murilo.recados.service;

import com.growdev.murilo.recados.dto.ResetPasswordDTO;
import com.growdev.murilo.recados.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.growdev.murilo.recados.dto.SignInDto;
import com.growdev.murilo.recados.dto.SignUpDto;
import com.growdev.murilo.recados.entities.AuthToken;
import com.growdev.murilo.recados.entities.User;
import com.growdev.murilo.recados.exceptions.customExceptions.AutheticationFailExeception;
import com.growdev.murilo.recados.exceptions.customExceptions.BadRequestException;
import com.growdev.murilo.recados.exceptions.customExceptions.NotFoundException;
import com.growdev.murilo.recados.repository.UserRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthService authService;
    @Value("${spring.mail.password}")
    private String passwordEmail;

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void signUp(SignUpDto signUpDto) throws NoSuchAlgorithmException, MessagingException {
//        Optional<User> userExiste = userRepository.findByEmail(signUpDto.getEmail());
//        if (userExiste.isPresent()) throw new BadRequestException("Este Email já está cadastrado.");

        String encryptedPassword = hashPassword(signUpDto.getPassword());
        User user = new User(signUpDto.getEmail(), encryptedPassword,
                signUpDto.getName(), String.valueOf(UUID.randomUUID()));

        final AuthToken authenticationToken = new AuthToken(user);
        String encryptedToken = hashPassword(authenticationToken.getToken());

        user.setAuthToken(encryptedToken);
        userRepository.save(user);
        authService.saveConfirmationToken(authenticationToken);
        JavaMailSenderImpl mailSender = this.getJavaMailSender();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject("Confirmação de e-mail.");
        helper.setFrom("murilo.bot100@gmail.com");
        helper.setTo(signUpDto.getEmail());

        boolean html = true;
        helper.setText("<p>Olá,</p>"
                        + "<p>Você cadastrou em nosso site.</p>"
                        + "<br>"
                        + "<p>Para confirmar seu e-mail clique no link: <a href=\"/confirm-account/" + user.getCheckerCode() + "\">Confirmar e-mail</a></p>"
                        + "<br>"
                        + "<p>Ignore this email if you do remember your password, or you have not made the request.</p>"
                , html);

        mailSender.send(message);
    }

    public UserDTO signIn(SignInDto signInDto) throws NoSuchAlgorithmException {
        User user = userRepository.findByEmail(signInDto.getEmail())
                .orElseThrow(() -> new NotFoundException("E-mail inválido."));

        if (!user.getPassword().equals(hashPassword(signInDto.getPassword())))
            throw new BadRequestException("Senha inválida.");

        AuthToken token = authService.getTokenByUser(user);
        if (Objects.isNull(token)) throw new NotFoundException("Token não encontrado.");

        return new UserDTO(user);
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();

        return DatatypeConverter
                .printHexBinary(digest).toUpperCase();
    }

    public void confirmEmail(String checkerCode) {
        User user = userRepository.findByCheckerCode(checkerCode)
                .orElseThrow(() -> new NotFoundException("usuário não encontrado"));
        user.setEnable(true);
        userRepository.save(user);
    }

    public void resendConfirmEmail(String email) throws MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        JavaMailSenderImpl mailSender = this.getJavaMailSender();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject("Comfirmação de e-mail.");
        helper.setFrom("murilo.bot100@gmail.com");
        helper.setTo(email);

        boolean html = true;
        helper.setText("<p>Olá,</p>"
                        + "<p>Você cadastrou em nosso site.</p>"
                        + "<br>"
                        + "<p>Para confirmar seu e-mail clique no link: <a href=\"http://localhost:3000/confirm-account/" + user.getCheckerCode() + "\">Confirmar e-mail</a></p>"
                        + "<br>"
                        + "<p>Ignore this email if you do remember your password, or you have not made the request.</p>"
                , html);

        mailSender.send(message);
    }

    public void resetPassword(ResetPasswordDTO resetPasswordDTO) throws NoSuchAlgorithmException {
        User user = userRepository.findByCheckerCode(resetPasswordDTO.getResetPasswordToken())
                .orElseThrow(() -> new NotFoundException("Token inválido."));

        user.setCheckerCode(null);
        user.setPassword(hashPassword(resetPasswordDTO.getNewPassword()));

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new DataIntegrityViolationException("Não foi possível salvar este usuário");
        }
    }

    public void sendEmailResetPassword(String to) throws MessagingException {
        User user = userRepository.findByEmail(to)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        user.setCheckerCode(String.valueOf(UUID.randomUUID()));
        userRepository.save(user);
        JavaMailSenderImpl mailSender = this.getJavaMailSender();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject("Link da sua senha.");
        helper.setFrom("murilo.bot100@gmail.com");
        helper.setTo(to);

        boolean html = true;
        helper.setText("<p>Olá,</p>"
                        + "<p>Você solicitou acesso a sua senha.</p>"
                        + "<br>"
                        + "<p>Clique no link para alterar sua senha: <a href=\"http://localhost:3000/reset-password/" + user.getCheckerCode() + "\">Trocar senha</a></p>"
                        + "<br>"
                        + "<p>Ignore this email if you do remember your password, or you have not made the request.</p>"
                , html);

        mailSender.send(message);
    }

    public JavaMailSenderImpl getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("murilo.bot100@gmail.com");
        mailSender.setPassword(passwordEmail);

        Properties props = mailSender.getJavaMailProperties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
//        props.setProperty("mail.debug", "true");
//        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        props.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
        mailSender.setJavaMailProperties(props);

        return mailSender;
    }
}
