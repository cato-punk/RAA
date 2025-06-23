package Datos;

import Modelo.Adoptante;
import Modelo.Persona;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class PersonaDA {
    private static final String NOMBRE_ARCHIVO = "Usuarios.json";
    private final String RUTA_ARCHIVO = "src/main/resources/Usuarios.json";

    // Método para leer el archivo JSON
    private JSONArray leerArchivo() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(NOMBRE_ARCHIVO)) {
            if (is == null) {
                return new JSONArray(); // Retorna array vacío si el archivo no existe
            }
            String contenido = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return new JSONArray(contenido);
        } catch (Exception e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return new JSONArray();
        }
    }

    // Método para guardar cambios en el archivo
    private void guardarArchivo(JSONArray jsonArray) {
        try {
            String ruta = getClass().getClassLoader().getResource(NOMBRE_ARCHIVO).getPath();
            // Corrección para Windows
            if (ruta.startsWith("/") && ruta.contains(":")) {
                ruta = ruta.substring(1);
            }
            java.nio.file.Files.write(
                    java.nio.file.Paths.get(ruta),
                    jsonArray.toString().getBytes(StandardCharsets.UTF_8)
            );
        } catch (Exception e) {
            System.err.println("Error al guardar archivo: " + e.getMessage());
        }
    }

    // Ejemplo de cómo adaptar tus métodos existentes:
    public void guardarPersona(Persona persona) {
        try {
            // 1. Cargar el archivo existente
            JSONArray personasJson = leerArchivo();

            // 2. Agregar la nueva persona
            personasJson.put(persona.toJSONObject());

            // 3. Guardar inmediatamente
            Files.write(
                    Paths.get(RUTA_ARCHIVO),
                    personasJson.toString(2).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

            System.out.println("Usuario guardado inmediatamente en: " + RUTA_ARCHIVO);
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }

    public ArrayList<Persona> cargarPersonas() {
        ArrayList<Persona> personas = new ArrayList<>();
        JSONArray personasJson = leerArchivo();

        for (int i = 0; i < personasJson.length(); i++) {
            JSONObject jsonObject = personasJson.getJSONObject(i);
            String tipo = jsonObject.getString("tipo");

            Persona persona = null;
            switch (tipo) {
                case "adoptante":
                    persona = Adoptante.fromJSONObject(jsonObject);
                    break;
            }

            if (persona != null) {
                personas.add(persona);
            }
        }
        return personas;
    }

    public void actualizarPersona(Persona personaActualizada) {
        JSONArray personasJson = leerArchivo();
        boolean encontrada = false;

        for (int i = 0; i < personasJson.length(); i++) {
            JSONObject jsonObject = personasJson.getJSONObject(i);
            if (jsonObject.getString("id").equals(personaActualizada.getId())) {
                personasJson.put(i, personaActualizada.toJSONObject());
                encontrada = true;
                break;
            }
        }

        if (encontrada) {
            guardarArchivo(personasJson);
            System.out.println("Persona con ID " + personaActualizada.getId() + " actualizada exitosamente.");
        } else {
            System.out.println("Persona con ID " + personaActualizada.getId() + " no encontrada para actualizar.");
        }
    }
    public boolean existeCorreo(String correo) {
        return cargarPersonas().stream()
                .anyMatch(p -> p.getCorreoElectronico().equalsIgnoreCase(correo));
    }

    public void eliminarPersona(String id) {
        JSONArray personasJson = leerArchivo();
        JSONArray nuevasPersonasJson = new JSONArray();
        boolean encontrado = false;

        for (int i = 0; i < personasJson.length(); i++) {
            JSONObject jsonObject = personasJson.getJSONObject(i);
            if (!jsonObject.getString("id").equals(id)) {
                nuevasPersonasJson.put(jsonObject);
            } else {
                encontrado = true;
            }
        }

        if (encontrado) {
            guardarArchivo(nuevasPersonasJson);
            System.out.println("Persona con ID " + id + " eliminada exitosamente.");
        } else {
            System.out.println("Persona con ID " + id + " no encontrada para eliminar.");
        }
    }
}

