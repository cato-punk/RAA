package Vista;

import Controlador.VeterinarioControlador;
import Modelo.Veterinario;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class GestionVeterinarioCLI {

    private Scanner sc;
    private VeterinarioControlador controlador;

    public GestionVeterinarioCLI(VeterinarioControlador controlador) {
        this.sc = new Scanner(System.in);
        this.controlador = controlador;
    }

    public void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n--- MENÚ GESTIÓN VETERINARIO ---");
            System.out.println("1. Registrar nuevo veterinario");
            System.out.println("2. Iniciar sesión como Veterinario"); // ¡NUEVO!
            System.out.println("3. Listar todos los veterinarios");
            System.out.println("4. Buscar veterinario por ID");
            System.out.println("5. Actualizar veterinario");
            System.out.println("6. Eliminar veterinario");
            System.out.println("7. Salir");
            System.out.print("Ingrese su opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    registrarNuevoVeterinario();
                    break;
                case "2":
                    if (iniciarSesionVeterinario()) {
                        mostrarMenuVeterinarioLogueado(); // específico tras login
                    }
                    break;
                case "3":
                    listarVeterinarios();
                    break;
                case "4":
                    buscarVeterinarioPorId();
                    break;
                case "5":
                    actualizarVeterinario();
                    break;
                case "6":
                    eliminarVeterinario();
                    break;
                case "7":
                    System.out.println("Saliendo del programa de gestión de veterinarios.");
                    return; // Sale del método y, por ende, del programa si es el main
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }

    private void registrarNuevoVeterinario() {
        System.out.println("\n--- Registrar Nuevo Veterinario ---");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("RUT (ej: 12345678-9): ");
        String rut = sc.nextLine();

        LocalDate fechaNacimiento = null;
        while (fechaNacimiento == null) {
            System.out.print("Fecha de Nacimiento (AAAA-MM-DD): ");
            try {
                fechaNacimiento = LocalDate.parse(sc.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto. Use AAAA-MM-DD.");
            }
        }

        System.out.print("Dirección: ");
        String direccion = sc.nextLine();

        System.out.print("Número de Teléfono: ");
        String numeroTelefono = sc.nextLine();

        System.out.print("Correo Electrónico: ");
        String correoElectronico = sc.nextLine();

        System.out.print("Especialidad: ");
        String especialidad = sc.nextLine();

        System.out.print("Número de Licencia: ");
        String licencia = sc.nextLine();

        System.out.print("Contraseña: "); // ¡NUEVO! Pedir contraseña
        String contrasena = sc.nextLine();

        if (controlador.registrarVeterinario(nombre, rut, fechaNacimiento, direccion,
                numeroTelefono, correoElectronico, especialidad, licencia, contrasena)) {
            System.out.println("Veterinario registrado exitosamente.");
        } else {
            System.out.println("Error al registrar veterinario. Posiblemente el correo ya está en uso.");
        }
    }

    // ¡NUEVO MÉTODO! Para el inicio de sesión del veterinario
    private boolean iniciarSesionVeterinario() {
        System.out.println("\n--- Iniciar Sesión Veterinario ---");
        System.out.print("Ingrese su correo electrónico: ");
        String correo = sc.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contrasena = sc.nextLine();

        if (controlador.iniciarSesion(correo, contrasena)) {
            System.out.println("¡Bienvenido Dr./Dra. " + controlador.getVeterinarioActual().getNombre() + "!");
            return true;
        } else {
            System.out.println("Credenciales incorrectas o usuario no registrado como Veterinario.");
            return false;
        }
    }

    // ¡NUEVO MÉTODO! Menú para el veterinario una vez logueado
    private void mostrarMenuVeterinarioLogueado() {
        while (true) {
            System.out.println("\n--- MENÚ DE VETERINARIO LOGUEADO ---");
            System.out.println("1. Actualizar estado de salud de animal (Pendiente de implementar)");
            System.out.println("2. Ver mis datos");
            System.out.println("3. Cerrar sesión");
            System.out.print("Ingrese su opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("Funcionalidad 'Actualizar estado de salud de animal' en desarrollo.");
                    // Aquí iría la lógica para actualizar el estado de un animal
                    break;
                case "2":
                    System.out.println("Funcionalidad 'Ver mis datos' en desarrollo.");
                    if (controlador.getVeterinarioActual() != null) {
                        System.out.println(controlador.getVeterinarioActual().toString());
                    }
                    break;
                case "3":
                    controlador.cerrarSesion();
                    System.out.println("Sesión cerrada. Volviendo al menú principal.");
                    return; // Salir de este menú y volver al principal
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void listarVeterinarios() {
        System.out.println("\n--- Listado de Veterinarios ---");
        ArrayList<Veterinario> veterinarios = controlador.listarTodosVeterinarios();
        if (veterinarios.isEmpty()) {
            System.out.println("No hay veterinarios registrados.");
        } else {
            for (Veterinario vet : veterinarios) {
                System.out.println(vet.toString());
            }
        }
    }

    private void buscarVeterinarioPorId() {
        System.out.println("\n--- Buscar Veterinario por ID ---");
        System.out.print("Ingrese el ID del veterinario a buscar: ");
        String id = sc.nextLine();
        Veterinario veterinario = controlador.buscarVeterinarioPorld(id);
        if (veterinario != null) {
            System.out.println("Veterinario encontrado: " + veterinario.toString());
        } else {
            System.out.println("Veterinario no encontrado.");
        }
    }

    private void actualizarVeterinario() {
        System.out.println("\n--- Actualizar Veterinario ---");
        System.out.print("Ingrese el ID del veterinario a actualizar: ");
        String id = sc.nextLine();

        Veterinario veterinarioExistente = controlador.buscarVeterinarioPorld(id);
        if (veterinarioExistente == null) {
            System.out.println("Veterinario no encontrado.");
            return;
        }

        System.out.println("Deje en blanco los campos que no desea actualizar.");

        System.out.print("Nuevo Nombre (" + veterinarioExistente.getNombre() + "): ");
        String nombre = sc.nextLine();
        if (nombre.isEmpty()) nombre = veterinarioExistente.getNombre();

        System.out.print("Nuevo RUT (" + veterinarioExistente.getRut() + "): ");
        String rut = sc.nextLine();
        if (rut.isEmpty()) rut = veterinarioExistente.getRut();

        LocalDate fechaNacimiento = veterinarioExistente.getFechaNacimiento();
        System.out.print("Nueva Fecha de Nacimiento (AAAA-MM-DD) (" + fechaNacimiento + "): ");
        String fechaStr = sc.nextLine();
        if (!fechaStr.isEmpty()) {
            try {
                fechaNacimiento = LocalDate.parse(fechaStr);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto. Usando la fecha existente.");
            }
        }

        System.out.print("Nueva Dirección (" + veterinarioExistente.getDireccion() + "): ");
        String direccion = sc.nextLine();
        if (direccion.isEmpty()) direccion = veterinarioExistente.getDireccion();

        System.out.print("Nuevo Número de Teléfono (" + veterinarioExistente.getNumeroTelefono() + "): ");
        String numeroTelefono = sc.nextLine();
        if (numeroTelefono.isEmpty()) numeroTelefono = veterinarioExistente.getNumeroTelefono();

        System.out.print("Nuevo Correo Electrónico (" + veterinarioExistente.getCorreoElectronico() + "): ");
        String correoElectronico = sc.nextLine();
        if (correoElectronico.isEmpty()) correoElectronico = veterinarioExistente.getCorreoElectronico();

        System.out.print("Nueva Especialidad (" + veterinarioExistente.getEspecialidad() + "): ");
        String especialidad = sc.nextLine();
        if (especialidad.isEmpty()) especialidad = veterinarioExistente.getEspecialidad();

        System.out.print("Nueva Licencia (" + veterinarioExistente.getLicencia() + "): ");
        String licencia = sc.nextLine();
        if (licencia.isEmpty()) licencia = veterinarioExistente.getLicencia();

        System.out.print("Nueva Contraseña (deje en blanco para mantener la actual): "); // ¡NUEVO!
        String contrasena = sc.nextLine(); // Se pasará null/vacío si no se actualiza

        if (controlador.actualizarVeterinario(id, nombre, rut, fechaNacimiento, direccion,
                numeroTelefono, correoElectronico, especialidad, licencia, contrasena)) {
            System.out.println("Veterinario actualizado exitosamente.");
        } else {
            System.out.println("Error al actualizar veterinario.");
        }
    }

    private void eliminarVeterinario() {
        System.out.println("\n--- Eliminar Veterinario ---");
        System.out.print("Ingrese el ID del veterinario a eliminar: ");
        String id = sc.nextLine();
        if (controlador.eliminarVeterinario(id)) {
            System.out.println("Veterinario eliminado exitosamente.");
        } else {
            System.out.println("Error al eliminar veterinario o ID no encontrado.");
        }
    }
}