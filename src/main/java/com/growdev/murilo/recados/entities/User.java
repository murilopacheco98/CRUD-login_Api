package com.growdev.murilo.recados.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

//  @OneToMany(mappedBy = "usuarioId")
//  private List<Recado> recados;

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
  }
}
