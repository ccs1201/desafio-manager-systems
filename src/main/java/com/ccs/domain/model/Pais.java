package com.ccs.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String nome;
    @Column(nullable = false, unique = true, length = 2)
    private String sigla;
    @Column(nullable = false, unique = true, length = 50)
    private  String gentilico;
}
