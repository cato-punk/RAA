package Vista;

import Controlador.AdoptanteControlador;
import Modelo.Adoptante;
import Modelo.Animal;
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
            System.out.println("3. Salir");
            System.out.print("Ingrese su opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    registrarNuevoAdoptante();
                    break;
                case "2":
                    if (iniciarSesionAdoptante()) {
                        mostrarMenuAdoptanteLogueado();
                    }
                    break;
                case "3":
                    System.out.println("Saliendo del menú de adoptantes.");
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

        System.out.print("Preferencias (ej: Perros pequeños, Gatos tranquilos): ");
        String preferencias = sc.nextLine();

        if (adoptanteControlador.registrarAdoptante(nombre, rut, fechaNacimiento, direccion,
                numeroTelefono, correoElectronico, preferencias)) {
            System.out.println("Adoptante registrado exitosamente.");
        } else {
            System.out.println("Error al registrar adoptante. Posiblemente el correo ya está en uso.");
        }
    }

    private boolean iniciarSesionAdoptante() {
        System.out.println("\n--- Iniciar Sesión Adoptante ---");
        System.out.print("Ingrese su correo electrónico: ");
        String correo = sc.nextLine();

        if (adoptanteControlador.iniciarSesion(correo)) {
            System.out.println("¡Bienvenido/a Adoptante " + adoptanteControlador.getAdoptanteActual().getNombre() + "!");
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
            System.out.println("2. Adoptar un animal"); // tenemos la funcionalidad
            System.out.println("3. Ver mi historial de adopciones"); // esta igual
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
                    return;
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
            animales.forEach(animal -> {
                System.out.println("--------------------");
                System.out.println("ID: " + animal.getId());
                System.out.println("Especie: " + animal.getEspecie());
                System.out.println("Raza: " + animal.getRaza());
                System.out.println("Sexo: " + animal.getSexo());
                System.out.println("Edad: " + animal.getEdadAproximadaAnios() + " años");
                System.out.println("Estado de Salud: " + animal.getEstadoSalud());
                System.out.println("Lugar encontrado: " + animal.getLugarEncontrado());
            });
            System.out.println("--------------------");
        }
    }

    // para adoptar un animal
    private void adoptarAnimal() {
        System.out.println("\n--- ADOPTAR UN ANIMAL ---");
        // mostrar los animales disponibles
        mostrarAnimalesDisponibles();

        if (adoptanteControlador.getAdoptanteActual() == null) {
            return; // Ya se maneja en el controlador, pero es un doble chequeo (este fue recomendaod)
        }

        System.out.print("\nIngrese el ID del animal que desea adoptar: ");
        String idAnimal = sc.nextLine();

        System.out.print("¿Está seguro de que desea adoptar este animal? (si/no): ");
        String confirmacion = sc.nextLine().trim().toLowerCase();

        if ("si".equals(confirmacion)) {
            adoptanteControlador.adoptarAnimal(idAnimal);
        } else {
            System.out.println("Adopción cancelada.");
        }
    }

    // para mostrar el historial de adopciones
    private void mostrarHistorialAdopciones() {
        System.out.println("\n--- TU HISTORIAL DE ADOPCIONES ---");
        List<Animal> adopciones = adoptanteControlador.obtenerHistorialAdopciones();

        if (adopciones.isEmpty()) {
            System.out.println("Aún no has adoptado ningún animal.");
        } else {
            adopciones.forEach(animal -> {
                System.out.println("--------------------");
                System.out.println("ID: " + animal.getId());
                System.out.println("Especie: " + animal.getEspecie());
                System.out.println("Raza: " + animal.getRaza());
                System.out.println("Sexo: " + animal.getSexo());
                System.out.println("Edad: " + animal.getEdadAproximadaAnios() + " años");
                System.out.println("Estado de Adopción: " + animal.getEstadoSalud());
                System.out.println("Fecha de Rescate: " + (animal.getFechaHoraRescate() != null ? animal.getFechaHoraRescate().toLocalDate() : "N/A"));
                System.out.println("Adoptado por (ID): " + (animal.getIdAdoptante() != null ? animal.getIdAdoptante() : "N/A"));
            });
            System.out.println("--------------------");
        }
    }
}