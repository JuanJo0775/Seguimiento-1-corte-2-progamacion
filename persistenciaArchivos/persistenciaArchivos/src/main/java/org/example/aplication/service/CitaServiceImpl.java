package org.example.aplication.service;

import org.example.domain.Cita;
import org.example.domain.Paciente;
import org.example.interfaces.CitaRepository;

import java.util.List;

import java.util.stream.Collectors;

public class CitaServiceImpl implements CitaService {
    private final CitaRepository citaRepository;

    public CitaServiceImpl(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Override
    public void registrarCita(Cita cita) {
        List<Cita> citas = citaRepository.cargarCitas();
        citas.add(cita);
        citaRepository.guardarCita(citas);
    }

    @Override
    public void actualizarCita(Cita cita) {
        List<Cita> citas = citaRepository.cargarCitas();
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getPaciente().getId().equals(cita.getPaciente().getId())) {
                citas.set(i, cita);
                break;
            }
        }
        citaRepository.guardarCita(citas);
    }

    @Override
    public void eliminarCita(Cita cita) {
        List<Cita> citas = citaRepository.cargarCitas();
        citas.removeIf(c -> c.getPaciente().getId().equals(cita.getPaciente().getId()));
        citaRepository.guardarCita(citas);
    }

    @Override
    public List<Cita> listarCitasPorPaciente(Paciente paciente) {
        List<Cita> citas = citaRepository.cargarCitas();
        return citas.stream()
                .filter(c -> c.getPaciente().getId().equals(paciente.getId()))
                .collect(Collectors.toList());
    }
}
