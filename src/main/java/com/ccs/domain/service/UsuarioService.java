package com.ccs.domain.service;

import com.ccs.core.exception.ApiServiceException;
import com.ccs.core.repository.UsuarioRepository;
import com.ccs.domain.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private static final String REGISTRO_NAO_LOCALIZADO = "Nenhum registro localizado";
    private static final String ERRO_SALVAR = "Erro ao salvar Usuário";
    private static final String JA_CADASTRADO = "Login ja cadastrado";

    private final UsuarioRepository repository;

    public Page<Usuario> getAll(Pageable pageable) {
        var usuarios = repository.findAll(pageable);
        if (usuarios.isEmpty()) {
            throw new ApiServiceException(REGISTRO_NAO_LOCALIZADO);
        }
        return usuarios;
    }

    @Transactional
    public Usuario save(Usuario usuario){
        try {
            return repository.save(usuario);
        } catch (IllegalArgumentException e) {
            throw new ApiServiceException(ERRO_SALVAR, e);
        } catch (DataIntegrityViolationException e) {
            throw new ApiServiceException(JA_CADASTRADO);
        }
    }
}
