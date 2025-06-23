package Controlador;

import Datos.PersonaDA;
import Modelo.Persona;
import Modelo.Veterinario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;

public class VeterinarioControlador {

    private PersonaDA personaDA;
    private Veterinario veterinarioActual; // para mantener el veterinario logueado

    public VeterinarioControlador(PersonaDA personaDA) {
        this.personaDA = personaDA;
    }

    //para registrar un nuevo veterinario, ahora incluye 'contrasenaPlana'
    public boolean registrarVeterinario(String nombre, String rut, LocalDate fechaNacimiento,
                                        String direccion, String numeroTelefono,
                                        String correoElectronico, String especialidad,
                                        String licencia, String contrasenaPlana) { // contraseña plana
        // Verifica si el correo ya existe
        Optional<Persona> personaExistente = personaDA.buscarPersonaPorCorreo(correoElectronico);
        if (personaExistente.isPresent()) {
            return false;
        }

        // el constructor de Veterinario se encarga de hashear la contraseña
        Veterinario nuevoVeterinario = new Veterinario(nombre, rut, fechaNacimiento,
                direccion, numeroTelefono,
                correoElectronico, especialidad,
                licencia, contrasenaPlana);
        personaDA.guardarPersona(nuevoVeterinario);
        return true;
    }

    // para iniciar sesión de un veterinario
    public boolean iniciarSesion(String correo, String contrasenaPlana) {
        Optional<Persona> personaOptional = personaDA.buscarPersonaPorCorreo(correo);

        if (personaOptional.isPresent()) {
            Persona persona = personaOptional.get();
            if (persona instanceof Veterinario) {
                Veterinario veterinario = (Veterinario) persona;

                if (BCrypt.checkpw(contrasenaPlana, veterinario.getContrasenaHash())) {
                    this.veterinarioActual = veterinario;
                    return true;
                }
            }
        }
        return false; // credenciales incorrectas o no es un Veterinario
    }

    // para cerrar sesión
    public void cerrarSesion() {
        this.veterinarioActual = null;
    }

    //para obtener el veterinario actualmente logueado
    public Veterinario getVeterinarioActual() {
        return veterinarioActual;
    }

    public Veterinario buscarVeterinarioPorld(String id) {
        ArrayList<Persona> personas = personaDA.cargarPersonas();
        for (Persona persona : personas) {
            if (persona instanceof Veterinario && persona.getId().equals(id)) {
                return (Veterinario) persona;
            }
        }
        return null;
    }

    public ArrayList<Veterinario> listarTodosVeterinarios() {
        ArrayList<Veterinario> veterinarios = new ArrayList<>();
        ArrayList<Persona> personas = personaDA.cargarPersonas();
        for (Persona persona : personas) {
            if (persona instanceof Veterinario) {
                veterinarios.add((Veterinario) persona);
            }
        }
        return veterinarios;
    }

    public boolean actualizarVeterinario(String id, String nombre, String rut, LocalDate fechaNacimiento,
                                         String direccion, String numeroTelefono, String correoElectronico,
                                         String especialidad, String licencia, String contrasenaPlana) { // Contraseña plana si se desea actualizar
        Veterinario veterinarioExistente = buscarVeterinarioPorld(id);
        if (veterinarioExistente != null) {
            veterinarioExistente.setNombre(nombre);
            veterinarioExistente.setFechaNacimiento(fechaNacimiento);
            veterinarioExistente.setDireccion(direccion);
            veterinarioExistente.setNumeroTelefono(numeroTelefono);
            veterinarioExistente.setCorreoElectronico(correoElectronico);
            veterinarioExistente.setEspecialidad(especialidad);
            veterinarioExistente.setLicencia(licencia);

            if (contrasenaPlana != null && !contrasenaPlana.isEmpty()) {
                String nuevoHash = BCrypt.hashpw(contrasenaPlana, BCrypt.gensalt());
                veterinarioExistente.setContrasenaHash(nuevoHash); // Usa el setter de Veterinario
            }

            personaDA.actualizarPersona(veterinarioExistente);
            return true;
        }
        return false;
    }

    public boolean eliminarVeterinario(String id) {
        Veterinario veterinario = buscarVeterinarioPorld(id);
        if (veterinario != null) {
            personaDA.eliminarPersona(id);
            return true;
        }
        return false;
    }
}