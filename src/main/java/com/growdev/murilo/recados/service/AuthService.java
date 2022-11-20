package com.growdev.murilo.recados.service;

import com.growdev.murilo.recados.entities.AuthToken;
import com.growdev.murilo.recados.entities.User;
import com.growdev.murilo.recados.exceptions.customExceptions.AutheticationFailExeception;
import com.growdev.murilo.recados.repository.AuthTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {

    @Autowired
    AuthTokenRepository authTokenRepository;

    public void saveConfirmationToken(AuthToken authenticationToken) {
        authTokenRepository.save(authenticationToken);
    }

    public AuthToken getTokenByUser(User user) {
        return authTokenRepository.findByUser(user);
    }

    public User getUser(String token) {
        AuthToken authenticationToken = authTokenRepository.findByToken(token);
        if (Objects.isNull(token)) {
            return null;
        }
        return authenticationToken.getUser();
    }

    public void authenticate(String token) {
        if (Objects.isNull(token)) {
            throw new AutheticationFailExeception("token not present");
        }

        if (Objects.isNull(getUser(token))) {
            throw new AutheticationFailExeception("token not valid");
        }
    }
}

