package com.growdev.murilo.recados.entities;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tb_tokens")
public class AuthToken implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "created_at")
    private Instant createdAt;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    // @OneToOne(mappedBy = "user")
    // private User user;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }

    public AuthToken(User user) {
        this.user = user;
        // this.createdAt = new Date();
        this.token = UUID.randomUUID().toString();
    }
}

