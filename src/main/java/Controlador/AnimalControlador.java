package Controlador;

// las interacciones principales con Animal ocurren a través de RescatistaControlador y VeterinarioControlador

import Datos.AnimalDAO;
import Modelo.Animal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnimalControlador {
    private AnimalDAO animalDAO;

    public AnimalControlador() {
        this.animalDAO = new AnimalDAO();
    }

    /**
     * Busca un animal por su ID.
     * @param id ID del animal a buscar.
     * @return Optional que contiene el animal si se encuentra.
     */
    public Optional<Animal> buscarAnimalPorId(String id) {
        return animalDAO.buscarPorId(id);
    }

    /**
     * Obtiene todos los animales registrados en el sistema.
     * @return Lista de todos los animales.
     */
    public List<Animal> obtenerTodosLosAnimales() {
        return animalDAO.cargarTodos();
    }

    /**
     * Filtra animales por su estado de adopción.
     * @param estado El estado de adopción a filtrar.
     * @return Lista de animales con ese estado.
     */
    public List<Animal> filtrarAnimalesPorEstado(Animal.EstadoAdopcion estado) {
        return animalDAO.cargarTodos().stream()
                .filter(animal -> animal.getEstadoAdopcion() == estado)
                .collect(Collectors.toList());
    }
}
