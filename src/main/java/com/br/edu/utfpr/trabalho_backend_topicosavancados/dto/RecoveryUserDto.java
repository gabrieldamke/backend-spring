package com.br.edu.utfpr.trabalho_backend_topicosavancados.dto;

import java.util.List;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Role;

public record RecoveryUserDto(

        Long id,
        String email,
        List<Role> roles

) {
}