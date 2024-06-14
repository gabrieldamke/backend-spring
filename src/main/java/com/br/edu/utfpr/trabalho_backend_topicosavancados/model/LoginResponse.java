package com.br.edu.utfpr.trabalho_backend_topicosavancados.model;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;

    private long expiresIn;

    public String getToken() {
        return token;
    }

}