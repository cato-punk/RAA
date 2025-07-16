package Controlador;

import java.util.UUID;

public class IdGenerator {
    //enera un ID único aleatorio.
     // return Un String que representa un ID único.

    public static String generateUniqueId() {
        // Simple UUID para generar IDs únicos
        return UUID.randomUUID().toString();
    }
}
