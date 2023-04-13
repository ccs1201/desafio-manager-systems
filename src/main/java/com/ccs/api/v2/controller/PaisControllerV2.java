package com.ccs.api.v2.controller;

import com.ccs.api.v1.model.input.PaisInput;
import com.ccs.api.v1.output.PaisOutput;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Retorna todo os Países com paginação")
    public CompletableFuture<Page<PaisOutput>> getAll(@PageableDefault Pageable pageable) {
        return supplyAsync(() ->
                service.getlAll(pageable), ForkJoinPool.commonPool())
                .thenApply(mapper::toPage);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Cadastra um país")
    public CompletableFuture<PaisOutput> save(@Valid PaisInput input) {
        return supplyAsync(() ->
                service.save(mapper.toEntity(input)), ForkJoinPool.commonPool())
                .thenApply(mapper::toModel);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Atualiza um País")
    @Parameters(
            @Parameter(name = "id", description = "ID do País que será atualizado", required = true)
    )
    public CompletableFuture<PaisOutput> update(@PathVariable Long id, @Valid PaisInput input) {

        return supplyAsync(() -> {
            var pais = service.findById(id);
            mapper.updateEntity(input, pais);
            return service.save(pais);
        }, ForkJoinPool.commonPool())
                .thenApply(mapper::toModel);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Remove um País pelo id")
    @Parameter(name = "id", description = "ID do País que será removido")
    public void delete(@PathVariable Long id){
        CompletableFuture.runAsync(() ->
                service.delete(id), ForkJoinPool.commonPool()
                );
    }
}
