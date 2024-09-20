package org.example.aplication.service;

import org.example.domain.CitaMedica;
import org.example.domain.Paciente;
import org.example.interfaces.CitaMedicaRepository;

import java.util.List;

public class CitaMedicaServiceImpl implements CitaMedicaService{
    private final CitaMedicaRepository citaMedicaRepository;

    public CitaMedicaServiceImpl(CitaMedicaRepository citaMedicaRepository) {
        this.citaMedicaRepository = citaMedicaRepository;
    }

    @Override
    public List<CitaMedica> findAll() {
        return CitaMedicaRepository.findAll();
    }

    @Override
    public CitaMedica findBynumeroCita(int numeroCita) {
        return null;
    }

    @Override
    public CitaMedica findById(int id) {
        return CitaMedicaRepository.findBynumeroCita(numeroCita);
    }

    @Override
    public void save(CitaMedica citaMedica) {
        validarCita(citaMedica);
        citaMedicaRepository.save(citaMedica);
    }

    @Override
    public void update(CitaMedica citaMedica) {
        validarCita(citaMedica);
        citaMedicaRepository.update(citaMedica);
    }

    @Override
    public void delete(int numeroCita) {
        citaMedicaRepository.delete(numeroCita);
    }

    private void validarCita(CitaMedica citaMedica) {
        if (citaMedica.getPaciente().isEmpty()) {
            throw new IllegalArgumentException("El paciente no puede ser vacío");
        }
        if (citaMedica.getNumeroCita()== CitaMedica.getNumeroCita()) {
            throw new IllegalArgumentException("La edad del paciente debe ser mayor a cero");
        }
    }


}





