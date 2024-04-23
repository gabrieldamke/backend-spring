package com.br.edu.utfpr.trabalho_backend_topicosavancados.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.auth.SecurityConfiguration;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.auth.UserDetailsImpl;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.LoginUserDto;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.PessoaDTO;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.RecoveryJwtTokenDto;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Pessoa;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.PessoaRepository;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

      // Método responsável por autenticar um usuário e retornar um token JWT
      public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }
    
    public Pessoa createPessoa(PessoaDTO dto) {
        var pessoa = new Pessoa();
        BeanUtils.copyProperties(dto, pessoa);
        return pessoaRepository.save(pessoa);
    }

    // Read
    public Optional<Pessoa> getPessoaById(Long id) {
        return pessoaRepository.findById(id);
    }

    public List<Pessoa> getAllPessoas() {
        return pessoaRepository.findAll();
    }

    // Update
    public Pessoa updatePessoas(Long id, PessoaDTO dto) {
        var pessoaParameter = new Pessoa();
        BeanUtils.copyProperties(dto, pessoaParameter);

        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pessoa Não encontrada para o id :: " + id));

        pessoa.setGateways(pessoaParameter.getGateways());
        pessoa.setNome(pessoaParameter.getNome());
        pessoa.setEmail(pessoaParameter.getEmail());
        pessoa.setPassword(pessoaParameter.getPassword());
        return pessoaRepository.save(pessoa);
    }

    // Delete
    public void deletePessoas(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pessoa Não encontrada para o id :: " + id));

        pessoaRepository.delete(pessoa);
    }
}
