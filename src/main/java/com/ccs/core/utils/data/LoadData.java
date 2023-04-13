package com.ccs.core.utils.data;

import com.ccs.core.repository.PaisRepository;
import com.ccs.domain.model.Pais;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Classe responsável por pular dados no banco durante a inicialização
 * da aplicação com finalidade de teste
 */
@Component
@RequiredArgsConstructor
public class LoadData {

    private final PaisRepository repository;

    @PostConstruct
    private void load() {
        var br = Pais.builder()
                .gentilico("Brasileiro")
                .nome("brasil")
                .sigla("BR").build();
        repository.save(br);

        var ar = Pais.builder()
                .sigla("AR")
                .nome("Argentina")
                .gentilico("Argentino").build();
        repository.save(ar);

        var cl = Pais.builder()
                .nome("Chile")
                .sigla("CL")
                .gentilico("Chileno").build();
        repository.save(cl);
    }
}
