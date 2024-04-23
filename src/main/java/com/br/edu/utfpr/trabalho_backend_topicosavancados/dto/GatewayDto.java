package com.br.edu.utfpr.trabalho_backend_topicosavancados.dto;

import java.util.Set;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Dispositivo;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Pessoa;

import jakarta.validation.constraints.NotBlank;

public record GatewayDto(@NotBlank String nome, @NotBlank String descricao, @NotBlank String endereco,
                Set<Dispositivo> dispositivos, @NotBlank Pessoa pessoa) {

}
