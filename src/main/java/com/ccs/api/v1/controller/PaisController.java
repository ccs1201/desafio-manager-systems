package com.ccs.api.v1.controller;

import com.ccs.api.model.input.PaisInput;
import com.ccs.api.model.output.PaisOutput;
import com.ccs.core.utils.mapper.PaisMapper;
import com.ccs.domain.service.PaisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.AllArgsConstructor;
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

@RestController
@RequestMapping("/api/v1/pais")
@RequiredArgsConstructor
public class PaisController {

    private final PaisService service;
    private final PaisMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Retorna todo os Países com paginação")
    public CompletableFuture<Page<PaisOutput>> getAll(@PageableDefault(size = 10) Pageable pageable) {
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

    @PutMapping("/{id}")
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
}
