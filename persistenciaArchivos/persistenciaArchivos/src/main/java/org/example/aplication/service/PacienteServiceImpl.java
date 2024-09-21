package org.example.aplication.service;

import org.example.domain.Paciente;
import org.example.interfaces.PacienteRepository;

import java.util.List;


public class PacienteServiceImpl implements PacienteService {
    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public void registrarPaciente(Paciente paciente) {
        List<Paciente> pacientes = pacienteRepository.cargarPacientes();
        pacientes.add(paciente);
        pacienteRepository.guardarPaciente(pacientes);
    }

    @Override
    public void actualizarPaciente(Paciente paciente) {
        List<Paciente> pacientes = pacienteRepository.cargarPacientes();
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId().equals(paciente.getId())) {
                pacientes.set(i, paciente);
                break;
            }
        }
        pacienteRepository.guardarPaciente(pacientes);
    }

    @Override
    public void eliminarPaciente(Paciente paciente) {
        List<Paciente> pacientes = pacienteRepository.cargarPacientes();
        pacientes.removeIf(p -> p.getId().equals(paciente.getId()));
        pacienteRepository.guardarPaciente(pacientes);
    }

    @Override
    public List<Paciente> listarPacientes() {
        return pacienteRepository.cargarPacientes();
    }
}
