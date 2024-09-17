package org.example.infraestructure.repository;

import org.example.domain.Paciente;
import org.example.interfaces.PacienteRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PacienteRepositoryImpl implements PacienteRepository {
    private static final String FILE_NAME = "productos.dat";

    @Override
    public List<Paciente> findAll() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<Paciente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Paciente findById(int id) {
        return findAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Paciente producto) {
        List<Paciente> productos = findAll();
        productos.add(producto);
        saveAll(productos);
    }

    @Override
    public void update(Paciente producto) {
        List<Paciente> productos = findAll();
        productos = productos.stream()
                .map(p -> p.getId() == producto.getId() ? producto : p)
                .collect(Collectors.toList());
        saveAll(productos);
    }

    @Override
    public void delete(int id) {
        List<Paciente> productos = findAll();
        productos = productos.stream()
                .filter(p -> p.getId() != id)
                .collect(Collectors.toList());
        saveAll(productos);
    }

    private void saveAll(List<Paciente> productos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(productos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
