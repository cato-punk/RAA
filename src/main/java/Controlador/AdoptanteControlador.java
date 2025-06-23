package Controlador;

import Modelo.Adoptante;
import Modelo.Animal;
import Datos.PersonaDA;
import Datos.AnimalDA;
import Modelo.Persona;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdoptanteControlador {

    private PersonaDA personaDA;
    private AnimalDA animalDA;
    private Adoptante adoptanteActual; // sesión del adoptante

    public AdoptanteControlador(PersonaDA personaDA, AnimalDA animalDA) {
        this.personaDA = personaDA;
        this.animalDA = animalDA;
    }

    public Adoptante getAdoptanteActual() {
        return adoptanteActual;
    }

    public boolean registrarAdoptante(String nombre, String rut, LocalDate fechaNacimiento,
                                      String direccion, String numeroTelefono,
                                      String correoElectronico, String preferencias) {
        if (personaDA.existeCorreo(correoElectronico)) {
            System.out.println("Error: El correo electrónico ya está registrado.");
            return false;
        }

        Adoptante nuevoAdoptante = new Adoptante(nombre, rut, fechaNacimiento, direccion,
                numeroTelefono, correoElectronico, preferencias);
        personaDA.agregarPersona(nuevoAdoptante); // Usar agregarPersona en lugar de guardarPersona
        System.out.println("Adoptante registrado exitosamente con ID: " + nuevoAdoptante.getId());
        return true;
    }

    public boolean iniciarSesion(String correoElectronico) {
        Persona persona = personaDA.buscarPersonaPorCorreo(correoElectronico);
        if (persona instanceof Adoptante) {
            this.adoptanteActual = (Adoptante) persona;
            return true;
        }
        this.adoptanteActual = null; // Asegurarse de limpiar la sesión si falla
        return false;
    }

    public void cerrarSesion() {
        this.adoptanteActual = null;
        System.out.println("Sesión de adoptante cerrada.");
    }

    // obtener animales disponibles para adopción
    public List<Animal> obtenerAnimalesDisponibles() {
        return animalDA.cargarAnimales().stream()
                .filter(animal -> "disponible".equalsIgnoreCase(animal.getEstadoSalud()))
                .collect(Collectors.toList());
    }

    // para adoptar un animal
    public boolean adoptarAnimal(String idAnimal) {
        if (adoptanteActual == null) {
            System.out.println("Debe iniciar sesión como adoptante para adoptar un animal.");
            return false;
        }

        Animal animalAAdoptar = animalDA.buscarAnimalPorld(idAnimal);
        if (animalAAdoptar == null) {
            System.out.println("Error: Animal con ID " + idAnimal + " no encontrado.");
            return false;
        }

        if (!"disponible".equalsIgnoreCase(animalAAdoptar.getEstadoSalud())) {
            System.out.println("Error: El animal con ID " + idAnimal + " no está disponible para adopción.");
            return false;
        }

        animalAAdoptar.setEstadoSalud("adoptado");
        animalAAdoptar.setIdAdoptante(adoptanteActual.getId()); // ¡Asignar el ID del adoptante!

        try {
            animalDA.actualizarAnimal(animalAAdoptar);
            System.out.println("¡Felicidades! Has adoptado exitosamente a " + animalAAdoptar.getEspecie() + " (ID: " + idAnimal + ").");
            return true;
        } catch (RuntimeException e) {
            System.err.println("Error al procesar la adopción: " + e.getMessage());
            return false;
        }
    }

    // para obtener el historial de adopciones del adoptante actual
    public List<Animal> obtenerHistorialAdopciones() {
        if (adoptanteActual == null) {
            System.out.println("Debe iniciar sesión como adoptante para ver su historial.");
            return new ArrayList<>();
        }

        return animalDA.cargarAnimales().stream()
                .filter(animal -> adoptanteActual.getId().equals(animal.getIdAdoptante()))
                .collect(Collectors.toList());
    }
}