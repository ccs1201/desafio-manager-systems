package com.ccs.core.utils.data;

import com.ccs.domain.entity.Pais;
import com.ccs.domain.entity.Usuario;
import com.ccs.domain.service.PaisService;
import com.ccs.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Classe responsável por pular dados no banco durante a inicialização
 * da aplicação.
 */
@Component
@RequiredArgsConstructor
public class LoadData {

    private final PaisService paisService;
    private final UsuarioService usuarioService;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void load() {
        loadPaises();
        loadUsuarios();
    }

    private void loadPaises() {
        var br = Pais.builder()
                .gentilico("Brasileiro")
                .nome("Brasil")
                .sigla("BR").build();
        paisService.save(br);

        var ar = Pais.builder()
                .sigla("AR")
                .nome("Argentina")
                .gentilico("Argentino").build();
        paisService.save(ar);

        var cl = Pais.builder()
                .nome("Alemanha")
                .sigla("AL")
                .gentilico("Alemão").build();
        paisService.save(cl);
    }

    private void loadUsuarios() {
        var convidado = Usuario.builder()
                .login("convidado")
                .senha(passwordEncoder.encode("manager"))
                .nome("'Usuário convidado")
                .administrador(Boolean.FALSE)
                .build();
        var admin = Usuario.builder()
                .login("admin")
                .senha(passwordEncoder.encode("suporte"))
                .nome("Gestor")
                .administrador(Boolean.TRUE)
                .build();
        usuarioService.save(convidado);
        usuarioService.save(admin);
    }
}
