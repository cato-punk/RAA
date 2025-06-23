package Launcher;

import Controlador.AnimalControlador;
import Controlador.AdoptanteControlador;
import Controlador.RescatistaControlador;
import Controlador.VeterinarioControlador;
import Datos.AnimalDA;
import Datos.PersonaDA;
import Vista.MenuPrincipalCLI;
import Vista.VisualizacionAdoptanteCLI;

// Para generar IDs únicos
import java.util.UUID;

public class Inicio {
    public static void main(String[] args) {
        // Inicializar DAOs (Data Access Objects)
        AnimalDA animalDA = new AnimalDA();
        PersonaDA personaDA = new PersonaDA();



        // Inicializar Controladores, inyectando los DAs  , tengo que investigar la inyeccion

        AdoptanteControlador adoptanteControlador = new AdoptanteControlador(personaDA, animalDA); // Adoptante interactúa con animales y se registra


        VisualizacionAdoptanteCLI adoptanteCLI = new VisualizacionAdoptanteCLI(adoptanteControlador);
        adoptanteCLI.mostrarMenuPrincipal();
    }


}
