package org.example.aplication.service;

import org.example.domain.CitaMedica;


import java.util.List;

public interface CitaMedicaService {
    List<CitaMedica> findAll();
    CitaMedica findBynumeroCita(int numeroCita);
    void save(CitaMedica citaMedica);
    void update(CitaMedica citaMedica);
    void delete(int numeroCita);
}
