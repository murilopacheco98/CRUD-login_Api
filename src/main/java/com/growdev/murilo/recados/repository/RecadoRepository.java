package com.growdev.murilo.recados.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.growdev.murilo.recados.entities.Recado;

@Repository
public interface RecadoRepository extends JpaRepository<Recado, Long> {
  List<Recado> findByAssuntoContaining(String search);
  List<Recado> findByDescricaoContaining(String search);
  List<Recado> findByStatusContaining(String status);

}
