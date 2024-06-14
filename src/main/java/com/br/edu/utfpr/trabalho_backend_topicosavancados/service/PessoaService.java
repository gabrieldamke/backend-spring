package com.br.edu.utfpr.trabalho_backend_topicosavancados.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.PessoaDTO;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Pessoa;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.PessoaRepository;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa createPessoa(PessoaDTO dto) {
        var pessoa = new Pessoa();
        BeanUtils.copyProperties(dto, pessoa);
        return pessoaRepository.save(pessoa);
    }

    public Optional<Pessoa> getPessoaById(Integer id) {
        return pessoaRepository.findById(id);
    }

    public List<Pessoa> getAllPessoas() {
        Iterable<Pessoa> iterable = pessoaRepository.findAll();
        List<Pessoa> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    public Pessoa updatePessoa(Integer id, PessoaDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pessoa Não encontrada para o id :: " + id));

        BeanUtils.copyProperties(dto, pessoa);
        return pessoaRepository.save(pessoa);
    }

    public void deletePessoa(Integer id) {
        pessoaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pessoa Não encontrada para o id :: " + id));

        pessoaRepository.deleteById(id);
    }

    @GetMapping("/me")
    public ResponseEntity<Pessoa> authenticatedUser() {
        // Authentication authentication =
        // SecurityContextHolder.getContext().getAuthentication();

        // Pessoa currentUser = (Pessoa) authentication.getPrincipal();
        Pessoa pessoa = new Pessoa();
        return ResponseEntity.ok(pessoa);
    }
}
