package com.br.edu.utfpr.trabalho_backend_topicosavancados.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.MedicaoDTO;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Medicao;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.service.MedicaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Medicao", description = "Controller para gerenciamento de medições")
@RestController
@RequestMapping("/medicao")
public class MedicaoController {

    @Autowired
    private MedicaoService medicaoService;

    @PostMapping
    @Operation(summary = "Cria uma nova medição", description = "Cria uma nova medição com os dados fornecidos", operationId = "createMedicao")
    @ApiResponse(responseCode = "201", description = "Medição criada com sucesso", content = @Content(schema = @Schema(implementation = Medicao.class)))
    @ApiResponse(responseCode = "400", description = "Erro nos dados fornecidos")
    public ResponseEntity<Object> create(@RequestBody @Valid MedicaoDTO dto) {
        try {
            var res = medicaoService.createMedicao(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma medição", description = "Atualiza os dados de uma medição com base no ID fornecido", operationId = "updateMedicao")
    @ApiResponse(responseCode = "200", description = "Medição atualizada com sucesso", content = @Content(schema = @Schema(implementation = Medicao.class)))
    @ApiResponse(responseCode = "404", description = "Medição não encontrada")
    @ApiResponse(responseCode = "400", description = "Erro na requisição")
    public ResponseEntity<Object> update(@PathVariable("id") long id, @RequestBody @Valid MedicaoDTO dto) {
        try {
            var res = medicaoService.updateMedicoes(id, dto);
            return ResponseEntity.ok(res);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Lista todas as medições", description = "Retorna uma lista de todas as medições registradas", operationId = "getAllMedicoes")
    public List<Medicao> getAll() {
        return medicaoService.getAllMedicoes();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma medição por ID", description = "Retorna detalhes de uma medição específica com base no seu ID", operationId = "getMedicaoById")
    @ApiResponse(responseCode = "200", description = "Medição encontrada", content = @Content(schema = @Schema(implementation = Medicao.class)))
    @ApiResponse(responseCode = "404", description = "Medição não encontrada")
    public ResponseEntity<Object> getById(@PathVariable("id") long id) {
        var res = medicaoService.getMedicaoById(id);

        return res.isPresent()
                ? ResponseEntity.ok(res.get())
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um medicao", description = "Deleta um medicção com base no ID fornecido", operationId = "deleteMedicao")
    @ApiResponse(responseCode = "200", description = "Medição deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Medição não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno ao deletar o medicao")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            medicaoService.deleteMedicao(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar medicao: " + e.getMessage());
        }
    }
}