package org.example.infraestructure.repository;

import org.example.domain.Cita;
import org.example.domain.Paciente;
import org.example.interfaces.CitaRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CitaRepositoryImpl implements CitaRepository {
    private static final String FILE_PATH = "citas.txt";

    @Override
    public void guardarCita(List<Cita> citas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Cita cita : citas) {
                Paciente paciente = cita.getPaciente();
                bw.write(cita.getFecha() + "," + cita.getHora() + "," + cita.getMotivo() + "," +
                        paciente.getId() + "," + paciente.getNombre() + "," + paciente.getApellido() + "," +
                        paciente.getEdad() + "," + paciente.getGenero() + "," + paciente.getDireccion() + "," +
                        paciente.getTelefono());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    @Override
    public List<Cita> cargarCitas() {
        List<Cita> citas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                // Asegurarse de que hay suficientes datos
                if (datos.length < 9) {
                    System.out.println("Datos incompletos en la línea: " + linea);
                    continue; // Salta esta línea
                }
                try {
                    String idPaciente = datos[3]; // ID del paciente
                    String nombre = datos[4];
                    String apellido = datos[5];
                    int edad = Integer.parseInt(datos[6]);
                    String genero = datos[7];
                    String direccion = datos[8];
                    String telefono = datos[9];

                    Paciente paciente = new Paciente(idPaciente, nombre, apellido, edad, genero, direccion, telefono);
                    Cita cita = new Cita(datos[0], datos[1], datos[2], paciente);
                    citas.add(cita);
                } catch (NumberFormatException e) {
                    System.out.println("Error al convertir la edad a entero en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
        return citas;
    }

}
