package com.br.edu.utfpr.trabalho_backend_topicosavancados.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.DispositivoDTO;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Atuador;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Dispositivo;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Gateway;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Sensor;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.AtuadorRepository;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.DispositivoRepository;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.GatewayRepository;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.SensorRepository;

@Service
public class DispositivoService {

        @Autowired
        private DispositivoRepository dispositivoRepository;
        @Autowired
        private GatewayRepository gatewayRepository;
        @Autowired
        private SensorRepository sensorRepository;
        @Autowired
        private AtuadorRepository atuadorRepository;

        public Dispositivo createDispositivo(DispositivoDTO dto) {
                var dispositivo = new Dispositivo();
                BeanUtils.copyProperties(dto, dispositivo);

                Gateway gateway = gatewayRepository.findById(dto.gatewayId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Gateway não encontrado com ID: " + dto.gatewayId()));
                dispositivo.setGateway(gateway);

                Set<Sensor> sensores = dto.sensoresIds().stream()
                                .map(id -> sensorRepository.findById(id)
                                                .orElseThrow(() -> new RuntimeException(
                                                                "Sensor não encontrado com ID: " + id)))
                                .collect(Collectors.toSet());
                dispositivo.setSensores(sensores);

                Set<Atuador> atuadores = dto.atuadoresIds().stream()
                                .map(id -> atuadorRepository.findById(id)
                                                .orElseThrow(() -> new RuntimeException(
                                                                "Atuador não encontrado com ID: " + id)))
                                .collect(Collectors.toSet());
                dispositivo.setAtuadores(atuadores);

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
                                .orElseThrow(() -> new NoSuchElementException(
                                                "Dispositivo Não encontrado para o id :: " + id));

                dispositivo.setNome(dispotivoParameter.getNome());
                dispositivo.setAtuadores(dispotivoParameter.getAtuadores());
                dispositivo.setSensores(dispotivoParameter.getSensores());
                dispositivo.setLatitude(dispotivoParameter.getLatitude());
                dispositivo.setLongitude(dispotivoParameter.getLongitude());
                dispositivo.setDescricao(dispotivoParameter.getDescricao());

                return dispositivoRepository.save(dispositivo);
        }

        // Delete
        public void deleteDispositivo(Long id) {
                Dispositivo dispositivo = dispositivoRepository.findById(id)
                                .orElseThrow(() -> new NoSuchElementException(
                                                "Dispositivo Não encontrado para o id :: " + id));

                dispositivoRepository.delete(dispositivo);
        }

}