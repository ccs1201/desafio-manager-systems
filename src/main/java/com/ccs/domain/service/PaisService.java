package com.ccs.domain.service;

import com.ccs.core.exception.ApiServiceException;
import com.ccs.core.repository.PaisRepository;
import com.ccs.domain.entity.Pais;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaisService {

    private static final String REGISTRO_NAO_LOCALIZADO = "Nenhum registro localizado";
    private static final String ERRO_SALVAR = "Erro ao salvar País";
    private static final String JA_CADASTRADO = "País já cadastrado.";
    private final PaisRepository repository;

    @Transactional
    public Pais save(Pais pais) {
        try {
            return repository.save(pais);
        } catch (IllegalArgumentException e) {
            throw new ApiServiceException(ERRO_SALVAR, e);
        } catch (DataIntegrityViolationException e) {
            throw new ApiServiceException(JA_CADASTRADO);
        }
    }

    public Page<Pais> getlAll(Pageable pageable) {
        var paises = repository.findAll(pageable);

        if (paises.isEmpty()) {
            throw new ApiServiceException(REGISTRO_NAO_LOCALIZADO);
        }
        return paises;
    }

    public Page<Pais> findByNome(String nome, Pageable pageable) {
        var paises = repository.findByNomeContaining(nome, pageable);

        if (paises.isEmpty()) {
            throw new ApiServiceException(REGISTRO_NAO_LOCALIZADO);
        }
        return paises;
    }

    public Pais findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApiServiceException(REGISTRO_NAO_LOCALIZADO));
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(this.findById(id));
    }

    @Transactional
    public boolean deleteGET(Long id) {
        try {
            var entity = this.findById(id);
            repository.delete(entity);
            return true;
        } catch (ApiServiceException e) {
            return false;
        }
    }
}
