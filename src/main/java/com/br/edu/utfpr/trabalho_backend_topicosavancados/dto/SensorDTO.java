package com.br.edu.utfpr.trabalho_backend_topicosavancados.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SensorDTO(
                @NotBlank String nome,
                @NotBlank String tipo,
                Set<Long> medicoesIds,
                @NotNull Long dispositivoId) {

}