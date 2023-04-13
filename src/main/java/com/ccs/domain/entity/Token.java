package com.ccs.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Data
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    @Column(nullable = false, unique = true)
    private String token;
    @Column(nullable = false, unique = true)
    private String login;
    @Column(nullable = false)
    private LocalDateTime expiracao;
    @Column(nullable = false)
    private Boolean administrador;
}