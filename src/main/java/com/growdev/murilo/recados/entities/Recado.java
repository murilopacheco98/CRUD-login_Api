package com.growdev.murilo.recados.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_recados")
public class Recado implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "Assunto")
  private String assunto;
  @Column(name = "Descricao")
  private String descricao;
  @Column(name = "Status")
  private String status;
  @Column(name = "Arquivado")
  private Boolean arquivado;
  @Column(name = "Created_at")
  private Instant createdAt;
  
  @Column(name = "Updated_at")
  private Instant updatedAt;

//  @ManyToOne
//  @JoinColumn(name = "Id_usuário_fk")
//  private Long usuarioId;

  @PrePersist
  public void prePersist() {
    createdAt = Instant.now();
  }

  @PreUpdate
  public void preUpdate() {
    updatedAt = Instant.now();
  }

  // Criação de recado
  public Recado(String assunto, String descricao, String status) {
    this.assunto = assunto;
    this.descricao = descricao;
    this.status = status;
    this.arquivado = false;
  }
}
