package Controlador;

import Datos.AnimalDA;
import Modelo.Animal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class AnimalControlador {

    private final AnimalDA animalDA;

    public AnimalControlador(AnimalDA animalDA) {
        this.animalDA = animalDA;
    }

    // para registrar un nuevo animal
    // para incluir fechaHoraRescate y idRescatista se ajusto
    public boolean registrarAnimal(String especie, String raza, String sexo, String estadoSalud,
                                   String lugarEncontrado, LocalDateTime fechaHoraRescate, // Corregido: LocalDateTime
                                   int edadAproximadaAnios, String idRescatista) { // Añadido y corregido a
        try {
            Animal nuevoAnimal = new Animal(especie, raza, sexo, estadoSalud,
                    lugarEncontrado, fechaHoraRescate,
                    edadAproximadaAnios, idRescatista);
            animalDA.agregarAnimal(nuevoAnimal);
            System.out.println("Animal registrado exitosamente con ID: " + nuevoAnimal.getId());
            return true;
        } catch (RuntimeException e) {
            System.err.println("Error al registrar animal: " + e.getMessage());
            return false; // fallo en la accion
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
            Optional<Animal> animalOptional = animalDA.buscarAnimalPorId(id);
            if (animalOptional.isPresent()) {
                return animalOptional.get(); // Si está presente, devuelve el Animal
            } else {
                System.out.println("Error: Animal con ID " + id + " no encontrado.");
                return null; // Si no se encuentra, devuelve null
            }
        } catch (RuntimeException e) {
            System.err.println("Error al buscar animal por ID: " + e.getMessage());
            return null;
        }
    }

    //para actualizar un animal existente
    // para que el idRescatista pueda ser actualizado si es necesario,
    // o para pasar el existente
    public boolean actualizarAnimal(String id, String especie, String raza, String sexo,
                                    String estadoSalud, String lugarEncontrado,
                                    LocalDateTime fechaHoraRescate, // Corregido: LocalDateTime
                                    int edadAproximadaAnios, // Corregido a Anios
                                    String diagnostico, String idRescatista) {
        try {
            Optional<Animal> animalOptional = animalDA.buscarAnimalPorId(id);

            if (animalOptional.isPresent()) {
                Animal animalAActualizar = animalOptional.get();

                // Actualiza las propiedades del animal
                animalAActualizar.setEspecie(especie);
                animalAActualizar.setRaza(raza);
                animalAActualizar.setSexo(sexo);
                animalAActualizar.setEstadoSalud(estadoSalud);
                animalAActualizar.setLugarEncontrado(lugarEncontrado);
                animalAActualizar.setFechaHoraRescate(fechaHoraRescate);
                animalAActualizar.setEdadAproximadaAnios(edadAproximadaAnios); // Corregido
                animalAActualizar.setDiagnostico(diagnostico);
                animalAActualizar.setIdRescatista(idRescatista);

                // Llamada a actualizarAnimal de AnimalDA
                animalDA.actualizarAnimal(animalAActualizar);

                System.out.println("Animal con ID " + id + " actualizado exitosamente.");
                return true; // true si la actualización fue exitosa
            } else {
                System.out.println("Error: Animal con ID " + id + " no encontrado para actualizar.");
                return false; // false si el animal no se encontró
            }
        } catch (Exception e) { // Captura una excepción más general para errores inesperados
            System.err.println("Error al actualizar animal con ID " + id + ": " + e.getMessage());
            return false;
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