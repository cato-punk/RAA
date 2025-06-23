package Vista;

import Controlador.RescatistaControlador;
import Controlador.AnimalControlador; //pueda informar rescates
import Modelo.Animal; // para mostrar detalles de animales rescatados
import Modelo.Rescatista;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class RescateCLI {

    private Scanner sc;
    private RescatistaControlador rescatistaControlador;
    private AnimalControlador animalControlador; // informar rescate

    public RescateCLI(RescatistaControlador rescatistaControlador, AnimalControlador animalControlador) {
        this.sc = new Scanner(System.in);
        this.rescatistaControlador = rescatistaControlador;
        this.animalControlador = animalControlador;
    }

    public void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n--- MENÚ RESCATISTA ---");
            System.out.println("1. Registrarse como Rescatista");
            System.out.println("2. Iniciar sesión como Rescatista");
            System.out.println("3. Salir");
            System.out.print("Ingrese su opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    registrarNuevoRescatista();
                    break;
                case "2":
                    if (iniciarSesionRescatista()) {
                        mostrarMenuRescatistaLogueado(); // Menú específico tras login
                    }
                    break;
                case "3":
                    System.out.println("Saliendo del menú de rescatistas.");
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


        if (rescatistaControlador.registrarRescatista(nombre, rut, fechaNacimiento, direccion,
                numeroTelefono, correoElectronico)) {
            System.out.println("Rescatista registrado exitosamente.");
        } else {
            System.out.println("Error al registrar rescatista. Posiblemente el correo ya está en uso.");
        }
    }

    private boolean iniciarSesionRescatista() {
        System.out.println("\n--- Iniciar Sesión Rescatista ---");
        System.out.print("Ingrese su correo electrónico: ");
        String correo = sc.nextLine();

        if (rescatistaControlador.iniciarSesion(correo)) { // se pasa solo el correo
            System.out.println("¡Bienvenido/a Rescatista " + rescatistaControlador.getRescatistaActual().getNombre() + "!");
            return true;
        } else {
            System.out.println("Correo electrónico no encontrado o no corresponde a un Rescatista.");
            return false;
        }
    }

    private void mostrarMenuRescatistaLogueado() {
        while (true) {
            System.out.println("\n--- MENÚ DE RESCATISTA LOGUEADO ---");
            System.out.println("1. Informar nuevo rescate");
            System.out.println("2. Ver mis datos");
            System.out.println("3. Cerrar sesión");
            System.out.print("Ingrese su opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    informarNuevoRescate();
                    break;
                case "2":
                    System.out.println("Funcionalidad 'Ver mis datos' en desarrollo.");
                    if (rescatistaControlador.getRescatistaActual() != null) {
                        System.out.println(rescatistaControlador.getRescatistaActual().toString());
                    }
                    break;
                case "3":
                    rescatistaControlador.cerrarSesion();
                    System.out.println("Sesión cerrada. Volviendo al menú principal de Rescatista.");
                    return; // Salir de este menú y volver al principal de Rescatista
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void informarNuevoRescate() {
        System.out.println("\n--- Informar Nuevo Rescate ---");
        //que hay un rescatista logueado
        if (rescatistaControlador.getRescatistaActual() == null) {
            System.out.println("Debe iniciar sesión como rescatista para informar un rescate.");
            return;
        }

        System.out.print("Especie del animal (ej: Perro, Gato): ");
        String especie = sc.nextLine();
        System.out.print("Raza del animal: ");
        String raza = sc.nextLine();
        System.out.print("Sexo del animal (Macho/Hembra): "); //ups casi pongo femenno o masculino
        String sexo = sc.nextLine();
        int edad = -1;
        while (edad < 0) {
            System.out.print("Edad del animal aproximada (en años): ");
            try {
                edad = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido para la edad.");
            }
        }
        System.out.print("Descripción de salud (ej: herido, desnutrido, sano): ");
        String estadoSalud = sc.nextLine();
        System.out.print("Ubicación del rescate: ");
        String ubicacion = sc.nextLine();

        // El ID del rescatista logueado se obtiene del controlador
        String idRescatista = rescatistaControlador.getRescatistaActual().getId();

        if (animalControlador.registrarAnimal(especie, raza, sexo, edad, estadoSalud, ubicacion, idRescatista)) {
            System.out.println("Rescate informado y animal registrado exitosamente.");
        } else {
            System.out.println("Error al informar el rescate o registrar el animal.");
        }
    }
}