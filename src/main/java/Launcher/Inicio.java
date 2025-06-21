package Launcher;

import Controlador.AnimalControlador;
import Controlador.AdoptanteControlador;
import Controlador.RescatistaControlador;
import Controlador.VeterinarioControlador;
import Datos.AnimalDA;
import Datos.PersonaDA;
import Vista.MenuPrincipalCLI;

// Para generar IDs únicos
import java.util.UUID;

public class Inicio {
    public static void main(String[] args) {
        // Inicializar DAOs (Data Access Objects)
        AnimalDA animalDA = new AnimalDA();
        PersonaDA personaDAO = new PersonaDA();

        // Inicializar Controladores, inyectando los DAs  , tengo que investigar la inyeccion
        AnimalControlador animalControlador = new AnimalControlador(animalDA);
        RescatistaControlador rescatistaControlador = new RescatistaControlador(personaDA); //aca estan en rojito porque no he ehcho ningun avarable de DA
        VeterinarioControlador veterinarioControlador = new VeterinarioControlador(personaDA);
        AdoptanteControlador adoptanteControlador = new AdoptanteControlador(animalDA); // Adoptante interactúa con animales

        // Inicializar la Vista CLI
        MenuPrincipalCLI menuPrincipal = new MenuPrincipalCLI(
                animalControlador,  //estas partes estan rojito porque no he ehcho ningun constructor
                rescatistaControlador,
                veterinarioControlador,
                adoptanteControlador
        );

        // Iniciar la aplicación
        menuPrincipal.mostrarMenu(); //esta es la tipica pero no he hecho ningun menu
    }
}
