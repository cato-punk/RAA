package Controlador;

import Modelo.Admin;
import Modelo.Persona;
import Datos.PersonaDA;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class AdminControlador {

    private PersonaDA personaDA;
    private Admin adminActual;

    public AdminControlador(PersonaDA personaDA) {
        this.personaDA = personaDA;
    }

    //para registrar un nuevo administrador
    public boolean registrarAdmin(String nombre, String rut, LocalDate fechaNacimiento,
                                  String direccion, String numeroTelefono, String correoElectronico) {
        if (personaDA.buscarPersonaPorCorreo(correoElectronico).isPresent()) {
            System.out.println("Error: El correo electrónico ya está registrado.");
            return false;
        }
        Admin nuevoAdmin = new Admin();
        personaDA.guardarPersona(nuevoAdmin);
        System.out.println("Administrador registrado exitosamente con ID: " + nuevoAdmin.getId());
        return true;
    }

    //iniciar sesión de un Administrador
    public boolean iniciarSesion(String correo) {
        Optional<Persona> personaOptional = personaDA.buscarPersonaPorCorreo(correo);

        if (personaOptional.isPresent()) {
            Persona persona = personaOptional.get();
            if (persona instanceof Admin) {
                this.adminActual = (Admin) persona;
                return true;
            }
        }
        return false;
    }

    public void cerrarSesion() {
        this.adminActual = null;
    }

    public Admin getAdminActual() {
        return adminActual;
    }

    //buscar cualquier tipo de Persona por ID (para el admin)
    public Persona buscarPersonaPorId(String id) {
        // CORRECCIÓN: Manejar Optional<Persona> aquí (línea 81 en tu código)
        Optional<Persona> personaOptional = personaDA.buscarPersonaPorId(id); // Esto devuelve Optional<Persona>
        if (personaOptional.isPresent()) {
            return personaOptional.get(); // Obtienes la Persona real del Optional
        }
        return null; // Si no se encuentra
    }

    // listar todos los administradores
    public ArrayList<Admin> listarTodosAdmins() {
        ArrayList<Admin> admins = new ArrayList<>();
        for (Persona p : personaDA.cargarPersonas()) {
            if (p instanceof Admin) {
                admins.add((Admin) p);
            }
        }
        return admins;
    }

    //  act datos de cualquier tipo de Persona (para el admin)
    public boolean actualizarDatosUsuario(String id, String nombre, String rut, LocalDate fechaNacimiento,
                                          String direccion, String numeroTelefono, String correoElectronico) {
        if (adminActual == null) {
            System.out.println("Error: Solo un administrador logueado puede actualizar usuarios.");
            return false;
        }

        Optional<Persona> personaOptional = personaDA.buscarPersonaPorId(id); //devuelve Optional<Persona>
        Persona personaAActualizar = null;

        if (personaOptional.isPresent()) {
            personaAActualizar = personaOptional.get(); // al

            // Actualizar campos comunes
            personaAActualizar.setNombre(nombre);
            personaAActualizar.setRut(rut);
            personaAActualizar.setFechaNacimiento(fechaNacimiento);
            personaAActualizar.setDireccion(direccion);
            personaAActualizar.setNumeroTelefono(numeroTelefono);
            personaAActualizar.setCorreoElectronico(correoElectronico);


            return personaDA.actualizarPersona(personaAActualizar);
        } else {
            System.out.println("Error: Usuario con ID " + id + " no encontrado para actualizar.");
            return false;
        }
    }

    //para eliminar cualquier tipo de Persona
    public boolean eliminarUsuario(String id) {
        if (adminActual == null) {
            System.out.println("Error: Solo un administrador logueado puede eliminar usuarios.");
            return false;
        }
        // Asumiendo que eliminarPersona en PersonaDA devuelve boolean y ya está corregido
        return personaDA.eliminarPersona(id);
    }
}