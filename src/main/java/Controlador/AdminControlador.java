package Controlador;

import Datos.PersonaDA;
import Modelo.Admin;
import Modelo.Persona;
import Modelo.Adoptante; // actualizar/eliminar si se necesita
import Modelo.Rescatista; //actualizar/eliminar si se necesita
import Modelo.Veterinario; //actualizar/eliminar si se necesita
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;

public class AdminControlador {

    private PersonaDA personaDA;
    private Admin adminActual; // Para mantener el admin logueado

    public AdminControlador(PersonaDA personaDA) {
        this.personaDA = personaDA;
        // Opcional: Crear un admin por defecto si no existe (solo para fines de prueba inicial) o lo hacemos manual
        if (personaDA.buscarPersonaPorCorreo("admin@example.com").isEmpty()) {
            registrarAdmin("Admin Maestro", "00000000-0", LocalDate.of(1990, 1, 1),
                    "Calle Ficticia 123", "987654321", "admin@example.com", "admin123");
            System.out.println("Admin por defecto creado: admin@example.com / admin123");
        }
    }

    //para registrar un nuevo Admin (solo el admin puede registrar otros admins, o es inicial) (a nosotras)
    public boolean registrarAdmin(String nombre, String rut, LocalDate fechaNacimiento,
                                  String direccion, String numeroTelefono,
                                  String correoElectronico, String contrasenaPlana) {
        if (personaDA.buscarPersonaPorCorreo(correoElectronico).isPresent()) {
            return false; // el correo ya existe
        }
        Admin nuevoAdmin = new Admin(nombre, rut, fechaNacimiento, direccion,
                numeroTelefono, correoElectronico, contrasenaPlana);
        personaDA.guardarPersona(nuevoAdmin);
        return true;
    }

    //para iniciar sesión de un Admin
    public boolean iniciarSesion(String correo, String contrasenaPlana) {
        Optional<Persona> personaOptional = personaDA.buscarPersonaPorCorreo(correo);

        if (personaOptional.isPresent()) {
            Persona persona = personaOptional.get();
            if (persona instanceof Admin) {
                Admin admin = (Admin) persona;
                if (BCrypt.checkpw(contrasenaPlana, admin.getContrasenaHash())) {
                    this.adminActual = admin;
                    return true;
                }
            }
        }
        return false; // credenciales incorrectas o no es un Admin
    }

    // cerrar sesión del Admin
    public void cerrarSesion() {
        this.adminActual = null;
    }

    // obtener el Admin actual
    public Admin getAdminActual() {
        return adminActual;
    }

    // --- Métodos de gestión de usuarios (solo para Admin logueado) ---

    //metodo lista todas las personas registradas, sin importar su tipo
    public ArrayList<Persona> listarTodasLasPersonas() {
        // Asegurarse de que solo un admin logueado pueda hacer esto, si es necesario.
        // el CLI controlará el acceso a esta función

        return personaDA.cargarPersonas();
    }

    //para buscar una persona por ID y devolverla como tipo Persona
    public Persona buscarPersonaPorId(String id) {
        return personaDA.buscarPersonaPorld(id);
    }

    //para actualizar cualquier tipo de Persona
    // Para otros tipos, este parámetro se ignorará o pasará vacío/null.
    public boolean actualizarDatosUsuario(String id, String nombre, String rut, LocalDate fechaNacimiento,
                                          String direccion, String numeroTelefono, String correoElectronico,
                                          String contrasenaPlana, // si se decide añadir contraseñas en el futuro
                                          String especialidad, String licencia, // Para Veterinario
                                          String preferencias, String infoAdopcion) { // Para Adoptante, Rescatista etc.
        if (adminActual == null) {
            System.out.println("Error: Solo un administrador logueado puede actualizar usuarios.");
            return false;
        }

        Persona personaAActualizar = personaDA.buscarPersonaPorld(id);
        if (personaAActualizar == null) {
            return false; // No encontramos la persona
        }

        // Actualizar campos comunes a todas las Personas
        personaAActualizar.setNombre(nombre);
        personaAActualizar.setRut(rut);
        personaAActualizar.setFechaNacimiento(fechaNacimiento);
        personaAActualizar.setDireccion(direccion);
        personaAActualizar.setNumeroTelefono(numeroTelefono);
        personaAActualizar.setCorreoElectronico(correoElectronico);

        // Actualizar campos específicos según el tipo de persona
        if (personaAActualizar instanceof Admin) {
            Admin admin = (Admin) personaAActualizar;
            if (contrasenaPlana != null && !contrasenaPlana.isEmpty()) {
                admin.setContrasenaHash(BCrypt.hashpw(contrasenaPlana, BCrypt.gensalt()));
            }
        } else if (personaAActualizar instanceof Veterinario) {
            Veterinario veterinario = (Veterinario) personaAActualizar;
            veterinario.setEspecialidad(especialidad);
            veterinario.setLicencia(licencia);
        } else if (personaAActualizar instanceof Adoptante) {
            Adoptante adoptante = (Adoptante) personaAActualizar;
            adoptante.setPreferencias(preferencias);
            adoptante.setInformacionAdopcion(infoAdopcion);

        }

        return personaDA.actualizarPersona(personaAActualizar);
    }


    //para eliminar cualquier tipo de Persona
    public boolean eliminarUsuario(String id) {
        if (adminActual == null) {
            System.out.println("Error: Solo un administrador logueado puede eliminar usuarios.");
            return false;
        }
        return personaDA.eliminarPersona(id);
    }
}