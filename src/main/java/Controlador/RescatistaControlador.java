package Controlador;

import Datos.PersonaDA;
import Modelo.Persona;
import Modelo.Rescatista;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional; //  usar Optional para buscar Persona por correo
import org.mindrot.jbcrypt.BCrypt;

public class RescatistaControlador {

    private PersonaDA personaDA;
    private Rescatista rescatistaActual; // NUEVO Para mantener el rescatista logueado

    public RescatistaControlador(PersonaDA personaDA) {
        this.personaDA = personaDA;
    }

    // ahora incluye 'contrasenaPlana'
    public boolean registrarRescatista(String nombre, String rut, LocalDate fechaNacimiento,
                                       String direccion, String numeroTelefono,
                                       String correoElectronico, String contrasenaPlana) { // Contraseña plana
        // Verifica si el correo ya existe
        if (personaDA.buscarPersonaPorCorreo(correoElectronico).isPresent()) { // usando Optional
            return false;
        }

        //el constructor de Rescatista se encarga de hashear la contraseña
        Rescatista nuevoRescatista = new Rescatista(nombre, rut, fechaNacimiento,
                direccion, numeroTelefono,
                correoElectronico, contrasenaPlana);
        personaDA.guardarPersona(nuevoRescatista);
        return true;
    }

    //para iniciar sesion de un rescatista
    public boolean iniciarSesion(String correo, String contrasenaPlana) {
        Optional<Persona> personaOptional = personaDA.buscarPersonaPorCorreo(correo);

        if (personaOptional.isPresent()) {
            Persona persona = personaOptional.get();
            if (persona instanceof Rescatista) {
                Rescatista rescatista = (Rescatista) persona;
                // compara con la contraseña hasheada
                if (BCrypt.checkpw(contrasenaPlana, rescatista.getContrasenaHash())) {
                    this.rescatistaActual = rescatista;
                    return true;
                }
            }
        }
        return false; // credenciales incorrectas o no es un Rescatista
    }

    // para cerrar sesion
    public void cerrarSesion() {
        this.rescatistaActual = null;
    }

    // para obtener el rescatista actualmente logueado
    public Rescatista getRescatistaActual() {
        return rescatistaActual;
    }


    public Rescatista buscarRescatistaPorld(String id) {
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
        ArrayList<Persona> personas = personaDA.cargarPersonas();
        for (Persona persona : personas) {
            if (persona instanceof Rescatista) {
                rescatistas.add((Rescatista) persona);
            }
        }
        return rescatistas;
    }

    public boolean actualizarRescatista(String id, String nombre, String rut, LocalDate fechaNacimiento,
                                        String direccion, String numeroTelefono, String correoElectronico,
                                        String contrasenaPlana) { //contraseña plana si se desea actualizar
        Rescatista rescatistaExistente = buscarRescatistaPorld(id);
        if (rescatistaExistente != null) {
            // actualizar solo los campos que cambian
            rescatistaExistente.setNombre(nombre); //el rut noooo, asumimos
            rescatistaExistente.setFechaNacimiento(fechaNacimiento);
            rescatistaExistente.setDireccion(direccion);
            rescatistaExistente.setNumeroTelefono(numeroTelefono);
            rescatistaExistente.setCorreoElectronico(correoElectronico);

            // aolo actualizar la contraseña si se proporciona una nueva
            if (contrasenaPlana != null && !contrasenaPlana.isEmpty()) {
                // generar un nuevo hash para la nueva contraseña
                String nuevoHash = BCrypt.hashpw(contrasenaPlana, BCrypt.gensalt());
                rescatistaExistente.setContrasenaHash(nuevoHash); // setter privado en Rescatista
            }

            personaDA.actualizarPersona(rescatistaExistente);
            return true;
        }
        return false;
    }

    public boolean eliminarRescatista(String id) {
        Rescatista rescatista = buscarRescatistaPorld(id);
        if (rescatista != null) {
            personaDA.eliminarPersona(id);
            return true;
        }
        return false;
    }
}