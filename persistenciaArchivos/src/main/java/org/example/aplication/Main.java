package org.example.aplication;

import org.example.aplication.service.PacienteService;
import org.example.aplication.service.PacienteServiceImpl;
import org.example.domain.Paciente;
import org.example.infraestructure.repository.PacienteRepositoryImpl;
import org.example.interfaces.PacienteRepository;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PacienteService pacienteService;

    static {
        PacienteRepository pacienteRepository = new PacienteRepositoryImpl();
        pacienteService = new PacienteServiceImpl(pacienteRepository);
    }

    public static void main(String[] args) {

        
        boolean salir = false;
        while (!salir) {
            System.out.println("1. Listar pacientes");
            System.out.println("2. Crear paciente");
            System.out.println("3. Actualizar paciente");
            System.out.println("4. Eliminar paciente");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    listarPacientes();
                    break;
                case 2:
                    crearPaciente();
                    break;
                case 3:
                    actualizarPaciente();
                    break;
                case 4:
                    eliminarpaciente();
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida \n");
            }
        }
    }

    private static void listarPacientes() {
        List<Paciente> pacientes = pacienteService.findAll();
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados. \n");
        } else {
            System.out.println("Listado de pacientes:\n");
            for (Paciente paciente : pacientes) {
                System.out.println(paciente);
            }
        }
    }

    private static void crearPaciente() {
        System.out.print("Ingrese el id del paciente: \n");
        int id  = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese el nombre del paciente: \n");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la edad del paciente: \n");
        int edad = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        Paciente paciente = new Paciente();
        paciente.setId(id);
        paciente.setNombre(nombre);
        paciente.setEdad(edad);

        try {
            pacienteService.save(paciente);
            System.out.println("paciente creado exitosamente.\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void actualizarPaciente() {
        System.out.print("Ingrese el ID del paciente a actualizar: \n");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        Paciente paciente = pacienteService.findById(id);
        if (paciente == null) {
            System.out.println("No se encontró el paciente con ID " + id + "\n");
            return;
        }

        System.out.print("Ingrese el nuevo nombre del paciente (dejar en blanco para no cambiar): ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) {
            paciente.setNombre(nombre);
        }

        System.out.print("Ingrese la edad del paciente (0 para no cambiar): ");
        int edad = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        if (edad > 0) {
            paciente.setEdad(edad);
        }

        try {
            pacienteService.update(paciente);
            System.out.println("paciente actualizado exitosamente.\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    private static void eliminarpaciente() {
        System.out.print("Ingrese el ID del paciente a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        Paciente paciente = pacienteService.findById(id);
        if (paciente == null) {
            System.out.println("No se encontró el paciente con ID " + id + "\n");
            return;
        }

        pacienteService.delete(id);
        System.out.println("paciente eliminado exitosamente.\n");
    }
}
