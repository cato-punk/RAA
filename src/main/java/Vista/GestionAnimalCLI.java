package Vista;

import Controlador.AnimalControlador;
import Modelo.Animal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class GestionAnimalCLI {

    private Scanner sc;
    private AnimalControlador animalControlador;

    public GestionAnimalCLI(AnimalControlador animalControlador) {
        this.sc = new Scanner(System.in);
        this.animalControlador = animalControlador;
    }

    public void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n--- MENÚ GESTIÓN DE ANIMALES ---");
            System.out.println("1. Ver todos los animales");
            System.out.println("2. Buscar animal por ID");
            System.out.println("3. Actualizar datos de un animal");
            System.out.println("4. Eliminar animal");
            System.out.println("5. Volver al menú principal");
            System.out.print("Ingrese su opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    mostrarTodosLosAnimales();
                    break;
                case "2":
                    buscarAnimalPorId();
                    break;
                case "3":
                    actualizarDatosAnimal();
                    break;
                case "4":
                    eliminarAnimal();
                    break;
                case "5":
                    System.out.println("Volviendo al menú principal.");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }

    private void mostrarTodosLosAnimales() {
        System.out.println("\n--- Listado de Todos los Animales ---");
        List<Animal> animales = animalControlador.obtenerTodosLosAnimales();
        if (animales.isEmpty()) {
            System.out.println("No hay animales registrados en el sistema.");
        } else {
            animales.forEach(animal -> System.out.println(animal.toString()));
        }
    }

    private void buscarAnimalPorId() {
        System.out.println("\n--- Buscar Animal por ID ---");
        System.out.print("Ingrese el ID del animal a buscar: ");
        String id = sc.nextLine();
        Animal animal = animalControlador.buscarAnimalPorId(id);
        if (animal != null) {
            System.out.println("Animal encontrado: " + animal.toString());
        } else {
            System.out.println("Animal no encontrado con el ID: " + id);
        }
    }

    private void actualizarDatosAnimal() {
        System.out.println("\n--- Actualizar Datos de un Animal ---");
        System.out.print("Ingrese el ID del animal a actualizar: ");
        String id = sc.nextLine();

        Animal animalExistente = animalControlador.buscarAnimalPorId(id);
        if (animalExistente == null) {
            System.out.println("Animal no encontrado con el ID: " + id);
            return;
        }

        System.out.println("Deje en blanco los campos que no desea actualizar.");
        System.out.println("Datos actuales del animal:");
        System.out.println(animalExistente.toString());

        String especie = obtenerEntrada("Nueva Especie (" + animalExistente.getEspecie() + "): ", animalExistente.getEspecie());
        String raza = obtenerEntrada("Nueva Raza (" + animalExistente.getRaza() + "): ", animalExistente.getRaza());
        String sexo = obtenerEntrada("Nuevo Sexo (" + animalExistente.getSexo() + "): ", animalExistente.getSexo());
        String estadoSalud = obtenerEntrada("Nuevo Estado de Salud (" + animalExistente.getEstadoSalud() + "): ", animalExistente.getEstadoSalud());
        String lugarEncontrado = obtenerEntrada("Nuevo Lugar Encontrado (" + animalExistente.getLugarEncontrado() + "): ", animalExistente.getLugarEncontrado());

        LocalDateTime fechaHoraRescate = animalExistente.getFechaHoraRescate();
        System.out.print("Nueva Fecha y Hora de Rescate (AAAA-MM-DD HH:MM:SS) (" + (fechaHoraRescate != null ? fechaHoraRescate.toLocalDate() + " " + fechaHoraRescate.toLocalTime() : "N/A") + "): ");
        String fechaHoraRescateStr = sc.nextLine();
        if (!fechaHoraRescateStr.isEmpty()) {
            try {
                fechaHoraRescate = LocalDateTime.parse(fechaHoraRescateStr);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha/hora incorrecto. Usando la fecha/hora existente.");
            }
        }

        int edadAproximadaAnios = animalExistente.getEdadAproximadaAnios();
        System.out.print("Nueva Edad Aproximada en Años (" + animalExistente.getEdadAproximadaAnios() + "): ");
        String edadStr = sc.nextLine();
        if (!edadStr.isEmpty()) {
            try {
                edadAproximadaAnios = Integer.parseInt(edadStr);
            } catch (NumberFormatException e) {
                System.out.println("Entrada de edad inválida. Usando la edad existente.");
            }
        }

        String diagnostico = obtenerEntrada("Nuevo Diagnóstico (" + animalExistente.getDiagnostico() + "): ", animalExistente.getDiagnostico());
        String idRescatista = obtenerEntrada("ID del Rescatista (actual: " + animalExistente.getIdRescatista() + "): ", animalExistente.getIdRescatista());


        if (animalControlador.actualizarAnimal(id, especie, raza, sexo, estadoSalud, lugarEncontrado,
                fechaHoraRescate, edadAproximadaAnios, diagnostico, idRescatista)) {
            System.out.println("Animal actualizado exitosamente.");
        } else {
            System.out.println("Error al actualizar el animal.");
        }
    }

    private String obtenerEntrada(String prompt, String valorActual) {
        System.out.print(prompt);
        String entrada = sc.nextLine();
        return entrada.isEmpty() ? valorActual : entrada;
    }

    private void eliminarAnimal() {
        System.out.println("\n--- Eliminar Animal ---");
        System.out.print("Ingrese el ID del animal a eliminar: ");
        String id = sc.nextLine();
        if (animalControlador.eliminarAnimal(id)) {
            System.out.println("Animal eliminado exitosamente.");
        } else {
            System.out.println("Error al eliminar animal o ID no encontrado.");
        }
    }
}