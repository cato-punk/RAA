package Vista;

import Controlador.AdoptanteControlador;
import Modelo.Animal;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class VisualizacionAdoptanteCLI {
    private Scanner scanner;
    private AdoptanteControlador adoptanteControlador;

    public VisualizacionAdoptanteCLI(AdoptanteControlador adoptanteControlador) {
        this.scanner = new Scanner(System.in);
        this.adoptanteControlador = adoptanteControlador;
    }

    public void mostrarMenuPrincipal() {
        while (true) {
            mostrarOpcionesMenuPrincipal();
            String opcion = obtenerOpcionUsuario();
            ejecutarOpcionMenuPrincipal(opcion);
        }
    }

    private void mostrarOpcionesMenuPrincipal() {
        System.out.println("\n=== MENÚ PRINCIPAL ADOPTANTE ===");
        System.out.println("1. Registrarse");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private String obtenerOpcionUsuario() {
        return scanner.nextLine();
    }

    private void ejecutarOpcionMenuPrincipal(String opcion) {
        switch (opcion) {
            case "1":
                registrarAdoptante();
                break;
            case "2":
                if (iniciarSesion()) {
                    mostrarMenuUsuario();
                }
                break;
            case "3":
                System.exit(0); // o puedes lanzar una excepción controlada
                break;
            default:
                System.out.println("Opción no válida. Intente nuevamente.");
        }
    }
    private void registrarAdoptante() {
        System.out.println("\n=== REGISTRO DE ADOPTANTE ===");

        // Captura de datos
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();

        LocalDate fechaNacimiento = null;
        while (fechaNacimiento == null) {
            System.out.print("Fecha de nacimiento (AAAA-MM-DD): ");
            try {
                fechaNacimiento = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto. Use AAAA-MM-DD.");
            }
        }

        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        System.out.print("Número de teléfono: ");
        String telefono = scanner.nextLine();

        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();

        System.out.print("Preferencias de animal (ej: perros, gatos, aves): ");
        String preferencias = scanner.nextLine();

        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine(); // Contraseña en texto plano

        try {
            boolean registroExitoso = adoptanteControlador.registrarAdoptante(
                    nombre, fechaNacimiento, direccion,
                    telefono, correo, preferencias, contrasena
            );

            if (registroExitoso) {
                System.out.println("¡Registro exitoso! Redirigiendo...");
                // Auto-login
                if (adoptanteControlador.iniciarSesion(correo, contrasena)) {
                    mostrarMenuUsuario(); // Entrar directamente al menú
                } else {
                    System.out.println("Error en auto-login. Intente iniciar sesión manualmente.");
                    mostrarMenuPrincipal();
                }
            }
        } catch (Exception e) {
            System.err.println("Error durante el registro: " + e.getMessage());
        }
    }

    private boolean iniciarSesion() {
        System.out.println("\n=== INICIO DE SESIÓN ===");
        System.out.print("Ingrese su correo electrónico: ");
        String correo = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();

        if (adoptanteControlador.iniciarSesion(correo, contrasena)) {
            System.out.println("¡Bienvenido " +
                    adoptanteControlador.getAdoptanteActual().getNombre() + "!");
            return true;
        } else {
            System.out.println("Credenciales incorrectas o usuario no registrado.");
            return false;
        }
    }

    private void mostrarMenuUsuario() {
        while (true) {
            mostrarOpcionesMenuUsuario();
            String opcion = obtenerOpcionUsuario();
            if (ejecutarOpcionMenuUsuario(opcion)) {
                break; // Salir del bucle si la opción fue "Cerrar sesión"
            }
        }
    }

    private void mostrarOpcionesMenuUsuario() {
        System.out.println("\n=== MENÚ DE ADOPTANTE ===");
        System.out.println("1. Ver animales disponibles");
        System.out.println("2. Filtrar animales");
        System.out.println("3. Ver mi historial de adopciones");
        System.out.println("4. Adoptar un animal");
        System.out.println("5. Cerrar sesión");
        System.out.print("Seleccione una opción: ");
    }

    private boolean ejecutarOpcionMenuUsuario(String opcion) {
        switch (opcion) {
            case "1":
                mostrarAnimalesDisponibles();
                return false;
            case "2":
                filtrarAnimales();
                return false;
            case "3":
                mostrarHistorialAdopciones();
                return false;
            case "4":
                adoptarAnimal();
                return false;
            case "5":
                adoptanteControlador.cerrarSesion();
                return true;
            default:
                System.out.println("Opción no válida. Intente nuevamente.");
                return false;
        }
    }
    private void mostrarAnimalesDisponibles() {
        System.out.println("\n=== ANIMALES DISPONIBLES PARA ADOPCIÓN ===");
        List<Animal> animales = adoptanteControlador.listarAnimalesDisponibles();

        if (animales.isEmpty()) {
            System.out.println("No hay animales disponibles en este momento.");
        } else {
            animales.forEach(animal -> {
                System.out.println("\nID: " + animal.getId());
                System.out.println("Especie: " + animal.getEspecie());
                System.out.println("Raza: " + animal.getRaza());
                System.out.println("Sexo: " + animal.getSexo());
                System.out.println("Edad: " + animal.getEdad() + " años");
                System.out.println("Estado de salud: " + animal.getEstadoSalud());
                System.out.println("Lugar encontrado: " + animal.getLugarEncontrado());
                System.out.println("Fecha rescate: " + animal.getFechaHoraRescate().toLocalDate());
            });
        }
    }

    private void filtrarAnimales() {
        System.out.println("\n=== FILTRAR ANIMALES ===");
        System.out.println("Criterios disponibles: especie, raza, sexo, edad, lugar");
        System.out.print("Ingrese el criterio de filtro: ");
        String criterio = scanner.nextLine();

        System.out.print("Ingrese el valor a buscar: ");
        String valor = scanner.nextLine();

        List<Animal> animalesFiltrados = adoptanteControlador.filtrarAnimales(criterio, valor);

        if (animalesFiltrados.isEmpty()) {
            System.out.println("No se encontraron animales con esos criterios.");
        } else {
            System.out.println("\n=== RESULTADOS DEL FILTRO ===");
            animalesFiltrados.forEach(animal -> {
                System.out.println("\nID: " + animal.getId());
                System.out.println("Especie: " + animal.getEspecie());
                System.out.println("Raza: " + animal.getRaza());
                System.out.println("Sexo: " + animal.getSexo());
                System.out.println("Edad: " + animal.getEdad() + " años");
            });
        }
    }

    private void mostrarHistorialAdopciones() {
        System.out.println("\n=== TU HISTORIAL DE ADOPCIONES ===");
        List<Animal> adopciones = adoptanteControlador.obtenerHistorialAdopciones();

        if (adopciones.isEmpty()) {
            System.out.println("Aún no has adoptado ningún animal.");
        } else {
            adopciones.forEach(animal -> {
                System.out.println("\nID: " + animal.getId());
                System.out.println("Especie: " + animal.getEspecie());
                System.out.println("Raza: " + animal.getRaza());
                System.out.println("Fecha de adopción: " +
                        animal.getFechaHoraRescate().plusDays(30).toLocalDate()); // Ejemplo
            });
        }
    }

    private void adoptarAnimal() {
        System.out.println("\n=== ADOPTAR UN ANIMAL ===");
        mostrarAnimalesDisponibles();

        System.out.print("\nIngrese el ID del animal que desea adoptar: ");
        String idAnimal = scanner.nextLine();

        if (adoptanteControlador.registrarAdopcion(idAnimal)) {
            System.out.println("¡Felicidades! Has adoptado un nuevo compañero.");
        } else {
            System.out.println("No se pudo completar la adopción. Verifique el ID.");
        }
    }
}