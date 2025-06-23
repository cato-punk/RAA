package Controlador;

import Datos.PersonaDA;
import Modelo.Persona;
import Modelo.Veterinario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class VeterinarioControlador {

    private final PersonaDA personaDA;

    public VeterinarioControlador(PersonaDA personaDA) {
        this.personaDA = personaDA;
    }

    //registrar un nuevo veterinario, cn  'rut'
    public void registrarVeterinario(String nombre, String rut, LocalDate fechaNacimiento,
                                     String direccion, String numeroTelefono, String correoElectronico,
                                     String especialidad, String licencia) {
        try {
            // Se pasa el 'rut' al constructor de Veterinario (que a su vez lo pasa a Persona)
            Veterinario nuevoVeterinario = new Veterinario(nombre, rut, fechaNacimiento,
                    direccion, numeroTelefono, correoElectronico, especialidad, licencia);
            personaDA.guardarPersona(nuevoVeterinario);
            System.out.println("Veterinario registrado exitosamente con ID: " +
                    nuevoVeterinario.getId());
        } catch (RuntimeException e) {
            System.err.println("Error al registrar veterinario: " + e.getMessage());
        }
    }

    //buscar un veterinario por su ID
    public Veterinario buscarVeterinarioPorld(String id) {
        Persona persona = personaDA.buscarPersonaPorld(id);
        if (persona instanceof Veterinario) {
            return (Veterinario) persona;
        }
        return null;
    }

    // actualizar los datos de un veterinario,  'rut'
    public void actualizarVeterinario(String id, String nombre, String rut, LocalDate
                                              fechaNacimiento, String direccion, String numeroTelefono, String correoElectronico,
                                      String especialidad, String licencia) {
        try {
            Veterinario veterinarioExistente = buscarVeterinarioPorld(id);
            if (veterinarioExistente != null) {
                veterinarioExistente.setNombre(nombre);
                veterinarioExistente.setRut(rut); // Actualizar RUT,en Persona
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

    //obtener todos los veterinarios registrados
    public ArrayList<Veterinario> obtenerTodosLosVeterinarios() {
        ArrayList<Persona> personas = personaDA.cargarPersonas();
        return personas.stream()
                .filter(p -> p instanceof Veterinario)
                .map(p -> (Veterinario) p)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}