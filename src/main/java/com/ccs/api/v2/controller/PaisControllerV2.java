package com.ccs.api.v2.controller;

import com.ccs.api.v1.model.input.PaisInput;
import com.ccs.api.v1.model.output.PaisOutput;
import com.ccs.core.utils.PermissaoAcessoUtils;
import com.ccs.core.utils.mapper.PaisMapper;
import com.ccs.domain.service.PaisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * Implementação da API utilizando os Métodos, Verbos e Response Status recomendados para HTTP
 *
 * @see <a href="https://www.service-architecture.com/articles/web-services/representational-state-transfer-rest.html">REST</a>}
 */
@RestController
@RequestMapping("/api/v2/pais")
@RequiredArgsConstructor
public class PaisControllerV2 {

    private final PaisService service;
    private final PaisMapper mapper;
    private final PermissaoAcessoUtils acessoUtils;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Retorna todo os Países com paginação")
    @Parameters({
            @Parameter(name = "pageable", example = """
                    {
                     "page": 0,
                     "size": 10,
                     "sort": [
                     "nome,DESC"
                      ]
                    }
                    """),
            @Parameter(name = "api-key", description = "Token de autenticação")})
    public CompletableFuture<Page<PaisOutput>> getAll(@PageableDefault Pageable pageable,
                                                      @RequestHeader(value = "api-key") String apiKey) {
        return supplyAsync(() ->
                service.getlAll(pageable), ForkJoinPool.commonPool())
                .thenApply(mapper::toPage);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Cadastra um país")
    @Parameter(name = "api-key", description = "Token de autenticação")
    public CompletableFuture<PaisOutput> salvar(@RequestBody @Valid PaisInput input, @RequestHeader(value = "api-key") String apiKey) {
        return supplyAsync(() -> {
            acessoUtils.validarPapelAdministrador(apiKey);
            return service.save(mapper.toEntity(input));
        }, ForkJoinPool.commonPool())
                .thenApply(mapper::toModel);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Atualiza um País")
    @Parameters({
            @Parameter(name = "id", description = "ID do País que será atualizado", required = true),
            @Parameter(name = "api-key", description = "Token de autenticação")
    })
    public CompletableFuture<PaisOutput> update(@PathVariable Long id, @Valid PaisInput input,
                                                @RequestHeader(value = "api-key") String apiKey) {

        return supplyAsync(() -> {
            acessoUtils.validarPapelAdministrador(apiKey);
            var pais = service.findById(id);
            mapper.updateEntity(input, pais);
            return service.save(pais);

        }, ForkJoinPool.commonPool())
                .thenApply(mapper::toModel);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Remove um País pelo id")
    @Parameters({
            @Parameter(name = "id", description = "ID do País que será removido"),
            @Parameter(name = "api-key", description = "Token de autenticação")
    })
    public CompletableFuture<Void> delete(@PathVariable Long id, @RequestHeader(value = "api-key") String apiKey) {
        return runAsync(() -> {
            acessoUtils.validarPapelAdministrador(apiKey);
            service.delete(id);

        }, ForkJoinPool.commonPool());
    }
}
