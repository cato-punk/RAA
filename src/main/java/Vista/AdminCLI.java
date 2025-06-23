package Vista;

import Controlador.AdminControlador;
import Modelo.Persona;
import Modelo.Adoptante;
import Modelo.Rescatista;
import Modelo.Veterinario;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminCLI {

    private Scanner sc;
    private AdminControlador controlador;

    public AdminCLI(AdminControlador controlador) {
        this.sc = new Scanner(System.in);
        this.controlador = controlador;
    }

    public void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n--- MENÚ ADMIN ---");
            System.out.println("1. Iniciar sesión como Administrador");
            System.out.println("2. Salir");
            System.out.print("Ingrese su opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    if (iniciarSesionAdmin()) {
                        mostrarMenuAdminLogueado();
                    }
                    break;
                case "2":
                    System.out.println("Saliendo del menú de administración.");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }

    private boolean iniciarSesionAdmin() {
        System.out.println("\n--- Iniciar Sesión Administrador ---");
        System.out.print("Ingrese su correo electrónico: ");
        String correo = sc.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contrasena = sc.nextLine();

        if (controlador.iniciarSesion(correo, contrasena)) {
            System.out.println("¡Bienvenido Admin " + controlador.getAdminActual().getNombre() + "!");
            return true;
        } else {
            System.out.println("Credenciales de administrador incorrectas.");
            return false;
        }
    }

    private void mostrarMenuAdminLogueado() {
        while (true) {
            System.out.println("\n--- MENÚ DE ADMINISTRADOR LOGUEADO ---");
            System.out.println("1. Listar todos los usuarios (Adoptantes, Rescatistas, Veterinarios, Admins)");
            System.out.println("2. Buscar usuario por ID");
            System.out.println("3. Actualizar datos de un usuario");
            System.out.println("4. Eliminar un usuario");
            System.out.println("5. Cerrar sesión");
            System.out.print("Ingrese su opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    listarTodosLosUsuarios();
                    break;
                case "2":
                    buscarUsuarioPorId();
                    break;
                case "3":
                    actualizarUsuario();
                    break;
                case "4":
                    eliminarUsuario();
                    break;
                case "5":
                    controlador.cerrarSesion();
                    System.out.println("Sesión de administrador cerrada.");
                    return; // Vuelve al menú principal (de login Admin)
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void listarTodosLosUsuarios() {
        System.out.println("\n--- Listado de Todos los Usuarios ---");
        ArrayList<Persona> personas = controlador.listarTodasLasPersonas();
        if (personas.isEmpty()) {
            System.out.println("No hay usuarios registrados en el sistema.");
        } else {
            for (Persona p : personas) {
                System.out.println(p.toString()); //el toString de cada tipo de Persona
            }
        }
    }

    private void buscarUsuarioPorId() {
        System.out.println("\n--- Buscar Usuario por ID ---");
        System.out.print("Ingrese el ID del usuario a buscar: ");
        String id = sc.nextLine();
        Persona persona = controlador.buscarPersonaPorId(id);
        if (persona != null) {
            System.out.println("Usuario encontrado: " + persona.toString());
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }

    private void actualizarUsuario() {
        System.out.println("\n--- Actualizar Datos de Usuario ---");
        System.out.print("Ingrese el ID del usuario a actualizar: ");
        String id = sc.nextLine();

        Persona personaExistente = controlador.buscarPersonaPorId(id);
        if (personaExistente == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        System.out.println("Deje en blanco los campos que no desea actualizar.");
        System.out.println("Datos actuales: " + personaExistente.toString());

        //datos comunes a todas las Personas
        System.out.print("Nuevo Nombre (" + personaExistente.getNombre() + "): ");
        String nombre = sc.nextLine();
        if (nombre.isEmpty()) nombre = personaExistente.getNombre();

        System.out.print("Nuevo RUT (" + personaExistente.getRut() + "): ");
        String rut = sc.nextLine();
        if (rut.isEmpty()) rut = personaExistente.getRut();

        LocalDate fechaNacimiento = personaExistente.getFechaNacimiento();
        System.out.print("Nueva Fecha de Nacimiento (AAAA-MM-DD) (" + fechaNacimiento + "): ");
        String fechaStr = sc.nextLine();
        if (!fechaStr.isEmpty()) {
            try {
                fechaNacimiento = LocalDate.parse(fechaStr);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto. Usando la fecha existente.");
            }
        }

        System.out.print("Nueva Dirección (" + personaExistente.getDireccion() + "): ");
        String direccion = sc.nextLine();
        if (direccion.isEmpty()) direccion = personaExistente.getDireccion();

        System.out.print("Nuevo Número de Teléfono (" + personaExistente.getNumeroTelefono() + "): ");
        String numeroTelefono = sc.nextLine();
        if (numeroTelefono.isEmpty()) numeroTelefono = personaExistente.getNumeroTelefono();

        System.out.print("Nuevo Correo Electrónico (" + personaExistente.getCorreoElectronico() + "): ");
        String correoElectronico = sc.nextLine();
        if (correoElectronico.isEmpty()) correoElectronico = personaExistente.getCorreoElectronico();

        //específicos por tipo de Persona
        String contrasena = null; // Solo para Admin
        String especialidad = null;
        String licencia = null;
        String preferencias = null;
        String infoAdopcion = null;

        if (personaExistente instanceof Admin) {
            System.out.print("Nueva Contraseña (deje en blanco para mantener la actual): ");
            contrasena = sc.nextLine();
        } else if (personaExistente instanceof Veterinario) {
            Veterinario vet = (Veterinario) personaExistente;
            System.out.print("Nueva Especialidad (" + vet.getEspecialidad() + "): ");
            especialidad = sc.nextLine();
            if (especialidad.isEmpty()) especialidad = vet.getEspecialidad();

            System.out.print("Nueva Licencia (" + vet.getLicencia() + "): ");
            licencia = sc.nextLine();
            if (licencia.isEmpty()) licencia = vet.getLicencia();
        } else if (personaExistente instanceof Adoptante) {
            Adoptante adop = (Adoptante) personaExistente;
            System.out.print("Nuevas Preferencias (" + adop.getPreferencias() + "): ");
            preferencias = sc.nextLine();
            if (preferencias.isEmpty()) preferencias = adop.getPreferencias();

            System.out.print("Nueva Información de Adopción (" + adop.getInformacionAdopcion() + "): ");
            infoAdopcion = sc.nextLine();
            if (infoAdopcion.isEmpty()) infoAdopcion = adop.getInformacionAdopcion();
        }

        if (controlador.actualizarDatosUsuario(id, nombre, rut, fechaNacimiento,
                direccion, numeroTelefono, correoElectronico,
                contrasena, especialidad, licencia,
                preferencias, infoAdopcion)) {
            System.out.println("Usuario actualizado exitosamente.");
        } else {
            System.out.println("Error al actualizar usuario o ID no encontrado.");
        }
    }

    private void eliminarUsuario() {
        System.out.println("\n--- Eliminar Usuario ---");
        System.out.print("Ingrese el ID del usuario a eliminar: ");
        String id = sc.nextLine();

        if (controlador.eliminarUsuario(id)) {
            System.out.println("Usuario eliminado exitosamente.");
        } else {
            System.out.println("Error al eliminar usuario o ID no encontrado.");
        }
    }
}