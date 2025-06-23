package Modelo;

import java.time.LocalDate;
import org.json.JSONObject;
import org.json.JSONException;

public class Rescatista extends Persona {

    // constructor principal, ahora con rut
    public Rescatista(String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                      String numeroTelefono, String correoElectronico) {
        super(nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
    }

    // constructor para cargar desde JSON, ahora con rut
    public Rescatista(String id, String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                      String numeroTelefono, String correoElectronico) {
        super(id, nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
    }

    @Override
    public String toString() {
        return "Rescatista - ID: " + getId() + ", Nombre: " + getNombre() + ", RUT: " + getRut() +
                ", Correo: " + getCorreoElectronico();
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("nombre", getNombre());
            jsonObject.put("rut", getRut()); // añadir RUT al JSON
            jsonObject.put("fechaNacimiento", getFechaNacimiento().toString());
            // jsonObject.put("sexo", getSexo()); // Eliminado
            jsonObject.put("direccion", getDireccion());
            jsonObject.put("numeroTelefono", getNumeroTelefono());
            jsonObject.put("correoElectronico", getCorreoElectronico());
            jsonObject.put("tipo", "rescatista"); // para diferenciar al cargar
        } catch (JSONException e) {
            System.err.println("Error al crear JSONObject de Rescatista: " + e.getMessage());
        }
        return jsonObject;
    }

    public static Rescatista fromJSONObject(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("id");
            String nombre = jsonObject.getString("nombre");
            String rut = jsonObject.getString("rut"); // obtener RUT del JSON
            LocalDate fechaNacimiento = LocalDate.parse(jsonObject.getString("fechaNacimiento"));
            // String sexo = jsonObject.getString("sexo"); // Eliminado
            String direccion = jsonObject.getString("direccion");
            String numeroTelefono = jsonObject.getString("numeroTelefono");
            String correoElectronico = jsonObject.getString("correoElectronico");
            return new Rescatista(id, nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        } catch (JSONException | java.time.format.DateTimeParseException e) {
            System.err.println("Error al parsear JSONObject a Rescatista: " + e.getMessage());
            return null;
        }
    }
}