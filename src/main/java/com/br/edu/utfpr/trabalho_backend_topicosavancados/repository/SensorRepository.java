package com.br.edu.utfpr.trabalho_backend_topicosavancados.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.edu.utfpr.trabalho_backend_topicosavancados.model.Sensor;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

}
