package com.br.edu.utfpr.trabalho_backend_topicosavancados.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.SensorDTO;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Sensor;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.SensorRepository;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    public Sensor createSensor(SensorDTO dto) {
        var sensor = new Sensor();
        BeanUtils.copyProperties(dto, sensor);
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

        return sensorRepository.save(sensor);
    }

    // Delete
    public void deleteSensors(Long id) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Sensor Não encontrado para o id :: " + id));

        sensorRepository.delete(sensor);
    }
}
