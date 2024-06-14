package com.br.edu.utfpr.trabalho_backend_topicosavancados.controller;

import org.springframework.http.ResponseEntity;
/*
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
 */
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.PessoaLoginDto;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.PessoaRegisterDTO;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.LoginResponse;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Pessoa;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.service.AuthenticationService;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.service.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "Controller para autenticação de usuários")
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    @Operation(summary = "Cadastro de usuário", description = "Cadastra um novo usuário no sistema")
    @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso", content = @Content(schema = @Schema(implementation = Pessoa.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    public ResponseEntity<Pessoa> register(@RequestBody PessoaRegisterDTO registerUserDto) {
        Pessoa registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    /*
     * @Operation(summary = "Autenticação de usuário", description =
     * "Autentica um usuário e retorna um token JWT")
     * 
     * @ApiResponse(responseCode = "200", description =
     * "Usuário autenticado com sucesso", content = @Content(schema
     * = @Schema(implementation = LoginResponse.class)))
     * 
     * @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
     * 
     * @PostMapping("/login")
     * public ResponseEntity<LoginResponse> authenticate(@RequestBody PessoaLoginDto
     * loginUserDto) {
     * Pessoa authenticatedUser = authenticationService.authenticate(loginUserDto);
     * 
     * String jwtToken = jwtService.generateToken(authenticatedUser);
     * 
     * LoginResponse loginResponse = new LoginResponse();
     * loginResponse.setToken(jwtToken);
     * loginResponse.setExpiresIn(jwtService.getExpirationTime());
     * 
     * return ResponseEntity.ok(loginResponse);
     * }
     * 
     * @GetMapping("/me")
     * 
     * @Operation(summary = "Obter usuário autenticado", description =
     * "Retorna o usuário autenticado atual")
     * 
     * @ApiResponse(responseCode = "200", description =
     * "Usuário retornado com sucesso", content = @Content(schema
     * = @Schema(implementation = Pessoa.class)))
     * 
     * @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
     * public ResponseEntity<Pessoa> authenticatedUser() {
     * Authentication authentication =
     * SecurityContextHolder.getContext().getAuthentication();
     * 
     * Pessoa currentUser = (Pessoa) authentication.getPrincipal();
     * 
     * return ResponseEntity.ok(currentUser);
     * }
     */
}