package com.ccs.api.v1.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioOutput {
    private long id;
    private String login;
    private String senha;
    private String nome;
    private boolean administrador;
}
