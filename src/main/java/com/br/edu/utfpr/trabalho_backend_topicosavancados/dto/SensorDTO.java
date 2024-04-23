package com.br.edu.utfpr.trabalho_backend_topicosavancados.dto;

import java.util.Set;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Medicao;

import jakarta.validation.constraints.NotBlank;

public record SensorDTO(
        @NotBlank String nome, @NotBlank String tipo, @NotBlank Set<Medicao> medicoes) {

}
