package Controlador;

import Modelo.Veterinario;
import Modelo.Persona;
import Datos.PersonaDA;
import Datos.AnimalDA; // interactúa con animales
import Modelo.Animal; // interactúa con animales


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List; // para listar animales y otros
import java.util.stream.Collectors; // para filtrar listas

public class VeterinarioControlador {

    private PersonaDA personaDA;
    private AnimalDA animalDA; //gestionar animales (diagnóstico, tratamiento)
    private Veterinario veterinarioActual; //mantener el veterinario logueado

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
                licencia); //
        personaDA.guardarPersona(nuevoVeterinario);
        System.out.println("Veterinario registrado exitosamente con ID: " + nuevoVeterinario.getId());
        return true;
    }

    //para iniciar sesión de un Veterinario (solo verific correo)
    public boolean iniciarSesion(String correo) { //
        Optional<Persona> personaOptional = personaDA.buscarPersonaPorCorreo(correo);

        if (personaOptional.isPresent()) {
            Persona persona = personaOptional.get();
            if (persona instanceof Veterinario) {
                this.veterinarioActual = (Veterinario) persona;
                return true; // Veterinario encontrado, "sesión iniciada"
            }
        }
        return false; // Veterinario no encontrado o el correo pertenece a otro tipo de persona
    }

    public void cerrarSesion() {
        this.veterinarioActual = null;
    }

    public Veterinario getVeterinarioActual() {
        return veterinarioActual;
    }

    //para buscar un veterinario por ID
    public Veterinario buscarVeterinarioPorId(String id) {
        Persona persona = personaDA.buscarPersonaPorld(id);
        if (persona instanceof Veterinario) {
            return (Veterinario) persona;
        }
        return null;
    }

    // para listar todos los veterinarios (mas util para el Admin)
    public ArrayList<Veterinario> listarTodosVeterinarios() {
        ArrayList<Veterinario> veterinarios = new ArrayList<>();
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

        Veterinario veterinarioAActualizar = buscarVeterinarioPorId(id);
        if (veterinarioAActualizar == null) {
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

    //gestión de animales por el vet
    public List<Animal> obtenerTodosLosAnimales() {
        return animalDA.cargarAnimales();
    }

    public Animal buscarAnimalPorId(String id) {
        return animalDA.buscarAnimalPorId(id);
    }

    public boolean actualizarEstadoSaludAnimal(String idAnimal, String nuevoEstado) {
        if (veterinarioActual == null) {
            System.out.println("Debe iniciar sesión como veterinario para actualizar el estado de salud de un animal.");
            return false;
        }
        Animal animal = animalDA.buscarAnimalPorId(idAnimal);
        if (animal != null) {
            animal.setEstadoSalud(nuevoEstado);
            animalDA.actualizarAnimal(animal);
            return true;
        }
        return false;
    }

    public boolean diagnosticarAnimal(String idAnimal, String diagnostico) {
        if (veterinarioActual == null) {
            System.out.println("Debe iniciar sesión como veterinario para diagnosticar un animal.");
            return false;
        }
        Animal animal = animalDA.buscarAnimalPorId(idAnimal);
        if (animal != null) {
            animal.setDiagnostico(diagnostico);
            animalDA.actualizarAnimal(animal);
            return true;
        }
        return false;
    }
}