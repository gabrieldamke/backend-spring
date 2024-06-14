package com.br.edu.utfpr.trabalho_backend_topicosavancados.dto;

import jakarta.validation.constraints.NotBlank;

public record PessoaRegisterDTO(@NotBlank String nome, @NotBlank String email, @NotBlank String password) {

}
