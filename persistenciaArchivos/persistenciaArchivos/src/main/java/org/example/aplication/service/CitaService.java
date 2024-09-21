package org.example.aplication.service;

import org.example.domain.Cita;
import org.example.domain.Paciente;
import java.util.List;

public interface CitaService {
    void registrarCita(Cita cita);
    void actualizarCita(Cita cita);
    void eliminarCita(Cita cita);
    List<Cita> listarCitasPorPaciente(Paciente paciente);
}

