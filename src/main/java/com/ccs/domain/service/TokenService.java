package com.ccs.domain.service;

import com.ccs.core.repository.TokenRepository;
import com.ccs.domain.entity.Token;
import com.ccs.domain.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository repository;
    private final int MINUTOS_ACRESCIMO = 5;

    public Boolean renovarTicket(String token) {
        var tokenEntity = repository.findByToken(token);

        if (tokenEntity.isEmpty()) {
            return false;
        }

        if (tokenEntity.get().getAdministrador()) {
            tokenEntity.get().setExpiracao(tokenEntity.get().getExpiracao().plusMinutes(MINUTOS_ACRESCIMO));
            repository.save(tokenEntity.get());
            return true;
        }
        return false;
    }

    public void save(Token token) {
        repository.save(token);
    }

    public void gerarToken(Usuario usuario) {
        var token = Token.builder()
                .expiracao(LocalDateTime.now().plusMinutes(MINUTOS_ACRESCIMO))
                .token(UUID.randomUUID().toString())
                .administrador(usuario.getAdministrador())
                .login(usuario.getLogin())
                .build();
        this.save(token);
    }

    public Token findByLogin(String login) {
        return repository.findByLogin(login).orElseThrow();

    }

}
