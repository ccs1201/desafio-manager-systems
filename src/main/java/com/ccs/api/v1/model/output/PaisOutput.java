package com.ccs.api.v1.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaisOutput {
    private long id;
    private String nome;
    private String sigla;
    private String gentilico;
}
