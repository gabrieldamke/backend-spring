package com.br.edu.utfpr.trabalho_backend_topicosavancados.dto;

import java.util.Set;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.auth.RoleName;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Gateway;

import jakarta.validation.constraints.NotBlank;

public record PessoaDTO(@NotBlank String nome, Set<Gateway> gateway, @NotBlank String email, @NotBlank String password, RoleName role) {

}
