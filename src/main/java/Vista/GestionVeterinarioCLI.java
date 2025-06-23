package Vista;

import Controlador.VeterinarioControlador;
import Modelo.Animal; //mostrar detalles de animales
import Modelo.Veterinario;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class GestionVeterinarioCLI {

    private Scanner sc;
    private VeterinarioControlador veterinarioControlador;

    public GestionVeterinarioCLI(VeterinarioControlador veterinarioControlador) {
        this.sc = new Scanner(System.in);
        this.veterinarioControlador = veterinarioControlador;
    }

    public void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n--- MENÚ VETERINARIO ---");
            System.out.println("1. Registrarse como Veterinario");
            System.out.println("2. Iniciar sesión como Veterinario");
            System.out.println("3. Salir");
            System.out.print("Ingrese su opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    registrarNuevoVeterinario();
                    break;
                case "2":
                    if (iniciarSesionVeterinario()) {
                        mostrarMenuVeterinarioLogueado(); // Menú específico tras login
                    }
                    break;
                case "3":
                    System.out.println("Saliendo del menú de veterinarios.");
                    return;
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

        System.out.print("Especialidad (ej: Pequeños Animales, Equinos): ");
        String especialidad = sc.nextLine();

        System.out.print("Número de Licencia: ");
        String licencia = sc.nextLine();



        if (veterinarioControlador.registrarVeterinario(nombre, rut, fechaNacimiento, direccion,
                numeroTelefono, correoElectronico, especialidad, licencia)) {
            System.out.println("Veterinario registrado exitosamente.");
        } else {
            System.out.println("Error al registrar veterinario. Posiblemente el correo ya está en uso.");
        }
    }

    private boolean iniciarSesionVeterinario() {
        System.out.println("\n--- Iniciar Sesión Veterinario ---");
        System.out.print("Ingrese su correo electrónico: ");
        String correo = sc.nextLine();


        if (veterinarioControlador.iniciarSesion(correo)) {
            System.out.println("¡Bienvenido/a Veterinario/a " + veterinarioControlador.getVeterinarioActual().getNombre() + "!");
            return true;
        } else {
            System.out.println("Correo electrónico no encontrado o no corresponde a un Veterinario.");
            return false;
        }
    }

    private void mostrarMenuVeterinarioLogueado() {
        while (true) {
            System.out.println("\n--- MENÚ DE VETERINARIO LOGUEADO ---");
            System.out.println("1. Ver todos los animales");
            System.out.println("2. Buscar animal por ID");
            System.out.println("3. Actualizar estado de salud de un animal");
            System.out.println("4. Diagnosticar animal");
            System.out.println("5. Ver mis datos");
            System.out.println("6. Cerrar sesión");
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
                    actualizarEstadoSaludAnimal();
                    break;
                case "4":
                    diagnosticarAnimal();
                    break;
                case "5":
                    System.out.println("Funcionalidad 'Ver mis datos' en desarrollo.");
                    if (veterinarioControlador.getVeterinarioActual() != null) {
                        System.out.println(veterinarioControlador.getVeterinarioActual().toString());
                    }
                    break;
                case "6":
                    veterinarioControlador.cerrarSesion();
                    System.out.println("Sesión cerrada. Volviendo al menú principal de Veterinario.");
                    return; // Salir de este menú y volver al principal de Veterinario
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void mostrarTodosLosAnimales() {
        System.out.println("\n--- Listado de Todos los Animales ---");
        List<Animal> animales = veterinarioControlador.obtenerTodosLosAnimales();
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
        Animal animal = veterinarioControlador.buscarAnimalPorId(id);
        if (animal != null) {
            System.out.println("Animal encontrado: " + animal.toString());
        } else {
            System.out.println("Animal no encontrado.");
        }
    }

    private void actualizarEstadoSaludAnimal() {
        System.out.println("\n--- Actualizar Estado de Salud de un Animal ---");
        System.out.print("Ingrese el ID del animal: ");
        String idAnimal = sc.nextLine();
        System.out.print("Ingrese el nuevo estado de salud: ");
        String nuevoEstado = sc.nextLine();

        if (veterinarioControlador.actualizarEstadoSaludAnimal(idAnimal, nuevoEstado)) {
            System.out.println("Estado de salud actualizado exitosamente.");
        } else {
            System.out.println("Error al actualizar el estado de salud. Verifique el ID del animal.");
        }
    }

    private void diagnosticarAnimal() {
        System.out.println("\n--- Diagnosticar Animal ---");
        System.out.print("Ingrese el ID del animal: ");
        String idAnimal = sc.nextLine();
        System.out.print("Ingrese el diagnóstico: ");
        String diagnostico = sc.nextLine();

        if (veterinarioControlador.diagnosticarAnimal(idAnimal, diagnostico)) {
            System.out.println("Diagnóstico registrado exitosamente.");
        } else {
            System.out.println("Error al registrar el diagnóstico. Verifique el ID del animal.");
        }
    }

    // Opcional: un metdoo para que el Veterinario pueda actualizar sus propios datos desde el CLI
    /*
    private void actualizarDatosVeterinario() {
        System.out.println("\n--- Actualizar Mis Datos de Veterinario ---");
        Veterinario veterinarioExistente = veterinarioControlador.getVeterinarioActual();
        if (veterinarioExistente == null) {
            System.out.println("No hay un veterinario logueado.");
            return;
        }

        System.out.println("Deje en blanco los campos que no desea actualizar.");
        System.out.println("Datos actuales: " + veterinarioExistente.toString());

        String id = veterinarioExistente.getId(); // Usar el ID del veterinario logueado

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

        System.out.print("Nuevo Número de Licencia (" + veterinarioExistente.getLicencia() + "): ");
        String licencia = sc.nextLine();
        if (licencia.isEmpty()) licencia = veterinarioExistente.getLicencia();


        if (veterinarioControlador.actualizarVeterinario(id, nombre, rut, fechaNacimiento,
                direccion, numeroTelefono, correoElectronico,
                especialidad, licencia)) {
            System.out.println("Mis datos de veterinario actualizados exitosamente.");
        } else {
            System.out.println("Error al actualizar mis datos de veterinario.");
        }
    }
    */
}