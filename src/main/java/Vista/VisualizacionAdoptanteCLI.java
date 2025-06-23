package Vista;

import Controlador.AdoptanteControlador;
import Modelo.Animal;
import Modelo.Adoptante;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class VisualizacionAdoptanteCLI {

    private Scanner sc;
    private AdoptanteControlador adoptanteControlador;

    public VisualizacionAdoptanteCLI(AdoptanteControlador adoptanteControlador) {
        this.sc = new Scanner(System.in);
        this.adoptanteControlador = adoptanteControlador;
    }

    public void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n--- MENÚ ADOPTANTE ---");
            System.out.println("1. Registrarse como Adoptante");
            System.out.println("2. Iniciar sesión como Adoptante");
            System.out.println("3. Ver animales disponibles para adopción"); // Puede ser visto sin login
            System.out.println("4. Salir");
            System.out.print("Ingrese su opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    registrarNuevoAdoptante();
                    break;
                case "2":
                    if (iniciarSesionAdoptante()) {
                        mostrarMenuAdoptanteLogueado(); // Menu específico tras login
                    }
                    break;
                case "3":
                    mostrarAnimalesDisponibles(); // Opción pública
                    break;
                case "4":
                    System.out.println("Saliendo del programa de adopción.");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }

    private void registrarNuevoAdoptante() {
        System.out.println("\n--- Registrar Nuevo Adoptante ---");
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

        System.out.print("Preferencias (ej: perro pequeño, gato, etc.): ");
        String preferencias = sc.nextLine();

        System.out.print("Información de Adopción (ej: experiencia previa, tamaño de vivienda): ");
        String informacionAdopcion = sc.nextLine();

        if (adoptanteControlador.registrarAdoptante(nombre, rut, fechaNacimiento, direccion,
                numeroTelefono, correoElectronico, preferencias, informacionAdopcion)) { // Se pasa sin contraseña
            System.out.println("Adoptante registrado exitosamente.");
        } else {
            System.out.println("Error al registrar adoptante. Posiblemente el correo ya está en uso.");
        }
    }

    private boolean iniciarSesionAdoptante() {
        System.out.println("\n--- Iniciar Sesión Adoptante ---");
        System.out.print("Ingrese su correo electrónico: ");
        String correo = sc.nextLine();

        if (adoptanteControlador.iniciarSesion(correo)) { // Se pasa solo el correo
            System.out.println("¡Bienvenido/a " + adoptanteControlador.getAdoptanteActual().getNombre() + "!");
            return true;
        } else {
            System.out.println("Correo electrónico no encontrado o no corresponde a un Adoptante.");
            return false;
        }
    }

    private void mostrarMenuAdoptanteLogueado() {
        while (true) {
            System.out.println("\n--- MENÚ DE ADOPTANTE LOGUEADO ---");
            System.out.println("1. Ver animales disponibles para adopción");
            System.out.println("2. Adoptar un animal (por ID)");
            System.out.println("3. Ver mi historial de adopciones");
            System.out.println("4. Ver mis datos");
            System.out.println("5. Cerrar sesión");
            System.out.print("Ingrese su opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    mostrarAnimalesDisponibles();
                    break;
                case "2":
                    adoptarAnimal();
                    break;
                case "3":
                    mostrarHistorialAdopciones();
                    break;
                case "4":
                    System.out.println("Funcionalidad 'Ver mis datos' en desarrollo.");
                    if (adoptanteControlador.getAdoptanteActual() != null) {
                        System.out.println(adoptanteControlador.getAdoptanteActual().toString());
                    }
                    break;
                case "5":
                    adoptanteControlador.cerrarSesion();
                    System.out.println("Sesión cerrada. Volviendo al menú principal de Adoptante.");
                    return; // Salir de este menú y volver al principal de Adoptante
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void mostrarAnimalesDisponibles() {
        System.out.println("\n--- Animales Disponibles para Adopción ---");
        List<Animal> animales = adoptanteControlador.obtenerAnimalesDisponibles();
        if (animales.isEmpty()) {
            System.out.println("No hay animales disponibles para adopción en este momento.");
        } else {
            animales.forEach(animal -> System.out.println(animal.toString())); // Asumiendo que Animal.toString() es descriptivo
        }
    }

    private void adoptarAnimal() {
        System.out.println("\n--- Adoptar un Animal ---");
        System.out.print("Ingrese el ID del animal que desea adoptar: ");
        String idAnimal = sc.nextLine();

        if (adoptanteControlador.adoptarAnimal(idAnimal)) {
            System.out.println("Solicitud de adopción enviada para el animal con ID: " + idAnimal + ". Un veterinario se pondrá en contacto.");
        } else {
            System.out.println("No se pudo procesar la solicitud de adopción. Verifique el ID del animal.");
        }
    }

    private void mostrarHistorialAdopciones() {
        System.out.println("\n--- Mi Historial de Adopciones ---");
        List<Animal> adopciones = adoptanteControlador.obtenerHistorialAdopciones();
        if (adopciones.isEmpty()) {
            System.out.println("Aún no tienes adopciones registradas.");
        } else {
            adopciones.forEach(animal -> System.out.println(animal.toString()));
        }
    }
}