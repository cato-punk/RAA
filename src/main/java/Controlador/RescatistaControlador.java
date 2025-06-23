package Controlador;

import Datos.PersonaDA;
import Modelo.Persona;
import Modelo.Rescatista;
import java.time.LocalDate;
import java.util.ArrayList;

public class RescatistaControlador {

    private PersonaDA personaDA;

    public RescatistaControlador(PersonaDA personaDA) {
        this.personaDA = personaDA;
    }

    //registrar un nuevo rescatista, ahora incluye 'rut'
    public void registrarRescatista(String nombre, String rut, LocalDate fechaNacimiento, String
            direccion, String numeroTelefono, String correoElectronico) {
        Rescatista nuevoRescatista = new Rescatista(nombre, rut, fechaNacimiento, direccion,
                numeroTelefono, correoElectronico);
        personaDA.guardarPersona(nuevoRescatista);
        System.out.println("Rescatista registrado exitosamente: " + nuevoRescatista.getId());
    }

    public Rescatista buscarRescatistaPorld(String id) {
        Persona persona = personaDA.buscarPersonaPorld(id);
        if (persona instanceof Rescatista) {
            return (Rescatista) persona;
        }
        return null;
    }

    public ArrayList<Rescatista> listarTodosRescatistas() {
        ArrayList<Rescatista> rescatistas = new ArrayList<>();
        for (Persona persona : personaDA.cargarPersonas()) {
            if (persona instanceof Rescatista) {
                rescatistas.add((Rescatista) persona);
            }
        }
        return rescatistas;
    }

    // actualizar  incluir rut)
    public void actualizarRescatista(String id, String nombre, String rut, LocalDate fechaNacimiento,
                                     String direccion, String numeroTelefono, String correoElectronico) {
        Rescatista rescatistaExistente = buscarRescatistaPorld(id);
        if (rescatistaExistente != null) {
            rescatistaExistente.setNombre(nombre);
            rescatistaExistente.setRut(rut); // Actualizar RUT
            rescatistaExistente.setFechaNacimiento(fechaNacimiento);
            rescatistaExistente.setDireccion(direccion);
            rescatistaExistente.setNumeroTelefono(numeroTelefono);
            rescatistaExistente.setCorreoElectronico(correoElectronico);
            personaDA.actualizarPersona(rescatistaExistente);
            System.out.println("Rescatista con ID " + id + " actualizado exitosamente.");
        } else {
            System.out.println("Rescatista con ID " + id + " no encontrado para actualizar.");
        }
    }
}