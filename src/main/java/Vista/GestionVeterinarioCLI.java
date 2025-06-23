package Vista;

import Controlador.VeterinarioControlador;
import Modelo.Veterinario;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GestionVeterinarioCLI {
    private Scanner sc;
    private VeterinarioControlador controlador;

    public GestionVeterinarioCLI(VeterinarioControlador controlador) {
        this.sc = new Scanner(System.in);
        this.controlador = controlador;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- Módulo Gestión de Veterinarios ---");
            System.out.println("1. Registrar nuevo Veterinario");
            System.out.println("2. Ver todos los Veterinarios");
            System.out.println("3. Buscar Veterinario por ID");
            System.out.println("4. Actualizar datos de Veterinario");
            System.out.println("0. Volver al menú principal");
            System.out.print("Ingrese una opción: ");

            try {
                opcion = sc.nextInt();
                sc.nextLine(); // Consumir el salto de línea

                switch (opcion) {
                    case 1:
                        registrarVeterinario();
                        break;
                    case 2:
                        verTodosLosVeterinarios();
                        break;
                    case 3:
                        buscarVeterinarioPorId();
                        break;
                    case 4:
                        actualizarDatosVeterinario();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                sc.nextLine();
                opcion = -1; // Para que el bucle cntinue
            }
        } while (opcion != 0);
    }

    private void registrarVeterinario() {
        System.out.println("\n--- Registrar Nuevo Veterinario ---");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("RUT (ej: 12345678-9): "); // pedir rut
        String rut = sc.nextLine();

        LocalDate fechaNacimiento = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            System.out.print("Fecha de Nacimiento (YYYY-MM-DD): ");
            String fechaStr = sc.nextLine();
            try {
                fechaNacimiento = LocalDate.parse(fechaStr);
                fechaValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use YYYY-MM-DD.");
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

        controlador.registrarVeterinario(nombre, rut, fechaNacimiento, direccion, numeroTelefono,
                correoElectronico, especialidad, licencia);
    }

    private void verTodosLosVeterinarios() {
        System.out.println("\n--- Todos los Veterinarios ---");
        for (Veterinario vet : controlador.obtenerTodosLosVeterinarios()) {
            System.out.println(vet);
        }
    }

    private void actualizarDatosVeterinario() {
        System.out.println("\n--- Actualizar Datos de Veterinario ---");
        System.out.print("Ingrese el ID del veterinario a actualizar: ");
        String id = sc.nextLine();
        Veterinario veterinarioExistente = controlador.buscarVeterinarioPorld(id);

        if (veterinarioExistente != null) {
            System.out.println("Veterinario actual: " + veterinarioExistente);

            System.out.print("Nuevo Nombre (" + veterinarioExistente.getNombre() + "): ");
            String nombre = sc.nextLine();
            if (nombre.isEmpty()) nombre = veterinarioExistente.getNombre();

            System.out.print("Nuevo RUT (" + veterinarioExistente.getRut() + "): "); // cctualizar RUT
            String rut = sc.nextLine();
            if (rut.isEmpty()) rut = veterinarioExistente.getRut();

            LocalDate fechaNacimiento = null;
            boolean fechaValida = false;
            while (!fechaValida) {
                System.out.print("Nueva Fecha de Nacimiento (YYYY-MM-DD) (" +
                        veterinarioExistente.getFechaNacimiento() + "): ");
                String fechaStr = sc.nextLine();
                if (fechaStr.isEmpty()) {
                    fechaNacimiento = veterinarioExistente.getFechaNacimiento();
                    fechaValida = true;
                } else {
                    try {
                        fechaNacimiento = LocalDate.parse(fechaStr);
                        fechaValida = true;
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato de fecha inválido. Use YYYY-MM-DD.");
                    }
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
            System.out.print("Nuevo Número de Licencia (" + veterinarioExistente.getLicencia() + "): ");
            String licencia = sc.nextLine();
            if (licencia.isEmpty()) licencia = veterinarioExistente.getLicencia();

            controlador.actualizarVeterinario(id, nombre, rut, fechaNacimiento, direccion,
                    numeroTelefono, correoElectronico, especialidad, licencia);
        } else {
            System.out.println("Veterinario con ID " + id + " no encontrado.");
        }
    }

    private void buscarVeterinarioPorId() {
        System.out.println("\n--- Buscar Veterinario por ID ---");
        System.out.print("Ingrese el ID del veterinario a buscar: ");
        String id = sc.nextLine();
        Veterinario veterinario = controlador.buscarVeterinarioPorld(id);
        if (veterinario != null) {
            System.out.println("\n--- Detalles del Veterinario ---");
            System.out.println(veterinario);
        } else {
            System.out.println("Veterinario con ID " + id + " no encontrado.");
        }
    }
}