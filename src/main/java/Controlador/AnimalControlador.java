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

    // para registrar un nuevo animal
    // para incluir fechaHoraRescate y idRescatista se ajustoo
    public boolean registrarAnimal(String especie, String raza, String sexo, String estadoSalud,
                                   String lugarEncontrado, LocalDateTime fechaHoraRescate, // correcion
                                   int edadAproximadaAnios, String idRescatista) { // añadido
        try {
            Animal nuevoAnimal = new Animal(especie, raza, sexo, estadoSalud,
                    lugarEncontrado, fechaHoraRescate,
                    edadAproximadaAnios, idRescatista);//nuevo constructor
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

    public Animal buscarAnimalPorld(String id) {
        try {
            return animalDA.buscarAnimalPorld(id);
        } catch (RuntimeException e) {
            System.err.println("Error al buscar animal por ID: " + e.getMessage());
            return null;
        }
    }

    //para actualizar un animal existente
    // para que el idRescatista pueda ser actualizado si es necesario,
    // o para pasar el existente.
    public boolean actualizarAnimal(String id, String especie, String raza, String sexo, String estadoSalud,
                                    String lugarEncontrado, LocalDateTime fechaHoraRescate,
                                    int edadAproximadaAnios, String diagnostico, String idRescatista) {
        try {
            Animal animalExistente = animalDA.buscarAnimalPorld(id);
            if (animalExistente != null) {
                animalExistente.setEspecie(especie);
                animalExistente.setRaza(raza);
                animalExistente.setSexo(sexo);
                animalExistente.setEstadoSalud(estadoSalud);
                animalExistente.setLugarEncontrado(lugarEncontrado);
                animalExistente.setFechaHoraRescate(fechaHoraRescate);
                animalExistente.setEdadAproximadaAnios(edadAproximadaAnios);
                animalExistente.setDiagnostico(diagnostico);
                animalExistente.setIdRescatista(idRescatista);

                animalDA.actualizarAnimal(animalExistente);
                System.out.println("Animal con ID " + id + " actualizado exitosamente.");
                return true;
            } else {
                System.out.println("Error: Animal con ID " + id + " no encontrado para actualizar.");
                return false;
            }
        } catch (RuntimeException e) {
            System.err.println("Error al actualizar animal: " + e.getMessage());
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
