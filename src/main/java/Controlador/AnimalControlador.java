package Controlador;

import Datos.AnimalDA;
import Modelo.Animal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AnimalControlador {
    private final AnimalDA animalDA;

    public AnimalControlador(AnimalDA animalDA) {
        this.animalDA = animalDA;
    }

    public void registrarAnimal(String especie, String raza, String sexo, String estadoSalud,
                                String lugarEncontrado, LocalDateTime fechaHoraRescate, int edadAproximadaAnios) {
        try {

            Animal nuevoAnimal = new Animal(especie, raza, sexo, estadoSalud,
                    lugarEncontrado, fechaHoraRescate, edadAproximadaAnios);

            animalDA.agregarAnimal(nuevoAnimal);
            System.out.println("Animal registrado exitosamente con ID: " + nuevoAnimal.getId());
        } catch (RuntimeException e) {

            System.err.println("Error al registrar animal: " + e.getMessage());
        }
    }

    public ArrayList<Animal> obtenerTodosLosAnimales() {
        try {
            return animalDA.cargarAnimales();
        } catch (RuntimeException e) {
            System.err.println("Error al obtener animales: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Animal buscarAnimalPorId(String id) {
        try {
            return animalDA.buscarAnimalPorId(id);
        } catch (RuntimeException e) {
            System.err.println("Error al buscar animal por ID: " + e.getMessage());
            return null;
        }
    }

    public void actualizarAnimal(String id, String especie, String raza, String sexo, String estadoSalud,
                                 String lugarEncontrado, LocalDateTime fechaHoraRescate, int edadAproximadaAnios) {
        try {
            Animal animalExistente = animalDA.buscarAnimalPorId(id);
            if (animalExistente != null) {

                animalExistente.setEspecie(especie);
                animalExistente.setRaza(raza);
                animalExistente.setSexo(sexo);
                animalExistente.setEstadoSalud(estadoSalud);
                animalExistente.setLugarEncontrado(lugarEncontrado);
                animalExistente.setFechaHoraRescate(fechaHoraRescate);
                animalExistente.setEdadAproximadaAnios(edadAproximadaAnios);

                animalDA.actualizarAnimal(animalExistente);
                System.out.println("Animal con ID " + id + " actualizado exitosamente.");
            } else {
                System.out.println("Error: Animal con ID " + id + " no encontrado para actualizar.");
            }
        } catch (RuntimeException e) {
            System.err.println("Error al actualizar animal: " + e.getMessage());
        }
    }

    public boolean eliminarAnimal(String id) {
        try {
            return animalDA.eliminarAnimal(id);
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar animal: " + e.getMessage());
            return false;
        }
    }
}