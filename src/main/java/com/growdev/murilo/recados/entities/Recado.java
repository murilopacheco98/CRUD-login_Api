package com.growdev.murilo.recados.entities;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

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
    private LocalDate createdAt;
    @Column(name = "Updated_at")
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "id_usuario_fk")
    private User user;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDate.now(ZoneId.of("Brazil/East"));
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDate.now(ZoneId.of("Brazil/East"));
    }

    public Recado(String assunto, String descricao, String status, User user) {
        this.assunto = assunto;
        this.descricao = descricao;
        this.status = status;
        this.arquivado = false;
        this.user = user;
    }
}
