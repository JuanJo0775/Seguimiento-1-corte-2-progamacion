package org.example.aplication.service;

import org.example.domain.Paciente;
import java.util.List;

public interface PacienteService {
    void registrarPaciente(Paciente paciente);
    void actualizarPaciente(Paciente paciente);
    void eliminarPaciente(Paciente paciente);
    List<Paciente> listarPacientes();
}

