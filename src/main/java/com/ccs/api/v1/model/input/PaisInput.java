package com.ccs.api.v1.model.input;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PaisInput {
    @Nullable
    private long id;
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    @NotBlank (message = "Sigla é obrigatório")
    @Length(max = 2, min = 2, message = "Sigla deve conter 2 caracteres")
    private String sigla;
    @NotBlank (message = "Gentilico é obrigatório")
    private String gentilico;
}
