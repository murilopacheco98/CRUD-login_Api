package com.growdev.murilo.recados.repository;

import java.util.List;
import java.util.Optional;

import com.growdev.murilo.recados.entities.Recado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.growdev.murilo.recados.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
//  User findByName(String name);
//    @Query(value = "SELECT objeto FROM User objeto WHERE objeto.id =:userId AND objeto.arquivado = false")
//    List<Recado> findByUserIdArchived(Long userId);
//
//    @Query(value = "SELECT objeto FROM Recado objeto WHERE objeto.user.id =:userId AND objeto.arquivado = false")
//    List<Recado> findByUserIdUnarchived(Long userId);

}