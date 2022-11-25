package com.growdev.murilo.recados.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.growdev.murilo.recados.entities.Recado;

@Repository
public interface RecadoRepository extends JpaRepository<Recado, Long> {
    List<Recado> findByAssuntoContaining(String search);
    List<Recado> findByDescricaoContaining(String search);
    List<Recado> findByStatusContaining(String status);
    @Query(value = "SELECT objeto FROM Recado objeto WHERE objeto.user.id =:userId")
    List<Recado> findByUser(Long userId);
    @Query(value = "SELECT objeto FROM Recado objeto WHERE objeto.user.id =:userId AND objeto.status = :status AND objeto.assunto LIKE %:search%")
    List<Recado> findRecadoAssuntoByUser(Long userId, String status, String search);
//    AND objeto.descricao LIKE '%:search%'
    @Query(value = "SELECT objeto FROM Recado objeto WHERE objeto.user.id =:userId AND objeto.status = :status AND objeto.descricao LIKE %:search%")
    List<Recado> findRecadoDescricaoByUser(Long userId, String status, String search);
}
