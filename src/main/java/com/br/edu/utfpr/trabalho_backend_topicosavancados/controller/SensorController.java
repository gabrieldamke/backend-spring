package com.br.edu.utfpr.trabalho_backend_topicosavancados.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.SensorDTO;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Sensor;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.service.SensorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Sensor", description = "Controller para operações relacionadas a sensores")
@RequestMapping("/sensor")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @Operation(summary = "Cria um novo sensor", description = "Cria um novo sensor com os dados fornecidos no DTO", operationId = "createSensor")
    @ApiResponse(responseCode = "201", description = "Sensor criado com sucesso", content = @Content(schema = @Schema(implementation = Sensor.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid SensorDTO dto) {
        try {
            var res = sensorService.createSensor(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @Operation(summary = "Atualiza um sensor", description = "Atualiza o sensor com o ID fornecido com novos dados", operationId = "updateSensor")
    @ApiResponse(responseCode = "200", description = "Sensor atualizado com sucesso", content = @Content(schema = @Schema(implementation = Sensor.class)))
    @ApiResponse(responseCode = "404", description = "Sensor não encontrado")
    @ApiResponse(responseCode = "400", description = "Erro de solicitação")
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") long id, @RequestBody @Valid SensorDTO dto) {
        try {
            var res = sensorService.updateSensors(id, dto);
            return ResponseEntity.ok(res);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Lista todos os sensores", description = "Retorna uma lista de todos os sensores disponíveis", operationId = "getAllSensors")
    @GetMapping
    public List<Sensor> getAll() {
        return sensorService.getAllSensors();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca sensor por ID", description = "Busca um sensor específico pelo seu ID", operationId = "getSensorById")
    @ApiResponse(responseCode = "200", description = "Sensor encontrado", content = @Content(schema = @Schema(implementation = Sensor.class)))
    @ApiResponse(responseCode = "404", description = "Sensor não encontrado")
    public ResponseEntity<Object> getById(@PathVariable("id") long id) {
        var res = sensorService.getSensorById(id);

        return res.isPresent()
                ? ResponseEntity.ok(res.get())
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um sensor", description = "Deleta um sensor com base no ID fornecido", operationId = "deletesensor")
    @ApiResponse(responseCode = "200", description = "Sensor deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Sensor não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno ao deletar o sensor")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            sensorService.deleteSensors(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar sensor: " + e.getMessage());
        }
    }
}
