package com.br.edu.utfpr.trabalho_backend_topicosavancados.dto;

import java.util.Date;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Sensor;

import jakarta.validation.constraints.NotBlank;

public record MedicaoDTO(@NotBlank float valor,@NotBlank Date data, @NotBlank Sensor sensor) {

}
