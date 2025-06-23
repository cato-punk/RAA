package Controlador;

import Datos.PersonaDA; // Importa PersonaDA
import Modelo.Persona;
import Modelo.Veterinario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class VeterinarioControlador {
    private final PersonaDA personaDA;

    // Constructor que recibe la instancia de PersonaDA
    public VeterinarioControlador(PersonaDA personaDA) {
        this.personaDA = personaDA;
    }

    // Metodo para registrar un nuevo veterinario
    // Añadí más parámetros para alinearse con los atributos completos de Persona
    public void registrarVeterinario(String nombre, String rut, LocalDate fechaNacimiento,
                                     String direccion, String numeroTelefono, String correoElectronico,
                                     String especialidad, String licencia) {
        try {
            Veterinario nuevoVeterinario = new Veterinario(nombre, rut, fechaNacimiento, direccion,
                    numeroTelefono, correoElectronico, especialidad, licencia);
            personaDA.agregarPersona(nuevoVeterinario); // Usa PersonaDA para agregar
            System.out.println("Veterinario registrado exitosamente con ID: " + nuevoVeterinario.getId());
        } catch (RuntimeException e) {
            System.err.println("Error al registrar veterinario: " + e.getMessage());

        }
    }

    // Método para buscar un veterinario por su ID
    public Veterinario buscarVeterinarioPorId(String id) {
        Persona persona = personaDA.buscarPersonaPorId(id);
        if (persona instanceof Veterinario) {
            return (Veterinario) persona;
        }
        return null;
    }

    // Método para actualizar los datos de un veterinario
    public void actualizarVeterinario(String id, String nombre, String rut, LocalDate fechaNacimiento,
                                      String direccion, String numeroTelefono, String correoElectronico,
                                      String especialidad, String licencia) {
        try {
            Veterinario veterinarioExistente = buscarVeterinarioPorId(id);
            if (veterinarioExistente != null) {
                veterinarioExistente.setNombre(nombre);
                veterinarioExistente.setRut(rut);
                veterinarioExistente.setFechaNacimiento(fechaNacimiento);
                veterinarioExistente.setDireccion(direccion);
                veterinarioExistente.setNumeroTelefono(numeroTelefono);
                veterinarioExistente.setCorreoElectronico(correoElectronico);
                veterinarioExistente.setEspecialidad(especialidad);
                veterinarioExistente.setLicencia(licencia);
                personaDA.actualizarPersona(veterinarioExistente);
                System.out.println("Veterinario actualizado exitosamente.");
            } else {
                System.out.println("Error: Veterinario con ID " + id + " no encontrado.");
            }
        } catch (RuntimeException e) {
            System.err.println("Error al actualizar veterinario: " + e.getMessage());
        }
    }


    // Metodo para obtener todos los veterinarios registrados
    public ArrayList<Veterinario> obtenerTodosLosVeterinarios() {
        // Carga todas las personas y las filtra para obtener solo los veterinarios
        ArrayList<Persona> personas = personaDA.cargarPersonas();
        return personas.stream()
                .filter(p -> p instanceof Veterinario)
                .map(p -> (Veterinario) p)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

