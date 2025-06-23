package Datos;

import Modelo.Persona;
import Modelo.Adoptante;
import Modelo.Rescatista;
import Modelo.Veterinario;
import Modelo.Admin; // ¡NUEVO! Importar la clase Admin

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
import java.util.Optional; // Para buscar por correo

public class PersonaDA {

    private static final String FILE_PATH = "personas.json";

    public PersonaDA() {
        // asegurarse de que el archivo existe al inicializar
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
                // crear un JSONArray vacío para que siempre sea un JSON válido
                try (FileWriter file = new FileWriter(FILE_PATH)) {
                    file.write(new JSONArray().toString(4)); // escribe un array JSON vacío con indentar
                }
            } catch (IOException e) {
                System.err.println("Error al crear el archivo personas.json: " + e.getMessage());
            }
        }
    }

    public void guardarPersona(Persona persona) {
        ArrayList<Persona> personas = cargarPersonas(); //las personas existentes
        personas.add(persona); // aadir la nueva persona
        guardarTodasLasPersonas(personas); // guardartodo de nuevo
    }

    public void actualizarPersona(Persona personaActualizada) {
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
        } else {
            System.err.println("Persona con ID " + personaActualizada.getId() + " no encontrada para actualizar.");
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

    // para guardar la lista completa de personas
    private void guardarTodasLasPersonas(ArrayList<Persona> personas) {
        JSONArray jsonArray = new JSONArray();
        for (Persona p : personas) {
            jsonArray.put(p.toJSONObject());
        }
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(jsonArray.toString(4)); //con indentación para legibilidad
        } catch (IOException e) {
            System.err.println("Error al guardar personas en " + FILE_PATH + ": " + e.getMessage());
        }
    }

    public ArrayList<Persona> cargarPersonas() {
        ArrayList<Persona> personas = new ArrayList<>();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONArray jsonArray = new JSONArray(tokener);

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
                    case "admin": // ¡NUEVO! Manejar el tipo Admin
                        persona = Admin.fromJSONObject(jsonObject);
                        break;
                    default:
                        // Si no hay tipo especifico, o es un tipo desconocido, se podría manejar como Persona base
                        // Pero para este diseño, cada Persona debe tener un tipo específico para instanciar correctamente.
                        System.err.println("Tipo de persona desconocido o faltante en JSON: " + tipo);
                        break;
                }
                if (persona != null) {
                    personas.add(persona);
                }
            }
        } catch (IOException e) {
            System.err.println("Error de I/O al cargar personas desde " + FILE_PATH + ": " + e.getMessage());
        } catch (JSONException e) {
            // Esto puede ocurrir si el archivo JSON está mal formado o vacío
            System.err.println("Error JSON al cargar personas desde " + FILE_PATH + ": " + e.getMessage());
            // Si el error es por un archivo JSON vacío, inicializarlo correctamente
            try (FileWriter file = new FileWriter(FILE_PATH)) {
                file.write(new JSONArray().toString(4));
            } catch (IOException ioException) {
                System.err.println("Error al resetear el archivo JSON vacío: " + ioException.getMessage());
            }
        }
        return personas;
    }

    public Optional<Persona> buscarPersonaPorCorreo(String correo) {
        return cargarPersonas().stream()
                .filter(p -> p.getCorreoElectronico() != null && p.getCorreoElectronico().equalsIgnoreCase(correo))
                .findFirst();
    }

    public Persona buscarPersonaPorld(String id) {
        return cargarPersonas().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}