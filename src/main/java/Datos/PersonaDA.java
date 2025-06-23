package Datos;

import Modelo.Persona;
import Modelo.Rescatista;
import Modelo.Veterinario;
import Modelo.Adoptante;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.time.LocalDate;
import java.util.ArrayList;

public class PersonaDA {
    private final String RUTA_ARCHIVO = "usuarios.json";

    public void guardarPersona(Persona persona) {
        JSONArray personasJson = JsonUtil.leerArchivoJson(RUTA_ARCHIVO);
        personasJson.put(persona.toJSONObject());
        JsonUtil.escribirArchivoJson(RUTA_ARCHIVO, personasJson);
    }

    public ArrayList<Persona> cargarPersonas() {
        ArrayList<Persona> personas = new ArrayList<>();
        JSONArray personasJson = JsonUtil.leerArchivoJson(RUTA_ARCHIVO);
        for (int i = 0; i < personasJson.length(); i++) {
            JSONObject jsonObject = personasJson.getJSONObject(i);
            try {
                String tipo = jsonObject.getString("tipo"); //  el campo 'tipo' para saber qué subclase instanciar
                Persona persona = null;
                switch (tipo) {
                    case "rescatista":
                        persona = Rescatista.fromJSONObject(jsonObject);
                        break;
                    case "veterinario":
                        persona = Veterinario.fromJSONObject(jsonObject);
                        break;
                    case "adoptante":
                        persona = Adoptante.fromJSONObject(jsonObject); //tener fromJSONObject en Adoptante
                        break;
                    default:
                        System.err.println("Tipo de persona desconocido: " + tipo);
                        break;
                }
                if (persona != null) {
                    personas.add(persona);
                }
            } catch (JSONException e) {
                System.err.println("Error al parsear objeto JSON de persona: " + e.getMessage());
            }
        }
        return personas;
    }

    //  para buscar por ID para actualizar y eliminar
    public Persona buscarPersonaPorld(String id) {
        ArrayList<Persona> personas = cargarPersonas();
        for (Persona persona : personas) {
            if (persona.getId().equals(id)) {
                return persona;
            }
        }
        return null;
    }

    public void actualizarPersona(Persona personaActualizada) {
        JSONArray personasJson = JsonUtil.leerArchivoJson(RUTA_ARCHIVO);
        JSONArray nuevosJson = new JSONArray();
        boolean encontrado = false;
        for (int i = 0; i < personasJson.length(); i++) {
            JSONObject jsonObject = personasJson.getJSONObject(i);
            if (jsonObject.getString("id").equals(personaActualizada.getId())) {
                nuevosJson.put(personaActualizada.toJSONObject());
                encontrado = true;
            } else {
                nuevosJson.put(jsonObject);
            }
        }
        if (encontrado) {
            JsonUtil.escribirArchivoJson(RUTA_ARCHIVO, nuevosJson);
            System.out.println("Persona con ID " + personaActualizada.getId() + " actualizada exitosamente.");
        } else {
            System.out.println("Persona con ID " + personaActualizada.getId() + " no encontrada para actualizar.");
        }
    }

    public void eliminarPersona(String id) {
        JSONArray personasJson = JsonUtil.leerArchivoJson(RUTA_ARCHIVO);
        JSONArray nuevosJson = new JSONArray();
        boolean encontrado = false;
        for (int i = 0; i < personasJson.length(); i++) {
            JSONObject jsonObject = personasJson.getJSONObject(i);
            if (!jsonObject.getString("id").equals(id)) {
                nuevosJson.put(jsonObject);
            } else {
                encontrado = true;
            }
        }
        if (encontrado) {
            JsonUtil.escribirArchivoJson(RUTA_ARCHIVO, nuevosJson);
            System.out.println("Persona con ID " + id + " eliminada exitosamente.");
        } else {
            System.out.println("Persona con ID " + id + " no encontrada para eliminar.");
        }
    }
}