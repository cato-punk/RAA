// AdoptanteControlador.java
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
    private Adoptante adoptanteActual;

    public AdoptanteControlador(PersonaDA personaDA, AnimalDA animalDA) {
        this.personaDA = personaDA;
        this.animalDA = animalDA;
    }

    public boolean registrarAdoptante(String nombre, LocalDate fechaNacimiento,
                                      String direccion, String telefono,
                                      String correo, String preferencias,
                                      String contrasena) {
        if (personaDA.existeCorreo(correo)) {
            System.out.println("El correo ya está registrado");
            return false;
        }

        Adoptante nuevoAdoptante = new Adoptante(
                nombre, fechaNacimiento, direccion,
                telefono, correo, preferencias, contrasena
        );

        personaDA.guardarPersona(nuevoAdoptante);
        this.adoptanteActual = nuevoAdoptante;
        return true;
    }

    public boolean iniciarSesion(String correo, String contrasena) {
        ArrayList<Persona> personas = personaDA.cargarPersonas();

        for (Persona persona : personas) {
            if (persona instanceof Adoptante) {
                Adoptante adoptante = (Adoptante) persona;
                if (adoptante.getCorreoElectronico().equalsIgnoreCase(correo)
                        && adoptante.getContrasena().equals(contrasena)) {
                    this.adoptanteActual = adoptante;
                    return true;
                }
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

    public boolean registrarAdopcion(String idAnimal) {
        if (adoptanteActual != null) {
            System.out.println("Buscando animal con ID: " + idAnimal);
            Animal animal = animalDA.buscarAnimalPorId(idAnimal);

            if (animal != null) {
                System.out.println("Animal encontrado: " + animal.getId());

                adoptanteActual.agregarAdopcion(idAnimal);
                animal.setEstadoSalud("adoptado");

                personaDA.actualizarPersona(adoptanteActual);
                animalDA.actualizarAnimal(animal);
                return true;
            } else {
                System.out.println("Animal con ID " + idAnimal + " no encontrado.");
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
