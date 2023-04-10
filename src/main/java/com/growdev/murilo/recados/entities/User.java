package com.growdev.murilo.recados.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private Boolean enable;
    private String checkerCode;
    private Instant createdAt;
    private Instant updatedAt;

    private Integer qtdRecadosDesarquivados;

    private Integer qtdRecadosArquivados;

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Recado> recados = new ArrayList<>();
    private String authToken;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

    public User(String email, String password, String name, String checkerCode) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.qtdRecadosDesarquivados = 0;
        this.qtdRecadosArquivados = 0;
        this.checkerCode = checkerCode;
    }
}
