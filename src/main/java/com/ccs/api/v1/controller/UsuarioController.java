package com.ccs.api.v1.controller;

import com.ccs.api.v1.model.input.LoginInput;
import com.ccs.domain.model.UsuarioAutenticado;
import com.ccs.domain.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/api/v1/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsuarioController {

    private final TokenService service;

    @PostMapping("/autenticar")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<UsuarioAutenticado> autenticar(@Valid @RequestBody LoginInput input){
        return CompletableFuture.supplyAsync(() -> new UsuarioAutenticado());
    }

    @GetMapping("/renovar-ticket")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<Boolean> renovarTicket(@RequestHeader(value = "api-key") String apiKey){
        return CompletableFuture.supplyAsync(()-> service.renovarTicket(apiKey));
    }
}
