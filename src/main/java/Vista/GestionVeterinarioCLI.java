package Vista;

import Controlador.VeterinarioControlador;
import Modelo.Veterinario;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GestionVeterinarioCLI {
    private final Scanner sc;
    private final VeterinarioControlador controlador;

    public GestionVeterinarioCLI(Scanner sc, VeterinarioControlador controlador) {
        this.sc = sc;
        this.controlador = controlador;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Veterinarios ---");
            System.out.println("1. Registrar nuevo veterinario");
            System.out.println("2. Listar todos los veterinarios");
            System.out.println("3. Actualizar datos de veterinario");
            System.out.println("4. Buscar veterinario por ID");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                sc.nextLine();
                opcion = -1; // Para que el bucle continúe
            }

            switch (opcion) {
                case 1 -> registrarVeterinario();
                case 2 -> listarVeterinarios();
                case 3 -> actualizarVeterinario();
                case 4 -> buscarVeterinarioPorId();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private void registrarVeterinario() {
        System.out.println("\n--- Registro de Nuevo Veterinario ---");
        System.out.print("Nombre completo: ");
        String nombre = sc.nextLine();
        System.out.print("RUT: ");
        String rut = sc.nextLine();

        LocalDate fechaNacimiento = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            System.out.print("Fecha de Nacimiento (YYYY-MM-DD): ");
            String fechaNacStr = sc.nextLine();
            try {
                fechaNacimiento = LocalDate.parse(fechaNacStr);
                fechaValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use YYYY-MM-DD.");
            }
        }

        System.out.print("Dirección completa: ");
        String direccion = sc.nextLine();
        System.out.print("Número de Teléfono: ");
        String numeroTelefono = sc.nextLine();
        System.out.print("Correo Electrónico: ");
        String correoElectronico = sc.nextLine();
        System.out.print("Especialidad: ");
        String especialidad = sc.nextLine();
        System.out.print("Número de Licencia: ");
        String licencia = sc.nextLine();

        controlador.registrarVeterinario(nombre, rut, fechaNacimiento, direccion,
                numeroTelefono, correoElectronico, especialidad, licencia);
    }

    private void listarVeterinarios() {
        System.out.println("\n--- Listado de Veterinarios ---");
        var lista = controlador.obtenerTodosLosVeterinarios();
        if (lista.isEmpty()) {
            System.out.println("No hay veterinarios registrados.");
        } else {
            for (Veterinario v : lista) {
                System.out.println(v);
            }
        }
    }

    private void actualizarVeterinario() {
        System.out.println("\n--- Actualizar Datos de Veterinario ---");
        System.out.print("Ingrese el ID del veterinario a actualizar: ");
        String id = sc.nextLine();

        Veterinario veterinarioExistente = controlador.buscarVeterinarioPorId(id);
        if (veterinarioExistente == null) {
            System.out.println("Veterinario con ID " + id + " no encontrado.");
            return;
        }

        System.out.println("Veterinario actual: " + veterinarioExistente);
        System.out.println("Ingrese los nuevos datos (deje en blanco para mantener el actual):");

        System.out.print("Nuevo Nombre completo (" + veterinarioExistente.getNombre() + "): ");
        String nombre = sc.nextLine();
        if (nombre.isEmpty()) nombre = veterinarioExistente.getNombre();

        System.out.print("Nuevo RUT (" + veterinarioExistente.getRut() + "): ");
        String rut = sc.nextLine();
        if (rut.isEmpty()) rut = veterinarioExistente.getRut();

        LocalDate fechaNacimiento = veterinarioExistente.getFechaNacimiento();
        System.out.print("Nueva Fecha de Nacimiento (YYYY-MM-DD, actual: " + (fechaNacimiento != null ? fechaNacimiento : "N/A") + "): ");
        String fechaNacStr = sc.nextLine();
        if (!fechaNacStr.isEmpty()) {
            try {
                fechaNacimiento = LocalDate.parse(fechaNacStr);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Se mantiene la fecha anterior.");
            }
        }

        System.out.print("Nueva Dirección completa (" + veterinarioExistente.getDireccion() + "): ");
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

        System.out.print("Nuevo Número de Licencia (" + veterinarioExistente.getLicencia() + "): ");
        String licencia = sc.nextLine();
        if (licencia.isEmpty()) licencia = veterinarioExistente.getLicencia();


        controlador.actualizarVeterinario(id, nombre, rut, fechaNacimiento, direccion,
                numeroTelefono, correoElectronico, especialidad, licencia);
    }

    private void buscarVeterinarioPorId() {
        System.out.println("\n--- Buscar Veterinario por ID ---");
        System.out.print("Ingrese el ID del veterinario a buscar: ");
        String id = sc.nextLine();

        Veterinario veterinario = controlador.buscarVeterinarioPorId(id);
        if (veterinario != null) {
            System.out.println("Veterinario encontrado: " + veterinario);
        } else {
            System.out.println("Veterinario con ID " + id + " no encontrado.");
        }
    }
}