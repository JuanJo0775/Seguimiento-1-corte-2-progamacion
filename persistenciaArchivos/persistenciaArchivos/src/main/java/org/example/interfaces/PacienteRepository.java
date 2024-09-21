package org.example.interfaces;

import org.example.domain.Paciente;

import java.util.List;

public interface PacienteRepository {
    void guardarPaciente(List<Paciente> pacientes);
    List<Paciente> cargarPacientes();
}

