package com.ccs.api.v1.controller;

import com.ccs.api.v1.model.input.PaisInput;
import com.ccs.api.v1.model.output.PaisOutput;
import com.ccs.core.utils.PermissaoAcessoUtils;
import com.ccs.core.utils.mapper.PaisMapper;
import com.ccs.domain.service.PaisService;
import com.ccs.domain.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@RestController
@RequestMapping(value = "/api/v1/pais", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PaisControllerV1 {


    private final PaisService service;
    private final PaisMapper mapper;
    private final TokenService tokenService;
    private final PermissaoAcessoUtils acessoUtils;

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Retorna todo os Países com paginação")
    @Parameters({@Parameter(name = "pageable", example = """
            {
             "page": 0,
             "size": 10,
             "sort": [
             "nome,DESC"
              ]
            }
            """),
            @Parameter(name = "api-key", description = "Token de autenticação")})
    public CompletableFuture<Page<PaisOutput>> listar(@PageableDefault Pageable pageable,
                                                      @RequestHeader(value = "api-key") String apiKey) {

        return supplyAsync(() ->
                service.getlAll(pageable), ForkJoinPool.commonPool())
                .thenApply(mapper::toPage);
    }

    @PostMapping("/salvar")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Cadastra um país")
    @Parameter(name = "api-key", description = "Token de autenticação")
    public CompletableFuture<PaisOutput> salvar(@RequestBody @Valid PaisInput input, @RequestHeader(value = "api-key") String apiKey) {

        return supplyAsync(() -> {
                    acessoUtils.validarPapelAdministrador(apiKey);
                    return service.save(mapper.toEntity(input));
                },
                ForkJoinPool.commonPool())
                .thenApply(mapper::toModel);
    }

    @GetMapping("/excluir/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Remove um País pelo id")
    @Parameters({
            @Parameter(name = "id", description = "ID do País que será removido", required = true),
            @Parameter(name = "api-key", description = "Token de autenticação")})
    public CompletableFuture<Boolean> excluir(@PathVariable @NotNull Long id, @RequestHeader(value = "api-key") String apiKey) {
        return supplyAsync(() -> {
                    acessoUtils.validarPapelAdministrador(apiKey);
                    return service.deleteGET(id);
                },
                ForkJoinPool.commonPool());
    }

    @GetMapping("/pesquisar")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Pesquisar países pelo nome")
    @Parameters({
            @Parameter(name = "nome", description = "Nome ou parte do nome do País", required = true),
            @Parameter(name = "pageable", example = """
                    {
                     "page": 0,
                     "size": 10,
                     "sort": [
                     "nome,DESC"
                      ]
                    }
                    """),
            @Parameter(name = "api-key", description = "Token de autenticação")
    })
    public CompletableFuture<Page<PaisOutput>> pesquisar(@NotBlank
                                                         @RequestParam String nome,
                                                         @PageableDefault Pageable pageable,
                                                         @RequestHeader(value = "api-key") String apiKey) {
        return supplyAsync(() ->
                service.findByNome(nome, pageable)
        ).thenApply(mapper::toPage);
    }
}
