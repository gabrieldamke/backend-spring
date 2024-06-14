package com.br.edu.utfpr.trabalho_backend_topicosavancados.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtuadorDTO(@NotBlank String nome, @NotNull Long dispositivoId) {
}