package com.br.edu.utfpr.trabalho_backend_topicosavancados.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Pessoa;

@Repository
public interface PessoaRepository extends CrudRepository<Pessoa, Integer> {
    Optional<Pessoa> findByEmail(String email);
}