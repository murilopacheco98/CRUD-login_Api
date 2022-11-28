package com.growdev.murilo.recados.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Email")
    private String email;
    @Column(name = "Password")
    private String password;
    @Column(name = "Name")
    private String name;
    @Column(name = "Created_at")
    private Instant createdAt;
    @Column(name = "Updated_At")
    private Instant updatedAt;

    @Column(name = "Qtd_Recados_Desarquivados")
    private Integer qtdRecadosDesarquivados;

    @Column(name = "Qtd_Recados_Arquivados")
    private Integer qtdRecadosArquivados;

    // @JsonIgnore
    // @JsonManagedReference
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

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.qtdRecadosDesarquivados = 0;
        this.qtdRecadosArquivados = 0;
    }
}
