package Vista;

import Controlador.AnimalControlador;
import Controlador.RescatistaControlador;
import Modelo.Rescatista;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException; //para scanner, es un "esto no es lo que esperaba"
import java.util.Scanner;

public class RescateCLI {
    private Scanner scanner;
    private AnimalControlador animalControlador;
    private RescatistaControlador rescatistaControlador;

    public RescateCLI(AnimalControlador animalControlador, RescatistaControlador rescatistaControlador) {
        this.scanner = new Scanner(System.in);
        this.animalControlador = animalControlador;
        this.rescatistaControlador = rescatistaControlador;
    }

    public void mostrarMenuRescate() {
        int opcion;
        do {
            System.out.println("\n--- Módulo Rescate ---");
            System.out.println("1. Registrar nuevo rescate de animal");
            System.out.println("2. Registrarme como Rescatista (si aún no lo está)");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                switch (opcion) {
                    case 1:
                        solicitarDatosRescate();
                        break;
                    case 2:
                        registrarNuevoRescatista();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine();
                opcion = -1;
            }
        } while (opcion != 0);
    }

    public void solicitarDatosRescate() {
        System.out.println("\n--- Ingreso de Datos del Animal Rescatado ---");

        System.out.print("Especie (Perro, Gato, Otro): ");
        String especie = scanner.nextLine();

        System.out.print("Raza: ");
        String raza = scanner.nextLine();

        System.out.print("Sexo (Macho, Hembra, Desconocido): ");
        String sexo = scanner.nextLine();

        System.out.print("Estado de Salud (Saludable, Herido, Enfermo, Buscando Hogar, etc.): ");
        String estadoSalud = scanner.nextLine();

        System.out.print("Lugar del Encuentro: ");
        String lugarEncontrado = scanner.nextLine();

        LocalDateTime fechaHoraRescate = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            System.out.print("Fecha y Hora del Encuentro (YYYY-MM-DD HH:MM): ");
            String fechaHoraStr = scanner.nextLine();
            try {
                fechaHoraRescate = LocalDateTime.parse(fechaHoraStr.replace(" ", "T")); // Formato ISO 8601
                fechaValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha y hora inválido. Use YYYY-MM-DD HH:MM.");
            }
        }

        int edad = -1;
        boolean edadValida = false;
        while (!edadValida) {
            System.out.print("Edad aproximada (años): ");
            try {
                edad = scanner.nextInt();
                if (edad >= 0) {
                    edadValida = true;
                } else {
                    System.out.println("La edad no puede ser negativa.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero para la edad.");
                scanner.nextLine(); // Limpiar el buffer
            } finally {
                scanner.nextLine(); // Asegurar consumo de línea después de nextInt
            }
        }

        animalControlador.registrarAnimal(especie, raza, sexo, estadoSalud, lugarEncontrado, fechaHoraRescate, edad); //puse la fc de Animal
    }

    private void registrarNuevoRescatista() {
        System.out.println("\n--- Registro de Nuevo Rescatista ---");
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();

        LocalDate fechaNacimiento = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            System.out.print("Fecha de Nacimiento (YYYY-MM-DD): ");
            String fechaNacStr = scanner.nextLine();
            try {
                fechaNacimiento = LocalDate.parse(fechaNacStr);
                fechaValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use YYYY-MM-DD.");
            }
        }

        System.out.print("Dirección completa: ");
        String direccion = scanner.nextLine();

        System.out.print("Número de Teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Correo Electrónico: ");
        String email = scanner.nextLine();

        rescatistaControlador.registrarRescatista(nombre, fechaNacimiento, direccion, telefono, email);
    }

}
