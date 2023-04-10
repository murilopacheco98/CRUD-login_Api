package com.growdev.murilo.recados.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.growdev.murilo.recados.entities.Recado;

@Repository
public interface RecadoRepository extends JpaRepository<Recado, Long> {
    @Query(value = "SELECT objeto FROM Recado objeto WHERE objeto.user.id =:userId AND objeto.arquivado = false")
    Page<Recado> findByUserIdPageableUnarchive(Long userId, Pageable pageable);

    @Query(value = "SELECT objeto FROM Recado objeto WHERE objeto.user.id =:userId AND objeto.arquivado = true")
    Page<Recado> findByUserIdPageableArchive(Long userId, Pageable pageable);
    @Query(value = "SELECT objeto FROM Recado objeto WHERE objeto.user.id =:userId")
    List<Recado> findByUserId(Long userId);
    @Query(value = "SELECT objeto FROM Recado objeto WHERE objeto.user.id =:userId AND objeto.status = :status AND objeto.assunto LIKE %:search%")
    List<Recado> findRecadoAssuntoByUser(Long userId, Pageable pageable, String status, String search);
    @Query(value = "SELECT objeto FROM Recado objeto WHERE objeto.user.id =:userId AND objeto.status = :status AND objeto.descricao LIKE %:search%")
    List<Recado> findRecadoDescricaoByUser(Long userId, Pageable pageable, String status, String search);
}
