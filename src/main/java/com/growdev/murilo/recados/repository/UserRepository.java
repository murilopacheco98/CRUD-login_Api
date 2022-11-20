package com.growdev.murilo.recados.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.growdev.murilo.recados.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
  Optional<User> findByEmail(String email);
  User findByName(String name);
}