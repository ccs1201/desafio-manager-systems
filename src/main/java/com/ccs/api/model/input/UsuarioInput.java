package com.ccs.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioInput {
    @NotBlank(message = "Login é obrigatório")
    private String login;
    @NotBlank(message = "Senha é obrigatório")
    private String senha;
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    private boolean administrador = false;
}
