package com.br.edu.utfpr.trabalho_backend_topicosavancados.dto;

import java.util.Set;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DispositivoDTO(
                @NotBlank String nome,
                @NotBlank String descricao,
                @DecimalMin(value = "-90.0", message = "Latitude deve ser >= -90") @DecimalMax(value = "90.0", message = "Latitude deve ser <= 90") Double latitude,
                @DecimalMin(value = "-180.0", message = "Longitude deve ser >= -180") @DecimalMax(value = "180.0", message = "Longitude deve ser <= 180") Double longitude,
                @NotNull long gatewayId,
                Set<Long> sensoresIds,
                Set<Long> atuadoresIds) {
}