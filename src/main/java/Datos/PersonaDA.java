package Datos;
import Modelo.Persona;
import Modelo.Rescatista;
import Modelo.Veterinario;
import Modelo.Adoptante;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.time.LocalDate;

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
            String tipo = jsonObject.getString("tipo"); //campo 'tipo' para saber qué subclase instanciar
            Persona persona = null;
            switch (tipo) {
                case "rescatista":
                    persona = Rescatista.fromJSONObject(jsonObject);
                    break;
                case "veterinario":
                    persona = Veterinario.fromJSONObject(jsonObject);
                    break;
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
        JSONArray personasJson = JsonUtil.leerArchivoJson(RUTA_ARCHIVO);
        for (int i = 0; i < personasJson.length(); i++) {
            JSONObject jsonObject = personasJson.getJSONObject(i);
            if (jsonObject.getString("id").equals(personaActualizada.getId())) {
                personasJson.put(i, personaActualizada.toJSONObject());
                JsonUtil.escribirArchivoJson(RUTA_ARCHIVO, personasJson);
                return;
            }
        }
        System.out.println("Persona con ID " + personaActualizada.getId() + " no encontrada para actualizar.");
    }

    public void eliminarPersona(String id) {
        JSONArray personasJson = JsonUtil.leerArchivoJson(RUTA_ARCHIVO);
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
            JsonUtil.escribirArchivoJson(RUTA_ARCHIVO, nuevasPersonasJson);
            System.out.println("Persona con ID " + id + " eliminada exitosamente.");
        } else {
            System.out.println("Persona con ID " + id + " no encontrada para eliminar.");
        }
    }
}