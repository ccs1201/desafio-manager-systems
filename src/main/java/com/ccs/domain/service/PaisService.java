package com.ccs.domain.service;

import com.ccs.core.exception.ServiceException;
import com.ccs.core.repository.PaisRepository;
import com.ccs.domain.model.Pais;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaisService {

    private final PaisRepository repository;
    private final String REGISTRO_NAO_LOCALIZADO = "Nenhum registro localizado";
    private final String ERRO_SALVAR = "Erro ao salvar País";
    private final String LOGIN_JA_CADASTRADO = "Login já cadastrado.";

    public Pais save(Pais pais) {
        try {
            return repository.save(pais);
        } catch (IllegalArgumentException e) {
            throw new ServiceException(ERRO_SALVAR, e);
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException(LOGIN_JA_CADASTRADO);
        }
    }

    public Page<Pais> getlAll(Pageable pageable) {
        var paises = repository.findAll(pageable);

        if (paises.isEmpty()) {
            throw new ServiceException(REGISTRO_NAO_LOCALIZADO);
        }

        return paises;
    }

    public Page<Pais> findByNome(String nome, Pageable pageable) {
        var paises = repository.findByNome(nome, pageable);

        if (paises.isEmpty()) {
            throw new ServiceException(REGISTRO_NAO_LOCALIZADO);
        }
        return paises;
    }

    public Pais findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ServiceException(REGISTRO_NAO_LOCALIZADO));
    }
}
