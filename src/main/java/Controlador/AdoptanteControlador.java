package Controlador;

import Modelo.Adoptante;
import Modelo.Animal;
import Datos.PersonaDA;
import Datos.AnimalDA;
import Modelo.Persona;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdoptanteControlador {
    private PersonaDA personaDA;
    private AnimalDA animalDA;
    private Adoptante adoptanteActual;

    public AdoptanteControlador(PersonaDA personaDA, AnimalDA animalDA) {
        this.personaDA = personaDA;
        this.animalDA = animalDA;
    }

    public boolean registrarAdoptante(String nombre, LocalDate fechaNacimiento,
                                      String direccion, String telefono,
                                      String correo, String preferencias,
                                      String contrasena) {
        // Verifica si el correo ya existe
        if (personaDA.existeCorreo(correo)) {
            return false;
        }

        Adoptante adoptante = new Adoptante(nombre, fechaNacimiento, direccion,
                telefono, correo, preferencias, contrasena);

        personaDA.guardarPersona(adoptante);
        return true;
    }

    public boolean iniciarSesion(String correo, String contrasena) {
        List<Persona> personas = personaDA.cargarPersonas();
        for (Persona persona : personas) {
            if (persona.getCorreoElectronico().equalsIgnoreCase(correo) &&
                    persona.getContrasena().equals(contrasena)) { // Comparación directa (sin hashing)
                this.adoptanteActual = (Adoptante) persona;
                return true;
            }
        }
        return false;
    }



    public void cerrarSesion() {
        this.adoptanteActual = null;
    }

    public Adoptante getAdoptanteActual() {
        return adoptanteActual;
    }

    // Búsquedas
    private Adoptante buscarAdoptantePorCorreo(String correo) {
        for (Persona persona : personaDA.cargarPersonas()) {
            if (persona instanceof Adoptante &&
                    persona.getCorreoElectronico().equalsIgnoreCase(correo)) {
                return (Adoptante) persona;
            }
        }
        return null;
    }


    // Animales
    public List<Animal> listarAnimalesDisponibles() {
        return animalDA.cargarAnimales().stream()
                .filter(a -> "disponible".equalsIgnoreCase(a.getEstadoSalud()))
                .collect(Collectors.toList());
    }

    public List<Animal> filtrarAnimales(String criterio, String valor) {
        return listarAnimalesDisponibles().stream()
                .filter(a -> {
                    switch (criterio.toLowerCase()) {
                        case "especie": return a.getEspecie().equalsIgnoreCase(valor);
                        case "raza": return a.getRaza().equalsIgnoreCase(valor);
                        case "sexo": return a.getSexo().equalsIgnoreCase(valor);
                        case "edad": return String.valueOf(a.getEdad()).equals(valor);
                        case "lugar": return a.getLugarEncontrado().contains(valor);
                        default: return true;
                    }
                })
                .collect(Collectors.toList());
    }

    // Adopciones
    public boolean registrarAdopcion(String idAnimal) {
        if (adoptanteActual != null) {
            adoptanteActual.agregarAdopcion(idAnimal);
            Animal animal = animalDA.buscarAnimalPorId(idAnimal);
            if (animal != null) {
                animal.setEstadoSalud("adoptado");
                personaDA.actualizarPersona(adoptanteActual);
                animalDA.actualizarAnimal(animal);
                return true;
            }
        }
        return false;
    }

    public List<Animal> obtenerHistorialAdopciones() {
        if (adoptanteActual != null) {
            return adoptanteActual.getHistorialAdopciones().stream()
                    .map(id -> animalDA.buscarAnimalPorId(id))
                    .filter(a -> a != null)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}