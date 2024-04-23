package com.br.edu.utfpr.trabalho_backend_topicosavancados.exception;

public class NotFoundException extends Exception {
    public NotFoundException() {
        super("Not found");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
