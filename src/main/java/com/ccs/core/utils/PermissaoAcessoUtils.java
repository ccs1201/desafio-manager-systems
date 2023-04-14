package com.ccs.core.utils;

import com.ccs.core.exception.ApiAutenticationException;
import com.ccs.domain.entity.Token;
import com.ccs.domain.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissaoAcessoUtils {

    private static final String FUNCAO_SOMENTE_ADMINISTRADOR = "Somente administradores podem realizar esta operação";
    private final TokenService tokenService;

    private Token findToken(String apikey) {
        return tokenService.findByApiKey(apikey);
    }

    public void validarPapelAdministrador(String apiKey) {
        if (!this.findToken(apiKey).getAdministrador()) {
            throw new ApiAutenticationException(FUNCAO_SOMENTE_ADMINISTRADOR);
        }
    }
}
