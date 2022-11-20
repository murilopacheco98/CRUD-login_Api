package com.growdev.murilo.recados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.growdev.murilo.recados.entities.AuthToken;
import com.growdev.murilo.recados.entities.User;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long>{
  AuthToken findByToken(String token);
  AuthToken findByUser(User user);
}
