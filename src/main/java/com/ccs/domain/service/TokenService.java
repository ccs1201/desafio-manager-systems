package com.ccs.domain.service;

import com.ccs.core.configuration.TokenDurationProperties;
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
    private final TokenDurationProperties tokenDurationProperties;

    public Boolean renovarTicket(String token) {
        var tokenEntity = repository.findByToken(token);

        tokenEntity
                .filter(Token::getAdministrador)
                .ifPresent(t -> {
                    t.setExpiracao(LocalDateTime.now().plusMinutes(tokenDurationProperties.getToken_duration()));
                    repository.save(t);
                });

        return tokenEntity.isPresent();
    }

    public void save(Token token) {
        repository.save(token);
    }

    public void gerarToken(Usuario usuario) {
        var token = Token.builder()
                .expiracao(LocalDateTime.now().plusMinutes(tokenDurationProperties.getToken_duration()))
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
