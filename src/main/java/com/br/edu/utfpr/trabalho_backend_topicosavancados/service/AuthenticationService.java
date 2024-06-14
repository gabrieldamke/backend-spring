package com.br.edu.utfpr.trabalho_backend_topicosavancados.service;

import org.slf4j.LoggerFactory;
/*
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
 */
import org.springframework.stereotype.Service;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.PessoaLoginDto;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.PessoaRegisterDTO;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Pessoa;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.PessoaRepository;

@Service
public class AuthenticationService {
    private final PessoaRepository pessoaRepository;

    /*
     * private final PasswordEncoder passwordEncoder;
     * 
     * private final AuthenticationManager authenticationManager;
     */

    public AuthenticationService(
            PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    /*
     * AuthenticationManager authenticationManager,
     * PasswordEncoder passwordEncoder) {
     * this.authenticationManager = authenticationManager;
     * this.pessoaRepository = pessoaRepository;
     * this.passwordEncoder = passwordEncoder;
     */

    public Pessoa signup(PessoaRegisterDTO input) {
        Pessoa pessoa = new Pessoa();

        pessoa.setNome(input.nome());
        pessoa.setEmail(input.email());

        // pessoa.setPassword(passwordEncoder.encode(input.password()));

        return pessoaRepository.save(pessoa);
    }

    public Pessoa authenticate(PessoaLoginDto input) {
        /*
         * authenticationManager.authenticate(
         * new UsernamePasswordAuthenticationToken(
         * input.email(),
         * input.password()));
         */
        return pessoaRepository.findByEmail(input.email())
                .orElseThrow();
    }
}