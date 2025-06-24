package Controlador;

import Modelo.Veterinario;
import Modelo.Persona;
import Datos.PersonaDA;
import Datos.AnimalDA;
import Modelo.Animal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

public class VeterinarioControlador {

    private PersonaDA personaDA;
    private AnimalDA animalDA;
    private Veterinario veterinarioActual;

    public VeterinarioControlador(PersonaDA personaDA, AnimalDA animalDA) {
        this.personaDA = personaDA;
        this.animalDA = animalDA;
    }

    //para registrar un nuevo veterinario (sin parámetro de contraseña)
    public boolean registrarVeterinario(String nombre, String rut, LocalDate fechaNacimiento,
                                        String direccion, String numeroTelefono,
                                        String correoElectronico, String especialidad, String licencia) {
        if (personaDA.buscarPersonaPorCorreo(correoElectronico).isPresent()) {
            System.out.println("Error: El correo electrónico ya está registrado.");
            return false;
        }
        Veterinario nuevoVeterinario = new Veterinario(nombre, rut, fechaNacimiento, direccion,
                numeroTelefono, correoElectronico, especialidad,
                licencia);
        personaDA.guardarPersona(nuevoVeterinario);
        System.out.println("Veterinario registrado exitosamente con ID: " + nuevoVeterinario.getId());
        return true;
    }

    //para iniciar sesión de un Veterinario (solo verific correo)
    public boolean iniciarSesion(String correo) {
        Optional<Persona> personaOptional = personaDA.buscarPersonaPorCorreo(correo);

        if (personaOptional.isPresent()) {
            Persona persona = personaOptional.get();
            if (persona instanceof Veterinario) {
                this.veterinarioActual = (Veterinario) persona;
                return true;
            }
        }
        return false;
    }

    public void cerrarSesion() {
        this.veterinarioActual = null;
    }

    public Veterinario getVeterinarioActual() {
        return veterinarioActual;
    }

    //para buscar un veterinario por ID
    public Veterinario buscarVeterinarioPorId(String id) {
        Optional<Persona> personaOptional = personaDA.buscarPersonaPorId(id);

        if (personaOptional.isPresent()) {
            Persona persona = personaOptional.get();
            if (persona instanceof Veterinario) {
                return (Veterinario) persona;
            }
        }
        return null; // Si no se encuentra o no es Veterinario
    }

    // para listar todos los veterinarios (más útil para el Admin)
    public ArrayList<Veterinario> listarTodosVeterinarios() {
        ArrayList<Veterinario> veterinarios = new ArrayList<>();
        // Asumo que cargarPersonas() devuelve ArrayList<Persona>
        for (Persona p : personaDA.cargarPersonas()) {
            if (p instanceof Veterinario) {
                veterinarios.add((Veterinario) p);
            }
        }
        return veterinarios;
    }

    //para que el Veterinario actualice sus propios datos (sin contraseña)
    public boolean actualizarVeterinario(String id, String nombre, String rut, LocalDate fechaNacimiento,
                                         String direccion, String numeroTelefono, String correoElectronico,
                                         String especialidad, String licencia) {
        if (veterinarioActual == null || !veterinarioActual.getId().equals(id)) {
            System.out.println("Error: No tiene permiso para actualizar este perfil o no ha iniciado sesión.");
            return false;
        }

        Optional<Persona> veterinarioOptional = personaDA.buscarPersonaPorId(id);
        Veterinario veterinarioAActualizar = null;
        if (veterinarioOptional.isPresent() && veterinarioOptional.get() instanceof Veterinario) {
            veterinarioAActualizar = (Veterinario) veterinarioOptional.get();
        }

        if (veterinarioAActualizar == null) {
            System.out.println("Error: Veterinario con ID " + id + " no encontrado para actualizar.");
            return false;
        }

        // campos comunes del vet
        veterinarioAActualizar.setNombre(nombre);
        veterinarioAActualizar.setRut(rut);
        veterinarioAActualizar.setFechaNacimiento(fechaNacimiento);
        veterinarioAActualizar.setDireccion(direccion);
        veterinarioAActualizar.setNumeroTelefono(numeroTelefono);
        veterinarioAActualizar.setCorreoElectronico(correoElectronico);
        veterinarioAActualizar.setEspecialidad(especialidad);
        veterinarioAActualizar.setLicencia(licencia);

        return personaDA.actualizarPersona(veterinarioAActualizar);
    }

    public List<Animal> obtenerTodosLosAnimales() {
        return animalDA.cargarAnimales();
    }

    public Animal buscarAnimalPorId(String idAnimal) { // id es el parámetro aquí
        Optional<Animal> animalOptional = animalDA.buscarAnimalPorId(idAnimal); // Devuelve Optional<Animal>

        if (animalOptional.isPresent()) {
            return animalOptional.get(); // Obtiene el Animal si está presente
        } else {
            System.out.println("Error: Animal con ID " + idAnimal + " no encontrado.");
            return null; // Si no se encuentra
        }
    }

    public boolean actualizarEstadoSaludAnimal(String idAnimal, String nuevoEstado) {
        if (veterinarioActual == null) {
            System.out.println("Debe iniciar sesión como veterinario para actualizar el estado de salud de un animal.");
            return false;
        }
        Optional<Animal> animalOptional = animalDA.buscarAnimalPorId(idAnimal);
        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();
            animal.setEstadoSalud(nuevoEstado);
            animalDA.actualizarAnimal(animal);
            System.out.println("Estado de salud del animal " + idAnimal + " actualizado a " + nuevoEstado + ".");
            return true;
        } else {
            System.out.println("Error: Animal con ID " + idAnimal + " no encontrado para actualizar el estado.");
            return false;
        }
    }


    public boolean diagnosticarAnimal(String idAnimal, String diagnostico) {
        if (veterinarioActual == null) {
            System.out.println("Debe iniciar sesión como veterinario para diagnosticar un animal.");
            return false;
        }
        Optional<Animal> animalOptional = animalDA.buscarAnimalPorId(idAnimal);
        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();
            animal.setDiagnostico(diagnostico);
            animalDA.actualizarAnimal(animal);
            System.out.println("Diagnóstico para animal " + idAnimal + " actualizado a: " + diagnostico + ".");
            return true;
        } else {
            System.out.println("Error: Animal con ID " + idAnimal + " no encontrado para diagnosticar.");
            return false;
        }
    }
}