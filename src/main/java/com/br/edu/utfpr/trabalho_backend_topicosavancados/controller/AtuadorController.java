package com.br.edu.utfpr.trabalho_backend_topicosavancados.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.AtuadorDTO;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Atuador;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.service.AtuadorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Atuador", description = "Controller para gerenciamento de atuadores")
@RestController
@RequestMapping("/atuadores")
public class AtuadorController {
    @Autowired
    private AtuadorService atuadorService;

    @PostMapping
    @Operation(summary = "Cria um novo atuador", description = "Registra um novo atuador com os dados fornecidos no DTO")
    @ApiResponse(responseCode = "201", description = "Atuador criado com sucesso", content = @Content(schema = @Schema(implementation = Atuador.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    public ResponseEntity<Object> create(@RequestBody @Valid AtuadorDTO dto) {
        try {
            var res = atuadorService.createAtuador(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um atuador existente", description = "Atualiza os dados de um atuador com base no ID fornecido")
    @ApiResponse(responseCode = "200", description = "Atuador atualizado com sucesso", content = @Content(schema = @Schema(implementation = Atuador.class)))
    @ApiResponse(responseCode = "404", description = "Atuador não encontrado")
    @ApiResponse(responseCode = "400", description = "Erro ao atualizar dados")
    public ResponseEntity<Object> update(@PathVariable("id") long id, @RequestBody @Valid AtuadorDTO dto) {
        try {
            var res = atuadorService.updateAtuador(id, dto);
            return ResponseEntity.ok(res);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Lista todos os atuadores", description = "Retorna uma lista de todos os atuadores disponíveis")
    public List<Atuador> getAll() {
        return atuadorService.getAllAtuadores();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtém um atuador por ID", description = "Retorna detalhes de um atuador específico pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Atuador encontrado", content = @Content(schema = @Schema(implementation = Atuador.class)))
    @ApiResponse(responseCode = "404", description = "Atuador não encontrado")
    public ResponseEntity<Object> getById(@PathVariable("id") long id) {
        var res = atuadorService.getAtuadorById(id);

        return res.isPresent()
                ? ResponseEntity.ok(res.get())
                : ResponseEntity.notFound().build();
    }
}