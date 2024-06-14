package com.br.edu.utfpr.trabalho_backend_topicosavancados.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GatewayDto(@NotBlank String nome, @NotBlank String descricao, @NotBlank String endereco,
                @NotNull Set<Long> dispositivosIds, @NotNull Integer pessoaId) {
}