package com.ccs.core.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 * Classe utilitária para carregar
 * o tempo de duração do TOKEN do arquivo
 * application.properties na propriedade
 * $com.ccs.api.token_duration.
 */
@Validated // Necessário para que o spring valide as anotações no startup da aplicação
@ConfigurationProperties("com.ccs.api")
@Component
@Getter
@Setter
public class TokenDurationProperties {
    @Positive
    @Min(1)
    private int token_duration;
}
