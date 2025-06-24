package Datos;

import Modelo.Animal;
import com.fasterxml.jackson.databind.ObjectMapper; //mapeo entre JSON y objetos Java
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List; //usamos List en otros lados
import java.util.Optional;

public class AnimalDA {

    private static final String FILE_PATH = "animales.json";
    private ObjectMapper objectMapper;


    public AnimalDA() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule()); // Para manejar LocalDateTime
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        inicializarArchivo();
    }

    // para asegurar que el archivo JSON esté siempre listo
    private void inicializarArchivo() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            try {
                // Escribir un array json vacio
                objectMapper.writeValue(file, new ArrayList<Animal>());
                System.out.println("Archivo de animales inicializado: " + FILE_PATH);
            } catch (IOException e) {
                System.err.println("Error al inicializar el archivo de animales: " + e.getMessage());
                throw new RuntimeException("No se pudo inicializar el archivo de animales", e);
            }
        }
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
        try {
            if (file.exists() && file.length() > 0) {
                Animal[] animalesArray = objectMapper.readValue(file, Animal[].class);
                return new ArrayList<>(Arrays.asList(animalesArray));
            }
            return new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al cargar animales: " + e.getMessage());
            throw new RuntimeException("No se pudieron cargar los animales", e);
        }
    }

    public void agregarAnimal(Animal nuevoAnimal) {
        ArrayList<Animal> animales = cargarAnimales();
        //verifiquemos si existen ids duplicados
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

    //para devolver Optional<Animal>
    public Optional<Animal> buscarAnimalPorId(String id) {
        ArrayList<Animal> animales = cargarAnimales();
        for (Animal animal : animales) {
            if (animal.getId().equals(id)) {
                return Optional.of(animal);
            }
        }
        return Optional.empty();
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