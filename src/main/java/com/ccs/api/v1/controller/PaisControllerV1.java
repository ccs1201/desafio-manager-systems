package com.ccs.api.v1.controller;

import com.ccs.api.v1.model.input.PaisInput;
import com.ccs.api.v1.output.PaisOutput;
import com.ccs.core.utils.mapper.PaisMapper;
import com.ccs.domain.service.PaisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@RestController
@RequestMapping("/api/v1/pais")
@RequiredArgsConstructor
public class PaisControllerV1 {

    private final PaisService service;
    private final PaisMapper mapper;

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Retorna todo os Países com paginação")
    public CompletableFuture<Page<PaisOutput>> listar(@PageableDefault Pageable pageable) {
        return supplyAsync(() ->
                service.getlAll(pageable), ForkJoinPool.commonPool())
                .thenApply(mapper::toPage);
    }

    @PostMapping("/salvar")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Cadastra um país")
    public CompletableFuture<PaisOutput> salvar(@RequestBody @Valid PaisInput input) {
        return supplyAsync(() ->
                service.save(mapper.toEntity(input)), ForkJoinPool.commonPool())
                .thenApply(mapper::toModel);
    }

    @GetMapping("/excluir/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Remove um País pelo id")
    @Parameter(name = "id", description = "ID do País que será removido", required = true)
    public CompletableFuture<Boolean> excluir(@PathVariable @NotNull Long id) {
        return supplyAsync(() ->
                service.deleteGET(id), ForkJoinPool.commonPool()
        );
    }

    @GetMapping("/pesquisar")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Pesquisar países pelo nome")
    @Parameter(name = "nome", description = "Nome ou parte do nome do País", required = true)
    public CompletableFuture<Page<PaisOutput>> pesquisar(@NotBlank(message = "informe o nome ou parte dele")
                                                         @RequestParam String nome,
                                                         @PageableDefault Pageable pageable) {
        return supplyAsync(() ->
                service.findByNome(nome, pageable)
        ).thenApply(mapper::toPage);
    }
}
