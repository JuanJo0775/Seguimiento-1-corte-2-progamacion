package org.example.infraestructure.repository;

import org.example.domain.Paciente;
import org.example.interfaces.PacienteRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepositoryImpl implements PacienteRepository {
    private static final String FILE_PATH = "pacientes.txt";

    @Override
    public void guardarPaciente(List<Paciente> pacientes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Paciente paciente : pacientes) {
                bw.write(paciente.getId() + "," + paciente.getNombre() + "," + paciente.getApellido() + "," +
                        paciente.getEdad() + "," + paciente.getGenero() + "," + paciente.getDireccion() + "," + paciente.getTelefono());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }


    @Override
    public List<Paciente> cargarPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = br.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(",");


                if (datos.length < 7) { // Se esperan al menos 7 campos: ID, nombre, apellido, edad, género, dirección, teléfono
                    System.out.println("Error: Datos incompletos en la línea: " + linea);
                    continue;
                }


                int edad;
                try {
                    edad = Integer.parseInt(datos[3]);
                } catch (NumberFormatException e) {
                    System.out.println("Error: La edad '" + datos[3] + "' no es un número válido en la línea: " + linea);
                    continue; // Saltar a la siguiente línea
                }


                Paciente paciente = new Paciente(datos[0], datos[1], datos[2], edad, datos[4], datos[5], datos[6]);
                pacientes.add(paciente);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
        return pacientes;
    }


}
