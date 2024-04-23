package com.br.edu.utfpr.trabalho_backend_topicosavancados.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.DispositivoDTO;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Dispositivo;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.DispositivoRepository;

@Service
public class DispositivoService {

    @Autowired
    private DispositivoRepository dispositivoRepository;

    public Dispositivo createDispositivo(DispositivoDTO dto) {
        var dispositivo = new Dispositivo();
        BeanUtils.copyProperties(dto, dispositivo);
        return dispositivoRepository.save(dispositivo);
    }

    // Read
    public Optional<Dispositivo> getDispositivoById(Long id) {
        return dispositivoRepository.findById(id);
    }

    public List<Dispositivo> getAllDispositivos() {
        return dispositivoRepository.findAll();
    }

    // Update
    public Dispositivo updateDispositivo(Long id, DispositivoDTO dto) {
        var dispotivoParameter = new Dispositivo();
        BeanUtils.copyProperties(dto, dispotivoParameter);

        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Dispositivo Não encontrado para o id :: " + id));

        dispositivo.setNome(dispotivoParameter.getNome());
        dispositivo.setAtuadores(dispotivoParameter.getAtuadores());
        dispositivo.setSensores(dispotivoParameter.getSensores());

        return dispositivoRepository.save(dispositivo);
    }

    // Delete
    public void deleteDispositivo(Long id) {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Dispositivo Não encontrado para o id :: " + id));

        dispositivoRepository.delete(dispositivo);
    }

}