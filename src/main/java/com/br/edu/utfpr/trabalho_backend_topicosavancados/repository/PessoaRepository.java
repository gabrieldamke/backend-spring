package com.br.edu.utfpr.trabalho_backend_topicosavancados.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByEmail(String email);
}
