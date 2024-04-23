package com.br.edu.utfpr.trabalho_backend_topicosavancados.service;

import java.io.Console;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.GatewayDto;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Gateway;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Pessoa;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.GatewayRepository;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.PessoaRepository;

@Service
public class GatewayService {

    @Autowired
    private GatewayRepository gatewayRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Gateway createGateway(GatewayDto dto) {
        Pessoa pessoa = new Pessoa();
        var gateway = new Gateway();
        BeanUtils.copyProperties(dto, gateway);
        if (dto.pessoa() != null) {
            Pessoa pessoaoObj = pessoaRepository.findById(dto.pessoa().getId())
                    .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com ID: " + dto.pessoa().getId()));
            gateway.setPessoa(pessoaoObj);
        }
        System.out.println(gateway);
        return gatewayRepository.save(gateway);
    }

    // Read
    public Optional<Gateway> getGatewayById(Long id) {
        return gatewayRepository.findById(id);
    }

    public List<Gateway> getAllGateways() {
        return gatewayRepository.findAll();
    }

    // Update
    public Gateway updateGateway(Long id, GatewayDto dto) {
        var gatewayParameter = new Gateway();
        BeanUtils.copyProperties(dto, gatewayParameter);

        Gateway gateway = gatewayRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Gateway Não encontrado para o id :: " + id));

        gateway.setNome(gatewayParameter.getNome());
        gateway.setEndereco(gatewayParameter.getEndereco());
        gateway.setDescricao(gatewayParameter.getDescricao());
        gateway.setDispositivos(gatewayParameter.getDispositivos());

        return gatewayRepository.save(gateway);
    }

    // Delete
    public void deleteGateway(Long id) {
        Gateway gateway = gatewayRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Gateway Não encontrado para o id :: " + id));

        gatewayRepository.delete(gateway);
    }

}
