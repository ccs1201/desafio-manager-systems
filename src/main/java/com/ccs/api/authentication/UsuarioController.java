package com.ccs.api.authentication;

import com.ccs.api.v1.model.input.LoginInput;
import com.ccs.domain.model.UsuarioAutenticado;
import com.ccs.domain.service.TokenService;
import com.ccs.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    @PostMapping("/autenticar")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<UsuarioAutenticado> autenticar(@Valid @RequestBody LoginInput input){
        return CompletableFuture.supplyAsync(() ->
                usuarioService.autenticar(input.getLogin(), input.getSenha()));
    }

    @GetMapping("/renovar-ticket")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<Boolean> renovarTicket(@RequestHeader(value = "api-key") String apiKey){
        return CompletableFuture.supplyAsync(()-> tokenService.renovarTicket(apiKey));
    }
}
