package com.growdev.murilo.recados.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.growdev.murilo.recados.dto.SignInDto;
import com.growdev.murilo.recados.dto.SignUpDto;
import com.growdev.murilo.recados.entities.AuthToken;
import com.growdev.murilo.recados.entities.User;
import com.growdev.murilo.recados.exceptions.customExceptions.AutheticationFailExeception;
import com.growdev.murilo.recados.exceptions.customExceptions.BadRequestException;
import com.growdev.murilo.recados.exceptions.customExceptions.NotFoundException;
import com.growdev.murilo.recados.repository.UserRepository;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;

    public void signUp(SignUpDto signUpDto) {

        if (Objects.nonNull(userRepository.findByEmail(signUpDto.getEmail()))) {
            throw new BadRequestException("Este Email já está cadastrado.");
        }

        String encryptedPassword = signUpDto.getPassword();

        try {
            encryptedPassword = hashPassword(signUpDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new BadRequestException("Error ao encriptar a senha.");
        }

        User user = new User(signUpDto.getEmail(), encryptedPassword,
                signUpDto.getName());

        final AuthToken authenticationToken = new AuthToken(user);

        String encryptedToken = authenticationToken.getToken();
        try {
            encryptedToken = hashPassword(authenticationToken.getToken());
        } catch (Exception e) {
            throw new BadRequestException("Erro ao encriptar o token.");
        }
        user.setAuthToken(encryptedToken);
        userRepository.save(user);

        authService.saveConfirmationToken(authenticationToken);
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();

        return hash;
    }

    public User signIn(SignInDto signInDto) {
        User user = userRepository.findByEmail(signInDto.getEmail())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        try {
            if (user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                AuthToken token = authService.getTokenByUser(user);

                if (Objects.isNull(token)) {
                    throw new NotFoundException("Token não encontrado.");
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new AutheticationFailExeception("Senha inválida.");
        }
        return user;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }
}
