package Datos;

import Modelo.Persona;
import Modelo.Adoptante;
import Modelo.Rescatista;
import Modelo.Veterinario;
import Modelo.Admin;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

public class PersonaDA {

    private static final String FILE_PATH = "personas.json";

    public PersonaDA() {
        //el archivo existe al inicializar
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
                try (FileWriter file = new FileWriter(FILE_PATH)) {
                    file.write(new JSONArray().toString(4)); //  array json vacío con indentación
                }
            } catch (IOException e) {
                System.err.println("Error al crear el archivo personas.json: " + e.getMessage());
            }
        }
    }

    public void guardarPersona(Persona persona) {
        ArrayList<Persona> personas = cargarPersonas(); // Carga las personas existentes
        personas.add(persona); // Añadir la nueva persona
        guardarTodasLasPersonas(personas); // Guardartodo de nuevo
    }

    public boolean actualizarPersona(Persona personaActualizada) {
        ArrayList<Persona> personas = cargarPersonas();
        boolean encontrada = false;
        for (int i = 0; i < personas.size(); i++) {
            if (personas.get(i).getId().equals(personaActualizada.getId())) {
                personas.set(i, personaActualizada);
                encontrada = true;
                break;
            }
        }
        if (encontrada) {
            guardarTodasLasPersonas(personas);
            return true; // La persona fue encontrada y actualizada exitosamente.
        } else {
            System.err.println("Persona con ID " + personaActualizada.getId() + " no encontrada para actualizar.");
            return false; // Persona no fue encontrada.
        }
    }

    public boolean eliminarPersona(String id) {
        ArrayList<Persona> personas = cargarPersonas();
        boolean eliminada = personas.removeIf(p -> p.getId().equals(id));
        if (eliminada) {
            guardarTodasLasPersonas(personas);
            return true;
        }
        return false;
    }

    //para guardar la lista completa de personas
    private void guardarTodasLasPersonas(ArrayList<Persona> personas) {
        JSONArray jsonArray = new JSONArray();
        for (Persona p : personas) {
            jsonArray.put(p.toJSONObject());
        }
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(jsonArray.toString(4)); // Con indentación para legibilidad
        } catch (IOException e) {
            System.err.println("Error al guardar personas en " + FILE_PATH + ": " + e.getMessage());
        }
    }

    public ArrayList<Persona> cargarPersonas() {
        ArrayList<Persona> personas = new ArrayList<>();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            if (reader.ready()) { // Verifica si hay algo para leer
                JSONTokener tokener = new JSONTokener(reader);

                if (tokener.nextClean() != '[') {
                    System.err.println("Advertencia: El archivo personas.json no es un JSONArray válido o está vacío. Se inicializará.");

                    try (FileWriter file = new FileWriter(FILE_PATH)) {
                        file.write(new JSONArray().toString(4));
                    } catch (IOException ioException) {
                        System.err.println("Error al resetear el archivo JSON: " + ioException.getMessage());
                    }
                    return personas; // Devolver lista vacía
                }

                String content = Files.readString(Paths.get(FILE_PATH));
                if (content.trim().isEmpty()) {
                    // Si está vacío después de limpiar, inicializar
                    try (FileWriter file = new FileWriter(FILE_PATH)) {
                        file.write(new JSONArray().toString(4));
                    } catch (IOException ioException) {
                        System.err.println("Error al resetear el archivo JSON vacío: " + ioException.getMessage());
                    }
                    return personas;
                }
                JSONArray jsonArray = new JSONArray(content); // Parsear el String completo

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String tipo = jsonObject.optString("tipo", "persona"); // Leer el tipo

                    Persona persona = null;
                    switch (tipo) {
                        case "adoptante":
                            persona = Adoptante.fromJSONObject(jsonObject);
                            break;
                        case "rescatista":
                            persona = Rescatista.fromJSONObject(jsonObject);
                            break;
                        case "veterinario":
                            persona = Veterinario.fromJSONObject(jsonObject);
                            break;
                        case "admin":
                            persona = Admin.fromJSONObject(jsonObject);
                            break;
                        default:
                            System.err.println("Tipo de persona desconocido o faltante en JSON: " + tipo + " para ID: " + jsonObject.optString("id", "N/A"));
                            break;
                    }
                    if (persona != null) {
                        personas.add(persona);
                    }
                }
            } else {
                //  (no tiene contenido)
                System.out.println("El archivo personas.json está vacío. Se inicializará como un array JSON vacío.");
                try (FileWriter file = new FileWriter(FILE_PATH)) {
                    file.write(new JSONArray().toString(4));
                } catch (IOException ioException) {
                    System.err.println("Error al resetear el archivo JSON vacío: " + ioException.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error de I/O al cargar personas desde " + FILE_PATH + ": " + e.getMessage());
        } catch (JSONException e) {
            System.err.println("Error JSON al cargar personas desde " + FILE_PATH + ": " + e.getMessage());
            // Intenta resetear el archivo si está mal formado
            try (FileWriter file = new FileWriter(FILE_PATH)) {
                file.write(new JSONArray().toString(4));
            } catch (IOException ioException) {
                System.err.println("Error al resetear el archivo JSON mal formado: " + ioException.getMessage());
            }
        }
        return personas;
    }

    public Optional<Persona> buscarPersonaPorCorreo(String correo) {
        return cargarPersonas().stream()
                .filter(p -> p.getCorreoElectronico() != null && p.getCorreoElectronico().equalsIgnoreCase(correo))
                .findFirst();
    }


    public Optional<Persona> buscarPersonaPorId(String id) {
        return cargarPersonas().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst(); // findFirst() ya devuelve Optional
    }
}