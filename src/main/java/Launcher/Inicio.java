package Launcher;

import Controlador.AdoptanteControlador;
import Controlador.AnimalControlador; //
import Controlador.RescatistaControlador;
import Controlador.VeterinarioControlador;
import Controlador.AdminControlador;
import Datos.AnimalDA;
import Datos.PersonaDA;
import Vista.VisualizacionAdoptanteCLI;
import Vista.RescateCLI;
import Vista.GestionVeterinarioCLI;
import Vista.AdminCLI;
import Vista.MenuPrincipalCLI; //

public class Inicio {

    public static void main(String[] args) {
        PersonaDA personaDA = new PersonaDA();
        AnimalDA animalDA = new AnimalDA();


        AdoptanteControlador adoptanteControlador = new AdoptanteControlador(personaDA, animalDA);
        RescatistaControlador rescatistaControlador = new RescatistaControlador(personaDA);
        VeterinarioControlador veterinarioControlador = new VeterinarioControlador(personaDA, animalDA);
        AdminControlador adminControlador = new AdminControlador(personaDA);

        VisualizacionAdoptanteCLI adoptanteCLI = new VisualizacionAdoptanteCLI(adoptanteControlador);
        RescateCLI rescateCLI = new RescateCLI(rescatistaControlador, animalControlador);
        GestionVeterinarioCLI veterinarioCLI = new GestionVeterinarioCLI(veterinarioControlador);
        AdminCLI adminCLI = new AdminCLI(adminControlador);

        MenuPrincipalCLI menuPrincipalCLI = new MenuPrincipalCLI(
                adoptanteCLI,
                rescateCLI,
                veterinarioCLI,
                adminCLI
        );

        // Iniciar mostrando el menú principal
        menuPrincipalCLI.mostrarMenuPrincipal();
    }
}