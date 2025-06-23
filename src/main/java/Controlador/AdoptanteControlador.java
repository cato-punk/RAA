package Controlador;

import Modelo.Adoptante;
import Modelo.Animal; //porque usamos el historial
import Datos.PersonaDA;
import Datos.AnimalDA;
import Modelo.Persona;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; //filtrar animales

public class AdoptanteControlador {

    private PersonaDA personaDA;
    private AnimalDA animalDA;
    private Adoptante adoptanteActual; // para mantener el adoptante logueado

    public AdoptanteControlador(PersonaDA personaDA, AnimalDA animalDA) {
        this.personaDA = personaDA;
        this.animalDA = animalDA;
    }

    //para registrar un nuevo adoptante (sin parámetro de contraseña)
    public boolean registrarAdoptante(String nombre, String rut, LocalDate fechaNacimiento,
                                      String direccion, String numeroTelefono,
                                      String correoElectronico, String preferencias,
                                      String informacionAdopcion) {
        //si el correo ya existe
        if (personaDA.buscarPersonaPorCorreo(correoElectronico).isPresent()) {
            return false; // El correo ya existe
        }
        Adoptante nuevoAdoptante = new Adoptante(nombre, rut, fechaNacimiento, direccion,
                numeroTelefono, correoElectronico, preferencias,
                informacionAdopcion);
        personaDA.guardarPersona(nuevoAdoptante);
        return true;
    }

    // Método para iniciar sesión de un Adoptante (solo verifica existencia de correo)
    public boolean iniciarSesion(String correo) {
        Optional<Persona> personaOptional = personaDA.buscarPersonaPorCorreo(correo);

        if (personaOptional.isPresent()) {
            Persona persona = personaOptional.get();
            if (persona instanceof Adoptante) {
                this.adoptanteActual = (Adoptante) persona;
                return true; // Adoptante encontrado, "sesión iniciada"
            }
        }
        return false; // Adoptante no encontrado o el correo pertenece a otro tipo de persona
    }

    // Cerrar sesión del Adoptante
    public void cerrarSesion() {
        this.adoptanteActual = null;
    }

    // obtener el Adoptante actual
    public Adoptante getAdoptanteActual() {
        return adoptanteActual;
    }

    // --- Métodos de funcionalidades de Adoptante (según se definan en el futuro) ---
    // Si AdoptanteControlador tiene métodos que requieren AnimalDA, asegúrate de mantenerlos.
    // Por ejemplo, para listar animales disponibles o gestionar historial de adopciones.

    public List<Animal> obtenerAnimalesDisponibles() {
        // Implementación futura: filtrar animales que están en estado "disponible para adopción"
        // Por ahora, solo devuelve una lista de ejemplo o todos los animales.
        return animalDA.cargarAnimales().stream()
                .filter(animal -> "disponible".equalsIgnoreCase(animal.getEstadoSalud())) // Asumiendo que el estado 'disponible' es para adopción
                .collect(Collectors.toList());
    }

    public boolean adoptarAnimal(String idAnimal) {
        // Implementación futura: Lógica para marcar un animal como adoptado por adoptanteActual
        // Esto requeriría interacción con AnimalDA y posiblemente una lista de adopciones en Adoptante.
        System.out.println("Funcionalidad de adopción pendiente de implementar.");
        return false;
    }

    public List<Animal> obtenerHistorialAdopciones() {
        // Implementación futura: Lógica para obtener animales adoptados por adoptanteActual
        System.out.println("Funcionalidad de historial de adopciones pendiente de implementar.");
        return new ArrayList<>();
    }
}