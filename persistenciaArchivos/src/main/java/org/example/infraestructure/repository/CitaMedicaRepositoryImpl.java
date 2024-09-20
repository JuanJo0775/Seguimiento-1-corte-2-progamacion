package org.example.infraestructure.repository;

import org.example.domain.CitaMedica;
import org.example.interfaces.CitaMedicaRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CitaMedicaRepositoryImpl implements CitaMedicaRepository {
    private static final String FILE_NAME = "Citas.dat";

    @Override
    public List<CitaMedica> findAll() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<CitaMedica>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public CitaMedica findBynumeroCita(int numeroCita) {
        return null;
    }

    @Override
    public CitaMedica findBynumeroCita(int numeroCita) {
        return findAll().stream()
                .filter(p -> p.getNumeroCita() == numeroCita)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(CitaMedica citaMedica) {
        List<CitaMedica> productos = findAll();
        productos.add(citaMedica);
        saveAll((citaMedica);
    }

    @Override
    public void update(CitaMedica citaMedica) {
        List<CitaMedica> productos = findAll();
        productos = productos.stream()
                .map(p -> p.getNumeroCita() == CitaMedica.getNumeroCita() ? citaMedica : p)
                .collect(Collectors.toList());
        saveAll(productos);
    }

    @Override
    public void delete(int numeroCita) {
        List<CitaMedica> productos = findAll();
        productos = productos.stream()
                .filter(p -> p.getNumeroCita() != numeroCita())
                .collect(Collectors.toList());
        saveAll(productos);
    }

    private void saveAll(List<CitaMedica> productos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(productos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
