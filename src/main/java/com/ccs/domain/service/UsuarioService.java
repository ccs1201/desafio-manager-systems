package com.ccs.domain.service;

import com.ccs.core.exception.ApiAutenticationException;
import com.ccs.core.exception.ApiServiceException;
import com.ccs.core.repository.UsuarioRepository;
import com.ccs.domain.entity.Token;
import com.ccs.domain.entity.Usuario;
import com.ccs.domain.model.UsuarioAutenticado;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private static final String REGISTRO_NAO_LOCALIZADO = "Nenhum registro localizado";
    private static final String ERRO_SALVAR = "Erro ao salvar Usuário";
    private static final String JA_CADASTRADO = "Login ja cadastrado";
    private static final String LOGIN_SENHA_INVALIDO = "Login ou Senha inválidos.";
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public Page<Usuario> getAll(Pageable pageable) {
        var usuarios = repository.findAll(pageable);
        if (usuarios.isEmpty()) {
            throw new ApiServiceException(REGISTRO_NAO_LOCALIZADO);
        }
        return usuarios;
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        try {
            usuario = repository.save(usuario);

            tokenService.gerarToken(usuario);

            return usuario;

        } catch (IllegalArgumentException e) {
            throw new ApiServiceException(ERRO_SALVAR, e);
        } catch (DataIntegrityViolationException e) {
            throw new ApiServiceException(JA_CADASTRADO);
        }
    }

    public UsuarioAutenticado autenticar(String login, String senha) {
        var usuario = this.findBylogin(login);
        var token = tokenService.findByLogin(login);

        if (this.valiadarSenha(usuario, senha)) {
            tokenService.calcularExpiracao(token);
            return this.buildUsuarioAutenticado(usuario, token);
        } else {
            throw new ApiAutenticationException(LOGIN_SENHA_INVALIDO);
        }
    }

    private UsuarioAutenticado buildUsuarioAutenticado(Usuario usuario, Token token) {
        var usuarioAutenticado = new UsuarioAutenticado(usuario, token);
        usuarioAutenticado.setAutenticado(true);
        return usuarioAutenticado;
    }

    private Usuario findBylogin(String login) {

        return repository.findByLogin(login).orElseThrow(() ->
                new ApiAutenticationException(LOGIN_SENHA_INVALIDO));
    }

    private boolean valiadarSenha(Usuario usuario, String senha) {
        return passwordEncoder.matches(senha, usuario.getSenha());
    }
}
