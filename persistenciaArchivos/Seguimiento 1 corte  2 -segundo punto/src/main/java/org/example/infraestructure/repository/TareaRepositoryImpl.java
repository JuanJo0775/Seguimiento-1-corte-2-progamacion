package org.example.infraestructure.repository;

import org.example.domain.Empleado;
import org.example.domain.Tarea;
import org.example.interfaces.repository.TareaInterfazRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TareaRepositoryImpl implements TareaInterfazRepository {
    private static final String FILE_NAME = "tareas.txt";

    @Override
    public List<Tarea> cargar() {
        List<Tarea> tareas = new ArrayList<>();
        try (ObjectInputStream oIs = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            tareas = (List<Tarea>) oIs.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado, se creará uno nuevo.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tareas;
    }

    @Override
    public Tarea buscarTarea(String titulo, Empleado empleado) {
        List<Tarea> tareas = cargar();
        for (Tarea tarea : tareas) {
            if (tarea.getTitulo().equals(titulo) && tarea.getEmpleado().equals(empleado)) {
                return tarea;
            }
        }
        return null;
    }

    @Override
    public void guardar(Tarea tarea) {
        List<Tarea> tareas = cargar();
        tareas.add(tarea);
        guardarLista(tareas);
    }

    @Override
    public void update(Tarea tarea) {
        List<Tarea> tareas = cargar();
        for (int i = 0; i < tareas.size(); i++) {  // Corrección del índice
            if (tareas.get(i).getTitulo().equals(tarea.getTitulo()) && tareas.get(i).getEmpleado().equals(tarea.getEmpleado())) {
                tareas.set(i, tarea); // Actualiza la tarea
                break;
            }
        }
        guardarLista(tareas);
    }

    @Override
    public void eliminar(String titulo, Empleado empleado) {
        List<Tarea> tareas = cargar();
        tareas.removeIf(tarea -> tarea.getTitulo().equals(titulo) && tarea.getEmpleado().equals(empleado)); // Corregido
        guardarLista(tareas);
    }

    // Método privado para guardar la lista completa de tareas en el archivo
    private void guardarLista(List<Tarea> tareas) {
        try (ObjectOutputStream oOs = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {  // Corregido
            oOs.writeObject(tareas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
