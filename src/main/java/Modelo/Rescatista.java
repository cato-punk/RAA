package Modelo;

import java.time.LocalDate;
import org.json.JSONObject;
import org.json.JSONException;

public class Rescatista extends Persona {

    // private String contrasenaHash;

    // constructor principal para crear un nuevo rescatista
    public Rescatista(String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                      String numeroTelefono, String correoElectronico) {
        super(nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        // this.contrasenaHash = BCrypt.hashpw(contrasenaPlana, BCrypt.gensalt());
    }

    // Constructor para cargar desde JSON (sin contraseña hasheada)
    public Rescatista(String id, String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                      String numeroTelefono, String correoElectronico) {
        super(id, nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        // this.contrasenaHash = contrasenaHash; //
    }


    // public String getContrasenaHash() { return contrasenaHash; } //
    // public void setContrasenaHash(String contrasenaHash) { this.contrasenaHash = contrasenaHash; }

    @Override
    public String toString() {
        return "Rescatista - ID: " + getId() + ", Nombre: " + getNombre() + ", RUT: " + getRut() +
                ", Correo: " + getCorreoElectronico();
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject = super.toJSONObject();
        try {
            jsonObject.put("tipo", "rescatista");
            // jsonObject.put("contrasenaHash", contrasenaHash);
        } catch (JSONException e) {
            System.err.println("Error al crear JSONObject de Rescatista: " + e.getMessage());
        }
        return jsonObject;
    }

    public static Rescatista fromJSONObject(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("id");
            String nombre = jsonObject.getString("nombre");
            String rut = jsonObject.getString("rut");
            LocalDate fechaNacimiento = LocalDate.parse(jsonObject.getString("fechaNacimiento"));
            String direccion = jsonObject.getString("direccion");
            String numeroTelefono = jsonObject.getString("numeroTelefono");
            String correoElectronico = jsonObject.getString("correoElectronico");
            // String contrasenaHash = jsonObject.optString("contrasenaHash", "");

            // Usar el constructor sin el parámetro de contraseña
            return new Rescatista(id, nombre, rut, fechaNacimiento, direccion,
                    numeroTelefono, correoElectronico);
        } catch (JSONException | java.time.format.DateTimeParseException e) {
            System.err.println("Error al parsear JSONObject a Rescatista: " + e.getMessage());
            return null;
        }
    }
}