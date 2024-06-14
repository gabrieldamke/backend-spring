package com.br.edu.utfpr.trabalho_backend_topicosavancados.dto;

import java.util.Date;
import jakarta.validation.constraints.NotNull;

public record MedicaoDTO(
        @NotNull float valor,
        @NotNull Date data,
        @NotNull Long sensorId 
) {
}