package com.ccs.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String token;
    @Column(nullable = false, unique = true)
    private String login;
    @Column(nullable = false)
    private LocalDateTime expiracao;
    @Column(nullable = false)
    private Boolean administrador;
}
