package org.example.aplication;

import org.example.domain.Cita;
import org.example.domain.Paciente;
import org.example.infraestructure.repository.CitaRepositoryImpl;
import org.example.infraestructure.repository.PacienteRepositoryImpl;
import org.example.aplication.service.CitaService;
import org.example.aplication.service.CitaServiceImpl;
import org.example.aplication.service.PacienteService;
import org.example.aplication.service.PacienteServiceImpl;
import org.example.interfaces.CitaRepository;
import org.example.interfaces.PacienteRepository;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Objects;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static PacienteService pacienteService;
    private static CitaService citaService;

    public static void main(String[] args) {
        // Inicialización de repositorios e inyección de dependencias
        PacienteRepository pacienteRepository = new PacienteRepositoryImpl();
        CitaRepository citaRepository = new CitaRepositoryImpl();
        pacienteService = new PacienteServiceImpl(pacienteRepository);
        citaService = new CitaServiceImpl(citaRepository);

        int opcion;
        do {
            mostrarMenu();
            opcion = obtenerOpcionMenu();

            switch (opcion) {
                case 1:
                    registrarPaciente();
                    break;
                case 2:
                    actualizarPaciente();
                    break;
                case 3:
                    eliminarPaciente();
                    break;
                case 4:
                    mostrarPacientes();
                    break;
                case 5:
                    registrarCita();
                    break;
                case 6:
                    eliminarCita();
                    break;
                case 7:
                    mostrarCitasPorPaciente();
                    break;
                case 8:
                    actualizarCita();
                    break;
                case 9:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 9);
    }

    private static void mostrarMenu() {
        System.out.println("Menú:");
        System.out.println("1. Registrar nuevo paciente");
        System.out.println("2. Actualizar datos del paciente");
        System.out.println("3. Eliminar un paciente");
        System.out.println("4. Mostrar todos los pacientes");
        System.out.println("5. Registrar nueva cita");
        System.out.println("6. Eliminar una cita");
        System.out.println("7. Mostrar citas por paciente");
        System.out.println("8. Actualizar datos de una cita");
        System.out.println("9. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int obtenerOpcionMenu() {
        int opcion = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor ingrese un número.");
                scanner.nextLine(); // Limpiar el buffer
            }
        }
        return opcion;
    }

    private static void registrarPaciente() {
        System.out.print("Ingrese el ID del paciente: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese la edad: ");
        int edad = obtenerEdad();
        System.out.print("Ingrese el género: ");
        String genero = scanner.nextLine();
        System.out.print("Ingrese la dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Ingrese el teléfono: ");
        String telefono = scanner.nextLine();

        Paciente paciente = new Paciente(id, nombre, apellido, edad, genero, direccion, telefono);
        pacienteService.registrarPaciente(paciente);
        System.out.println("Paciente registrado exitosamente.");
    }

    private static int obtenerEdad() {
        int edad = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                edad = scanner.nextInt();
                scanner.nextLine(); // Consumir salto de línea
                if (edad <= 0) {
                    System.out.println("La edad debe ser un número positivo. Intente nuevamente.");
                } else {
                    entradaValida = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor ingrese un número.");
                scanner.nextLine(); // Limpiar buffer
            }
        }
        return edad;
    }

    private static void actualizarPaciente() {
        String id;
        Paciente paciente;
        do {
            System.out.print("Ingrese el ID del paciente a actualizar: ");
            id = scanner.nextLine();
            String finalId = id;
            paciente = pacienteService.listarPacientes().stream()
                    .filter(p -> Objects.equals(p.getId(), finalId))
                    .findFirst().orElse(null);

            if (paciente == null) {
                System.out.println("ID no válido. Intente nuevamente o escriba 'salir' para volver al menú.");
                if (id.equalsIgnoreCase("salir")) return;
            }
        } while (paciente == null);

        actualizarDatosPaciente(paciente);
    }

    private static void actualizarDatosPaciente(Paciente paciente) {
        System.out.print("Ingrese el nuevo nombre (actual: " + paciente.getNombre() + "): ");
        paciente.setNombre(scanner.nextLine());
        System.out.print("Ingrese el nuevo apellido (actual: " + paciente.getApellido() + "): ");
        paciente.setApellido(scanner.nextLine());
        System.out.print("Ingrese la nueva edad (actual: " + paciente.getEdad() + "): ");
        paciente.setEdad(obtenerEdad());
        System.out.print("Ingrese el nuevo género (actual: " + paciente.getGenero() + "): ");
        paciente.setGenero(scanner.nextLine());
        System.out.print("Ingrese la nueva dirección (actual: " + paciente.getDireccion() + "): ");
        paciente.setDireccion(scanner.nextLine());
        System.out.print("Ingrese el nuevo teléfono (actual: " + paciente.getTelefono() + "): ");
        paciente.setTelefono(scanner.nextLine());

        pacienteService.actualizarPaciente(paciente);
        System.out.println("Paciente actualizado exitosamente.");
    }

    private static void eliminarPaciente() {
        String id;
        Paciente paciente;
        do {
            System.out.print("Ingrese el ID del paciente a eliminar: ");
            id = scanner.nextLine();
            String finalId = id;
            paciente = pacienteService.listarPacientes().stream()
                    .filter(p -> Objects.equals(p.getId(), finalId))
                    .findFirst().orElse(null);

            if (paciente == null) {
                System.out.println("ID no válido. Intente nuevamente o escriba 'salir' para volver al menú.");
                if (id.equalsIgnoreCase("salir")) return;
            }
        } while (paciente == null);

        pacienteService.eliminarPaciente(paciente);
        System.out.println("Paciente eliminado exitosamente.");
    }

    private static void mostrarPacientes() {
        List<Paciente> pacientes = pacienteService.listarPacientes();
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
        } else {
            pacientes.forEach(System.out::println);
        }
    }

    private static void registrarCita() {
        String id;
        Paciente paciente;
        do {
            System.out.print("Ingrese el ID del paciente: ");
            id = scanner.nextLine();
            String finalId = id;
            paciente = pacienteService.listarPacientes().stream()
                    .filter(p -> Objects.equals(p.getId(), finalId))
                    .findFirst().orElse(null);

            if (paciente == null) {
                System.out.println("ID no válido. Intente nuevamente o escriba 'salir' para volver al menú.");
                if (id.equalsIgnoreCase("salir")) return;
            }
        } while (paciente == null);

        System.out.print("Ingrese la fecha de la cita: ");
        String fecha = scanner.nextLine();
        System.out.print("Ingrese la hora de la cita: ");
        String hora = scanner.nextLine();
        System.out.print("Ingrese el motivo de la cita: ");
        String motivo = scanner.nextLine();

        Cita cita = new Cita(fecha, hora, motivo, paciente);
        citaService.registrarCita(cita);
        System.out.println("Cita registrada exitosamente.");
    }

    private static void eliminarCita() {
        String id;
        Paciente paciente;
        do {
            System.out.print("Ingrese el ID del paciente: ");
            id = scanner.nextLine();
            String finalId = id;
            paciente = pacienteService.listarPacientes().stream()
                    .filter(p -> Objects.equals(p.getId(), finalId))
                    .findFirst().orElse(null);

            if (paciente == null) {
                System.out.println("ID no válido. Intente nuevamente o escriba 'salir' para volver al menú.");
                if (id.equalsIgnoreCase("salir")) return;
            }
        } while (paciente == null);

        List<Cita> citas = citaService.listarCitasPorPaciente(paciente);
        if (citas.isEmpty()) {
            System.out.println("No hay citas registradas para este paciente.");
            return;
        }

        for (int i = 0; i < citas.size(); i++) {
            System.out.println((i + 1) + ". " + citas.get(i));
        }

        int opcion;
        do {
            System.out.print("Seleccione el número de la cita a eliminar: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            if (opcion < 1 || opcion > citas.size()) {
                System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion < 1 || opcion > citas.size());

        citaService.eliminarCita(citas.get(opcion - 1));
        System.out.println("Cita eliminada exitosamente.");
    }

    private static void mostrarCitasPorPaciente() {
        String id;
        Paciente paciente;
        do {
            System.out.print("Ingrese el ID del paciente: ");
            id = scanner.nextLine();
            String finalId = id;
            paciente = pacienteService.listarPacientes().stream()
                    .filter(p -> Objects.equals(p.getId(), finalId))
                    .findFirst().orElse(null);

            if (paciente == null) {
                System.out.println("ID no válido. Intente nuevamente o escriba 'salir' para volver al menú.");
                if (id.equalsIgnoreCase("salir")) return;
            }
        } while (paciente == null);

        List<Cita> citas = citaService.listarCitasPorPaciente(paciente);
        if (citas.isEmpty()) {
            System.out.println("No hay citas registradas para este paciente.");
        } else {
            citas.forEach(System.out::println);
        }
    }

    private static void actualizarCita() {
        String id;
        Paciente paciente;
        do {
            System.out.print("Ingrese el ID del paciente: ");
            id = scanner.nextLine();
            String finalId = id;
            paciente = pacienteService.listarPacientes().stream()
                    .filter(p -> Objects.equals(p.getId(), finalId))
                    .findFirst().orElse(null);

            if (paciente == null) {
                System.out.println("ID no válido. Intente nuevamente o escriba 'salir' para volver al menú.");
                if (id.equalsIgnoreCase("salir")) return;
            }
        } while (paciente == null);

        List<Cita> citas = citaService.listarCitasPorPaciente(paciente);
        if (citas.isEmpty()) {
            System.out.println("No hay citas registradas para este paciente.");
            return;
        }

        for (int i = 0; i < citas.size(); i++) {
            System.out.println((i + 1) + ". " + citas.get(i));
        }

        int opcion;
        do {
            System.out.print("Seleccione el número de la cita a actualizar: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            if (opcion < 1 || opcion > citas.size()) {
                System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion < 1 || opcion > citas.size());

        actualizarDatosCita(citas.get(opcion - 1));
    }

    private static void actualizarDatosCita(Cita cita) {
        System.out.print("Ingrese la nueva fecha (actual: " + cita.getFecha() + "): ");
        cita.setFecha(scanner.nextLine());
        System.out.print("Ingrese la nueva hora (actual: " + cita.getHora() + "): ");
        cita.setHora(scanner.nextLine());
        System.out.print("Ingrese el nuevo motivo (actual: " + cita.getMotivo() + "): ");
        cita.setMotivo(scanner.nextLine());

        citaService.actualizarCita(cita);
        System.out.println("Cita actualizada exitosamente.");
    }
}
