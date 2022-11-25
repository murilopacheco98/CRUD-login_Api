package com.growdev.murilo.recados.resources;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.growdev.murilo.recados.dto.SignInDto;
import com.growdev.murilo.recados.dto.SignUpDto;
import com.growdev.murilo.recados.entities.User;
import com.growdev.murilo.recados.service.UserService;

@CrossOrigin("*")
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpDto) {
        userService.signUp(signUpDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody SignInDto signInDto) {
        User user = userService.signIn(signInDto);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> users() {
        List<User> users = userService.listUsers();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
}
