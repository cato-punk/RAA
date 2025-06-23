package Vista;

import Controlador.RescatistaControlador;
import Modelo.Rescatista;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;

public class RescateCLI {

    private Scanner sc;
    private RescatistaControlador controlador;

    public RescateCLI(RescatistaControlador controlador) {
        this.sc = new Scanner(System.in);
        this.controlador = controlador;
    }

    public void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n--- MENÚ RESCATISTA ---");
            System.out.println("1. Registrar nuevo rescatista");
            System.out.println("2. Iniciar sesión como Rescatista"); //NUEVOO000
            System.out.println("3. Listar todos los rescatistas");
            System.out.println("4. Buscar rescatista por ID");
            System.out.println("5. Actualizar rescatista");
            System.out.println("6. Eliminar rescatista");
            System.out.println("7. Salir");
            System.out.print("Ingrese su opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    registrarNuevoRescatista();
                    break;
                case "2": // NUEVO
                    if (iniciarSesionRescatista()) {
                        mostrarMenuRescatistaLogueado(); // MenU específico tras login
                    }
                    break;
                case "3":
                    listarRescatistas();
                    break;
                case "4":
                    buscarRescatistaPorId();
                    break;
                case "5":
                    actualizarRescatista();
                    break;
                case "6":
                    eliminarRescatista();
                    break;
                case "7":
                    System.out.println("Saliendo del programa de gestión de rescatistas.");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }

    private void registrarNuevoRescatista() {
        System.out.println("\n--- Registrar Nuevo Rescatista ---");
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

        System.out.print("Contraseña: "); // ¡NUEVO! Pedir contraseña
        String contrasena = sc.nextLine();

        if (controlador.registrarRescatista(nombre, rut, fechaNacimiento, direccion,
                numeroTelefono, correoElectronico, contrasena)) {
            System.out.println("Rescatista registrado exitosamente.");
        } else {
            System.out.println("Error al registrar rescatista. Posiblemente el correo ya está en uso.");
        }
    }

    // Para el inicio de sesión del rescatista
    private boolean iniciarSesionRescatista() {
        System.out.println("\n--- Iniciar Sesión Rescatista ---");
        System.out.print("Ingrese su correo electrónico: ");
        String correo = sc.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contrasena = sc.nextLine();

        if (controlador.iniciarSesion(correo, contrasena)) {
            System.out.println("¡Bienvenido " + controlador.getRescatistaActual().getNombre() + "!");
            return true;
        } else {
            System.out.println("Credenciales incorrectas o usuario no registrado como Rescatista.");
            return false;
        }
    }

    // MenU para el rescatista una vez logueado
    private void mostrarMenuRescatistaLogueado() {
        while (true) {
            System.out.println("\n--- MENÚ DE RESCATISTA LOGUEADO ---");
            System.out.println("1. Realizar un nuevo rescate (Pendiente de implementar lógica)");
            System.out.println("2. Ver mis datos (Pendiente de implementar lógica)");
            System.out.println("3. Cerrar sesión");
            System.out.print("Ingrese su opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("Funcionalidad 'Realizar un nuevo rescate' en desarrollo.");
                    // Aquí iría la lógica para el registro de resccate
                    break;
                case "2":
                    System.out.println("Funcionalidad 'Ver mis datos' en desarrollo.");
                    // Aquí se mostrarían los datos del rescatistaActual
                    if (controlador.getRescatistaActual() != null) {
                        System.out.println(controlador.getRescatistaActual().toString());
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


    private void listarRescatistas() {
        System.out.println("\n--- Listado de Rescatistas ---");
        ArrayList<Rescatista> rescatistas = controlador.listarTodosRescatistas();
        if (rescatistas.isEmpty()) {
            System.out.println("No hay rescatistas registrados.");
        } else {
            for (Rescatista res : rescatistas) {
                System.out.println(res.toString()); // Usará el toString modificado de Rescatista
            }
        }
    }

    private void buscarRescatistaPorId() {
        System.out.println("\n--- Buscar Rescatista por ID ---");
        System.out.print("Ingrese el ID del rescatista a buscar: ");
        String id = sc.nextLine();
        Rescatista rescatista = controlador.buscarRescatistaPorld(id);
        if (rescatista != null) {
            System.out.println("Rescatista encontrado: " + rescatista.toString());
        } else {
            System.out.println("Rescatista no encontrado.");
        }
    }

    private void actualizarRescatista() {
        System.out.println("\n--- Actualizar Rescatista ---");
        System.out.print("Ingrese el ID del rescatista a actualizar: ");
        String id = sc.nextLine();

        Rescatista rescatistaExistente = controlador.buscarRescatistaPorld(id);
        if (rescatistaExistente == null) {
            System.out.println("Rescatista no encontrado.");
            return;
        }

        System.out.println("De