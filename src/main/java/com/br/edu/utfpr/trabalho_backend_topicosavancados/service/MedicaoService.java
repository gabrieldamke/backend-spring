package com.br.edu.utfpr.trabalho_backend_topicosavancados.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.dto.MedicaoDTO;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Medicao;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Sensor;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.MedicaoRepository;
import com.br.edu.utfpr.trabalho_backend_topicosavancados.repository.SensorRepository;

@Service
public class MedicaoService {

    @Autowired
    private MedicaoRepository medicaoRepository;

    @Autowired
    private SensorRepository sensorRepository;

    public Medicao createMedicao(MedicaoDTO dto) {
        var medicao = new Medicao();
        BeanUtils.copyProperties(dto, medicao);
        Sensor sensor = sensorRepository.findById(dto.sensorId())
                .orElseThrow(() -> new NoSuchElementException("Sensor não encontrado para o ID: " + dto.sensorId()));
        medicao.setSensor(sensor);
        return medicaoRepository.save(medicao);
    }

    // Read
    public Optional<Medicao> getMedicaoById(Long id) {
        return medicaoRepository.findById(id);
    }

    public List<Medicao> getAllMedicoes() {
        return medicaoRepository.findAll();
    }

    // Update
    public Medicao updateMedicoes(Long id, MedicaoDTO dto) {
        var medicaoParameter = new Medicao();
        BeanUtils.copyProperties(dto, medicaoParameter);

        Medicao medicao = medicaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Medicao Não encontrado para o id :: " + id));

        medicao.setSensor(medicaoParameter.getSensor());
        medicao.setValor(medicaoParameter.getValor());
        medicao.setData(medicaoParameter.getData());

        return medicaoRepository.save(medicao);
    }

    // Delete
    public void deleteMedicao(Long id) {
        Medicao medicao = medicaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Medicao Não encontrado para o id :: " + id));

        medicaoRepository.delete(medicao);
    }
}
