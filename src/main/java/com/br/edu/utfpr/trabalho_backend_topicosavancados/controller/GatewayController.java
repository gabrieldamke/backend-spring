package com.br.edu.utfpr.trabalho_backend_topicosavancados.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.GatewayDto;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Gateway;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.service.GatewayService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Gateway", description = "Controller para gerenciar gateways")
@RestController
@RequestMapping("/gateway")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @PostMapping
    @Operation(summary = "Cria um novo gateway", description = "Cria um novo gateway com os dados fornecidos no DTO", operationId = "createGateway")
    @ApiResponse(responseCode = "201", description = "Gateway criado com sucesso", content = @Content(schema = @Schema(implementation = Gateway.class)))
    @ApiResponse(responseCode = "400", description = "Erro devido a dados inválidos")
    public ResponseEntity<Object> create(@RequestBody @Valid GatewayDto dto) {
        try {
            var res = gatewayService.createGateway(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um gateway", description = "Atualiza os dados de um gateway existente com base no ID fornecido", operationId = "updateGateway")
    @ApiResponse(responseCode = "200", description = "Gateway atualizado com sucesso", content = @Content(schema = @Schema(implementation = Gateway.class)))
    @ApiResponse(responseCode = "404", description = "Gateway não encontrado")
    @ApiResponse(responseCode = "400", description = "Erro na requisição devido a dados inválidos")
    public ResponseEntity<Object> update(@PathVariable("id") long id, @RequestBody @Valid GatewayDto dto) {
        try {
            var res = gatewayService.updateGateway(id, dto);
            return ResponseEntity.ok(res);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Lista todos os gateways", description = "Retorna uma lista de todos os gateways cadastrados", operationId = "getAllGateways")
    public List<Gateway> getAll() {
        return gatewayService.getAllGateways();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um gateway por ID", description = "Retorna um gateway específico com base no seu ID", operationId = "getGatewayById")
    @ApiResponse(responseCode = "200", description = "Gateway encontrado", content = @Content(schema = @Schema(implementation = Gateway.class)))
    @ApiResponse(responseCode = "404", description = "Gateway não encontrado")
    public ResponseEntity<Object> getById(@PathVariable("id") long id) {
        var res = gatewayService.getGatewayById(id);
        return res.isPresent()
                ? ResponseEntity.ok(res.get())
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um gateway", description = "Deleta um gateway com base no ID fornecido", operationId = "deleteGateway")
    @ApiResponse(responseCode = "200", description = "Gateway deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Gateway não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno ao deletar o gateway")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            gatewayService.deleteGateway(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar gateway: " + e.getMessage());
        }
    }
}