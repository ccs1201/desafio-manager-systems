package com.ccs.domain.service;

import com.ccs.core.configuration.TokenDurationProperties;
import com.ccs.core.exception.ApiAutenticationException;
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

    private static final String TOKEN_INVALIDO = "Token invalido.";
    private final TokenRepository repository;
    private final TokenDurationProperties tokenDurationProperties;

    public Boolean renovarTicket(String token) {
        var tokenEntity = repository.findByToken(token);

        if(tokenEntity.isPresent()){
            if(tokenEntity.get().getAdministrador()){
                this.calcularExpiracao(tokenEntity.get());
                repository.save(tokenEntity.get());
                return true;
            }
        }
        return false;
    }

    public void save(Token token) {
        repository.save(token);
    }

    public void gerarToken(Usuario usuario) {
        var token = Token.builder()
                .token(UUID.randomUUID().toString())
                .administrador(usuario.getAdministrador())
                .login(usuario.getLogin())
                .build();
        this.calcularExpiracao(token);
        this.save(token);
    }

    public Token findByLogin(String login) {
        return repository.findByLogin(login).orElseThrow();

    }

    public Token findByApiKey(String apiKey) {
        return repository.findByToken(apiKey)
                .orElseThrow(() -> new ApiAutenticationException(TOKEN_INVALIDO));
    }

    public void calcularExpiracao(Token token) {
        token.setExpiracao(LocalDateTime.now().plusMinutes(tokenDurationProperties.getToken_duration()));
    }
}
