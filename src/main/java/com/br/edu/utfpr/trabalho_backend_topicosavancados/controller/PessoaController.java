package com.br.edu.utfpr.trabalho_backend_topicosavancados.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.PessoaDTO;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Pessoa;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.service.PessoaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Pessoa", description = "Controller responsável por gerenciar pessoas")
@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    @Operation(summary = "Cria uma nova pessoa", description = "Cria uma nova pessoa com os dados fornecidos", operationId = "createPessoa")
    @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso", content = @Content(schema = @Schema(implementation = Pessoa.class)))
    @ApiResponse(responseCode = "400", description = "Erro ao criar pessoa")
    public ResponseEntity<Object> create(@RequestBody @Valid PessoaDTO dto) {
        try {
            var res = pessoaService.createPessoa(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma pessoa", description = "Atualiza os dados de uma pessoa com base no ID fornecido", operationId = "updatePessoa")
    @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso", content = @Content(schema = @Schema(implementation = Pessoa.class)))
    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar pessoa")
    public ResponseEntity<Object> update(@PathVariable("id") Integer id, @RequestBody @Valid PessoaDTO dto) {
        try {
            var res = pessoaService.updatePessoa(id, dto);
            return ResponseEntity.ok(res);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Lista todas as pessoas", description = "Retorna uma lista de todas as pessoas cadastradas", operationId = "getAllPessoas")
    public List<Pessoa> getAll() {
        return pessoaService.getAllPessoas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma pessoa por ID", description = "Retorna os dados de uma pessoa específica com base no seu ID", operationId = "getPessoaById")
    @ApiResponse(responseCode = "200", description = "Pessoa encontrada", content = @Content(schema = @Schema(implementation = Pessoa.class)))
    @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    public ResponseEntity<Object> getById(@PathVariable("id") Integer id) {
        var res = pessoaService.getPessoaById(id);

        return res.isPresent()
                ? ResponseEntity.ok(res.get())
                : ResponseEntity.notFound().build();
    }
}