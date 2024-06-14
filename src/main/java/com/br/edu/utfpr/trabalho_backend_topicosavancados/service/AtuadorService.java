package com.br.edu.utfpr.trabalho_backend_topicosavancados.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.AtuadorDTO;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Atuador;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Dispositivo;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.AtuadorRepository;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.DispositivoRepository;

@Service
public class AtuadorService {

    @Autowired
    private AtuadorRepository atuadorRepository;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    public Atuador createAtuador(AtuadorDTO dto) {
        var atuador = new Atuador();
        BeanUtils.copyProperties(dto, atuador);

        Dispositivo dispositivo = dispositivoRepository.findById(dto.dispositivoId())
                .orElseThrow(
                        () -> new NoSuchElementException("Dispositivo não encontrado com id: " + dto.dispositivoId()));

        atuador.setDispositivo(dispositivo);
        return atuadorRepository.save(atuador);
    }

    // Read
    public Optional<Atuador> getAtuadorById(Long id) {
        return atuadorRepository.findById(id);
    }

    public List<Atuador> getAllAtuadores() {
        return atuadorRepository.findAll();
    }

    // Update
    public Atuador updateAtuador(Long id, AtuadorDTO dto) {
        var atuadorParameter = new Atuador();
        BeanUtils.copyProperties(dto, atuadorParameter);

        Atuador atuador = atuadorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Atuador Não encontrado para o id :: " + id));

        atuador.setNome(atuadorParameter.getNome());

        return atuadorRepository.save(atuador);
    }

    // Delete
    public void deleteAtuador(Long id) {
        Atuador atuador = atuadorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Atuador Não encontrado para o id :: " + id));

        atuadorRepository.delete(atuador);
    }
}
