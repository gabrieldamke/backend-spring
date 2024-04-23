package com.br.edu.utfpr.trabalho_backend_topicosavancados.dto;

import java.util.Set;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Atuador;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Sensor;

import jakarta.validation.constraints.NotBlank;

public record DispositivoDTO(@NotBlank String nome, @NotBlank String descricao, @NotBlank String endereco,
                String localizacao, long gateway_id, Set<Sensor> sensores, Set<Atuador> atuadores) {

}
