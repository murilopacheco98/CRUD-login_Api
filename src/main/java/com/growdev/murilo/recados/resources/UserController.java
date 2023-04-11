package com.growdev.murilo.recados.resources;

import com.growdev.murilo.recados.dto.ResetPasswordDTO;
import com.growdev.murilo.recados.dto.UserDTO;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.growdev.murilo.recados.dto.SignInDto;
import com.growdev.murilo.recados.dto.SignUpDto;
import com.growdev.murilo.recados.entities.User;
import com.growdev.murilo.recados.service.UserService;

@RequestMapping("/user")
@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> users() {
        List<User> users = userService.listUsers();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping("/resend/confirm-email/{email}")
    public ResponseEntity<?> resendConfirmEmail(@PathVariable("email") String email) throws MessagingException {
        userService.resendConfirmEmail(email);
        return ResponseEntity.ok().body("E-mail confirmado com sucesso.");
    }

    @GetMapping("/email/reset-password/{email}")
    public ResponseEntity<?> reset(@PathVariable("email") String email) throws MessagingException {
        userService.sendEmailResetPassword(email);
        return ResponseEntity.ok().body("Email enviado neste endereço de email.");
    }

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpDto) throws NoSuchAlgorithmException, MessagingException {
        userService.signUp(signUpDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> signIn(@RequestBody SignInDto signInDto) throws NoSuchAlgorithmException {
        UserDTO userDTO = userService.signIn(signInDto);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    @PostMapping("/confirm-email/{checkerCode}")
    public ResponseEntity<?> confirmEmail(@RequestParam("checkerCode") String checkerCode) {
        userService.confirmEmail(checkerCode);
        return ResponseEntity.ok().body("E-mail confirmado com sucesso.");
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) throws NoSuchAlgorithmException {
        userService.resetPassword(resetPasswordDTO);
        return ResponseEntity.ok().body("Senha alterada com sucesso.");
    }

}
