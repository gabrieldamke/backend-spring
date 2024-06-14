package com.br.edu.utfpr.trabalho_backend_topicosavancados.service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.SensorDTO;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Dispositivo;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Medicao;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Sensor;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.DispositivoRepository;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.MedicaoRepository;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.SensorRepository;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private MedicaoRepository medicaoRepository;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    public Sensor createSensor(SensorDTO dto) {
        Sensor sensor = new Sensor();
        BeanUtils.copyProperties(dto, sensor);

        Dispositivo dispositivo = dispositivoRepository.findById(dto.dispositivoId())
                .orElseThrow(() -> new RuntimeException("Dispositivo não encontrado com ID: " + dto.dispositivoId()));
        sensor.setDispositivo(dispositivo);

        if (dto.medicoesIds() != null && !dto.medicoesIds().isEmpty()) {
            Set<Medicao> medicoes = dto.medicoesIds().stream()
                    .map(id -> medicaoRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Medição não encontrada com ID: " + id)))
                    .collect(Collectors.toSet());
            sensor.setMedicoes(medicoes);
        } else {
            sensor.setMedicoes(new HashSet<>());
        }

        return sensorRepository.save(sensor);
    }

    // Read
    public Optional<Sensor> getSensorById(Long id) {
        return sensorRepository.findById(id);
    }

    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    // Update
    public Sensor updateSensors(Long id, SensorDTO dto) {
        var sensorParameter = new Sensor();
        BeanUtils.copyProperties(dto, sensorParameter);

        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Sensor Não encontrado para o id :: " + id));

        sensor.setNome(sensorParameter.getNome());
        sensor.setMedicoes(sensorParameter.getMedicoes());
        sensor.setTipo(sensorParameter.getTipo());
        sensor.setDispositivo(sensorParameter.getDispositivo());

        return sensorRepository.save(sensor);
    }

    // Delete
    public void deleteSensors(Long id) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Sensor Não encontrado para o id :: " + id));

        sensorRepository.delete(sensor);
    }
}
