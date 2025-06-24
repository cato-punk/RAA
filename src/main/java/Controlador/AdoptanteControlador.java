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
import java.util.Optional; // ¡Importante para Optional!

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
        // Verifica si ya existe una persona con ese correo electrónico
        if (personaDA.buscarPersonaPorCorreo(correoElectronico).isPresent()) {
            System.out.println("Error: El correo electrónico ya está registrado.");
            return false;
        }

        Adoptante nuevoAdoptante = new Adoptante(nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico, preferencias);
        personaDA.guardarPersona(nuevoAdoptante); // Usa guardarPersona para persistir el nuevo adoptante
        System.out.println("Adoptante registrado exitosamente con ID: " + nuevoAdoptante.getId());
        return true;
    }

    public boolean iniciarSesion(String correoElectronico) {
        // Busca la persona por correo electrónico, que devuelve un Optional<Persona>
        Optional<Persona> personaOptional = personaDA.buscarPersonaPorCorreo(correoElectronico);

        // Verifica si el Optional contiene una persona
        if (personaOptional.isPresent()) {
            Persona persona = personaOptional.get(); // Obtiene la Persona del Optional
            // Verifica si la persona encontrada es una instancia de Adoptante
            if (persona instanceof Adoptante) {
                this.adoptanteActual = (Adoptante) persona; // Castea y asigna a adoptanteActual
                System.out.println("Sesión iniciada como Adoptante: " + adoptanteActual.getNombre());
                return true;
            } else {
                System.out.println("Error: El correo electrónico corresponde a otro tipo de usuario.");
                this.adoptanteActual = null;
                return false;
            }
        } else {
            System.out.println("Error: Correo electrónico no encontrado.");
            this.adoptanteActual = null;
            return false;
        }
    }

    public void cerrarSesion() {
        this.adoptanteActual = null;
        System.out.println("Sesión de adoptante cerrada.");
    }

    // obtener animales disponibles para adopción
    public List<Animal> obtenerAnimalesDisponibles() {
        // Carga todos los animales y los filtra por estado "disponible"
        // Asumo que cargarAnimales() en AnimalDA devuelve una List<Animal> o ArrayList<Animal>
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

        // Busca el animal por ID, que devuelve un Optional<Animal>
        Optional<Animal> animalOptional = animalDA.buscarAnimalPorId(idAnimal);

        // Verifica si el Optional contiene el animal
        if (animalOptional.isPresent()) {
            Animal animalAAdoptar = animalOptional.get(); // Obtiene el Animal del Optional

            // Verifica el estado de salud antes de la adopción
            if (!"disponible".equalsIgnoreCase(animalAAdoptar.getEstadoSalud())) {
                System.out.println("Error: El animal con ID " + idAnimal + " no está disponible para adopción (estado: " + animalAAdoptar.getEstadoSalud() + ").");
                return false;
            }

            // Realiza los cambios para la adopción
            animalAAdoptar.setEstadoSalud("adoptado");
            animalAAdoptar.setIdAdoptante(adoptanteActual.getId()); // Asignar el ID del adoptante actual

            try {
                // Actualiza el animal en la persistencia (AnimalDA)
                animalDA.actualizarAnimal(animalAAdoptar);
                System.out.println("¡Felicidades! Has adoptado exitosamente a " + animalAAdoptar.getEspecie() + " (ID: " + idAnimal + ").");
                return true;
            } catch (RuntimeException e) {
                System.err.println("Error al procesar la adopción: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("Error: Animal con ID " + idAnimal + " no encontrado.");
            return false;
        }
    }

    // para obtener el historial de adopciones del adoptante actual
    public List<Animal> obtenerHistorialAdopciones() {
        if (adoptanteActual == null) {
            System.out.println("Debe iniciar sesión como adoptante para ver su historial.");
            return new ArrayList<>(); // Retorna una lista vacía si no hay sesión
        }

        // Carga todos los animales y los filtra por el ID del adoptante actual
        return animalDA.cargarAnimales().stream()
                .filter(animal -> adoptanteActual.getId().equals(animal.getIdAdoptante()))
                .collect(Collectors.toList());
    }
}