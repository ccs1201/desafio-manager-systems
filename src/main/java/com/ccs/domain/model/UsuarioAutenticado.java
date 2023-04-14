package com.ccs.domain.model;

import com.ccs.domain.entity.Token;
import com.ccs.domain.entity.Usuario;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioAutenticado {

    public UsuarioAutenticado(Usuario usuario, Token token){
        this.administrador = token.getAdministrador();
        this.login = token.getLogin();
        this.nome = usuario.getNome();
        this.token = token.getToken();
    }

    private String login;
    private String nome;
    private String token;
    private Boolean administrador;
    private Boolean autenticado;
}
