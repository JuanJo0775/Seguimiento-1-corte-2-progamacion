package org.example.interfaces;

import org.example.domain.Cita;


import org.example.domain.Cita;

import java.util.List;

public interface CitaRepository {
    void guardarCita(List<Cita> citas);
    List<Cita> cargarCitas();
}

