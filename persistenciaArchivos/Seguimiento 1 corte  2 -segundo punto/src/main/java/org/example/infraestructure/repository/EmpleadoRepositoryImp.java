package org.example.infraestructure.repository;

import org.example.domain.Empleado;
import org.example.interfaces.repository.EmpleadoInterfazRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepositoryImp implements EmpleadoInterfazRepository {
    private static final String FILE_NAME = "empleados.txt";

    @Override
    public List<Empleado> cargar() {
        List<Empleado> empleados = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            empleados = (List<Empleado>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado, se creará uno nuevo.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    @Override
    public Empleado buscarEmpleado(String nombre, String apellido, String cargo) {
        List<Empleado> empleados = cargar();
        for (Empleado empleado : empleados) {
            if (empleado.getNombre().equals(nombre) && empleado.getApellido().equals(apellido) && empleado.getCargo().equals(cargo)) {
                return empleado;
            }
        }
        return null;
    }

    @Override
    public void guardar(Empleado empleado) {
        List<Empleado> empleados = cargar();
        empleados.add(empleado);
        guardarLista(empleados);
    }

    @Override
    public void update(Empleado empleado) {
        List<Empleado> empleados = cargar();
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getNombre().equals(empleado.getNombre()) &&
                    empleados.get(i).getApellido().equals(empleado.getApellido()) &&
                    empleados.get(i).getCargo().equals(empleado.getCargo())) {
                empleados.set(i, empleado);
                break;
            }
        }
        guardarLista(empleados);
    }

    @Override
    public void eliminar(String nombre, String apellido, String cargo) {
        List<Empleado> empleados = cargar();
        empleados.removeIf(emp -> emp.getNombre().equals(nombre) && emp.getApellido().equals(apellido) && emp.getCargo().equals(cargo));
        guardarLista(empleados);
    }

    // Método privado para guardar la lista completa de empleados en el archivo
    private void guardarLista(List<Empleado> empleados) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(empleados);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
