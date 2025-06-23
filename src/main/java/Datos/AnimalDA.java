package Datos;

import Modelo.Animal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AnimalDA {
    private static final String FILE_PATH = "animales.json";
    private ObjectMapper objectMapper;

    public AnimalDA() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public void guardarAnimales(ArrayList<Animal> animales) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), animales);
            System.out.println("Animales guardados exitosamente en " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error al guardar animales: " + e.getMessage());

            throw new RuntimeException("No se pudieron guardar los animales", e);
        }
    }

    public ArrayList<Animal> cargarAnimales() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Archivo de animales no encontrado. Creando una lista vacía.");
            return new ArrayList<>();
        }
        try {

            Animal[] animalesArray = objectMapper.readValue(file, Animal[].class);
            return new ArrayList<>(Arrays.asList(animalesArray));
        } catch (IOException e) {
            System.err.println("Error al cargar animales: " + e.getMessage());

            throw new RuntimeException("No se pudieron cargar los animales", e);
        }
    }

    public void agregarAnimal(Animal nuevoAnimal) {
        ArrayList<Animal> animales = cargarAnimales();
        animales.add(nuevoAnimal);
        guardarAnimales(animales);
    }

    public void actualizarAnimal(Animal animalActualizado) {
        ArrayList<Animal> animales = cargarAnimales();
        boolean encontrada = false;
        for (int i = 0; i < animales.size(); i++) {
            if (animales.get(i).getId().equals(animalActualizado.getId())) {
                animales.set(i, animalActualizado);
                encontrada = true;
                break;
            }
        }
        if (encontrada) {
            guardarAnimales(animales);
        } else {
            System.err.println("Error: Animal con ID " + animalActualizado.getId() + " no encontrado para actualizar.");

        }
    }

    public Animal buscarAnimalPorId(String id) {
        ArrayList<Animal> animales = cargarAnimales();
        for (Animal animal : animales) {
            if (animal.getId().equals(id)) {
                return animal;
            }
        }
        return null;
    }

    public boolean eliminarAnimal(String id) {
        ArrayList<Animal> animales = cargarAnimales();
        boolean removido = animales.removeIf(animal -> animal.getId().equals(id));
        if (removido) {
            guardarAnimales(animales);
            System.out.println("Animal con ID " + id + " eliminado exitosamente.");
        } else {
            System.out.println("No se encontró el animal con ID " + id + " para eliminar.");
        }
        return removido;
    }
}