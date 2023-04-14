package com.ccs.domain.service;

import com.ccs.core.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository repository;
    private final int MINUTOS_ACRESCIMO = 5;

    public Boolean renovarTicket(String apiKey) {
        var token = repository.findByToken(apiKey);

        if (token.isEmpty()) {
            return false;
        }

        if (token.get().getAdministrador()) {
            token.get().setExpiracao(token.get().getExpiracao().plusMinutes(MINUTOS_ACRESCIMO));
            repository.save(token.get());
            return true;
        }
        return false;
    }
}
