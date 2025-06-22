package Controlador;

import Datos.PersonaDA;
import java.time.LocalDate;
import java.util.ArrayList;
import Modelo.Persona;
import Modelo.Rescatista;

public class RescatistaControlador {
    private PersonaDA personaDA;

    public RescatistaControlador(PersonaDA personaDA) {
        this.personaDA = personaDA;
    }

    public void registrarRescatista(String nombre, LocalDate fechaNacimiento, String direccion, String numeroTelefono, String correoElectronico) {
        Rescatista nuevoRescatista = new Rescatista(nombre, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        personaDA.guardarPersona(nuevoRescatista);
        System.out.println("Rescatista registrado exitosamente: " + nuevoRescatista.getId());
    }

    public Rescatista buscarRescatistaPorId(String id) {
        ArrayList<Persona> personas = personaDA.cargarPersonas();
        for (Persona persona : personas) {
            if (persona instanceof Rescatista && persona.getId().equals(id)) {
                return (Rescatista) persona;
            }
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
    //otras tareas que puedan hacer los rescatistas
}
