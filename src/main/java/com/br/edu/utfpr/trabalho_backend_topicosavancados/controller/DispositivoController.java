package com.br.edu.utfpr.trabalho_backend_topicosavancados.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.DispositivoDTO;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Dispositivo;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.service.DispositivoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Dispositivo", description = "Controller para gerenciamento de dispositivos")
@RestController
@RequestMapping("/dispositivos")
public class DispositivoController {

    @Autowired
    private DispositivoService dispositivoService;

    @PostMapping
    @Operation(summary = "Cria um novo dispositivo", description = "Registra um novo dispositivo com os dados fornecidos")
    @ApiResponse(responseCode = "201", description = "Dispositivo criado com sucesso", content = @Content(schema = @Schema(implementation = Dispositivo.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    public ResponseEntity<Object> create(@RequestBody @Valid DispositivoDTO dto) {
        try {
            var res = dispositivoService.createDispositivo(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um dispositivo", description = "Atualiza os dados de um dispositivo existente com base no ID fornecido")
    @ApiResponse(responseCode = "200", description = "Dispositivo atualizado com sucesso", content = @Content(schema = @Schema(implementation = Dispositivo.class)))
    @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado")
    @ApiResponse(responseCode = "400", description = "Erro na requisição")
    public ResponseEntity<Object> update(@PathVariable("id") long id, @RequestBody @Valid DispositivoDTO dto) {
        try {
            var res = dispositivoService.updateDispositivo(id, dto);
            return ResponseEntity.ok(res);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Lista todos os dispositivos", description = "Retorna uma lista de todos os dispositivos registrados")
    public List<Dispositivo> getAll() {
        return dispositivoService.getAllDispositivos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um dispositivo por ID", description = "Retorna detalhes de um dispositivo específico com base no seu ID")
    @ApiResponse(responseCode = "200", description = "Dispositivo encontrado",
                 content = @Content(schema = @Schema(implementation = Dispositivo.class)))
    @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado")
    public ResponseEntity<Object> getById(@PathVariable("id") long id) {
        var res = dispositivoService.getDispositivoById(id);

        return res.isPresent()
                ? ResponseEntity.ok(res.get())
                : ResponseEntity.notFound().build();
    }
}