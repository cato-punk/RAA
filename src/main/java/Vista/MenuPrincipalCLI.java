package Vista;

import java.util.Scanner;

public class MenuPrincipalCLI {

    private Scanner sc;
    private VisualizacionAdoptanteCLI adoptanteCLI;
    private RescateCLI rescateCLI;
    private GestionVeterinarioCLI veterinarioCLI;
    private AdminCLI adminCLI;

    public MenuPrincipalCLI(VisualizacionAdoptanteCLI adoptanteCLI,
                            RescateCLI rescateCLI,
                            GestionVeterinarioCLI veterinarioCLI,
                            AdminCLI adminCLI) { // ahora sip
        this.sc = new Scanner(System.in);
        this.adoptanteCLI = adoptanteCLI;
        this.rescateCLI = rescateCLI;
        this.veterinarioCLI = veterinarioCLI;
        this.adminCLI = adminCLI; // Asignar el nuevo CLI de Admin
    }

    public void mostrarMenuPrincipal() {
        System.out.println("--- BIENVENIDO A RED AYUDA ANIMAL ---");

        while (true) {
            System.out.println("\nSeleccione su tipo de usuario:");
            System.out.println("1. Adoptante");
            System.out.println("2. Rescatista");
            System.out.println("3. Veterinario");
            System.out.println("4. Administrador");
            System.out.println("5. Salir");
            System.out.print("Ingrese su opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    adoptanteCLI.mostrarMenuPrincipal();
                    break;
                case "2":
                    rescateCLI.mostrarMenuPrincipal();
                    break;
                case "3":
                    veterinarioCLI.mostrarMenuPrincipal();
                    break;
                case "4": // Opn para Administrador
                    adminCLI.mostrarMenuPrincipal();
                    break;
                case "5":
                    System.out.println("Gracias por usar Red Ayuda Animal. ¡Hasta pronto!");
                    sc.close();
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }
}