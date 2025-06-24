package Modelo;

import org.json.JSONObject;
import org.json.JSONException;
import java.time.LocalDate;
import java.util.UUID;

public class Rescatista extends Persona {
    // la contraseña hasheada esto es para mas adelante
    // pero no se usa activamente si no hay funcionalidad de login con contraseña
    private String contrasenaHash;

    // constructor para un nuevo Rescatista sin contraseña al registrar
    public Rescatista(String nombre, String rut, LocalDate fechaNacimiento,
                      String direccion, String numeroTelefono, String correoElectronico) {
        super(nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        this.contrasenaHash = null; // No  hash al crear, ya que no hay contraseña por ahora
    }

    // constructor para cargar un Rescatista existente
    // donde la contraseña hasheada puede ocuparse
    public Rescatista(String id, String nombre, String rut, LocalDate fechaNacimiento,
                      String direccion, String numeroTelefono, String correoElectronico,
                      String contrasenaHash) {
        super(id, nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        this.contrasenaHash = contrasenaHash;
    }

    // Getters y Setters para contrasenaHash
    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            // campos Persona
            jsonObject.put("id", getId());
            jsonObject.put("nombre", getNombre());
            jsonObject.put("rut", getRut());
            jsonObject.put("fechaNacimiento", getFechaNacimiento().toString());
            jsonObject.put("direccion", getDireccion());
            jsonObject.put("numeroTelefono", getNumeroTelefono());
            jsonObject.put("correoElectronico", getCorreoElectronico());
            jsonObject.put("tipo", "rescatista"); //el tipo al cargar


            if (contrasenaHash != null) { //mas adelante
                jsonObject.put("contrasenaHash", contrasenaHash);
            }
            //usar BCrypt.hashpw
            // jsonObject.put("contrasenaHash", BCrypt.hashpw(unaContrasenaPlana, BCrypt.gensalt()));

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
            // Usamos optString para que si "contrasenaHash" no existe en el JSON, no lance un error sino que devuelva null o un valor por defecto.
            String contrasenaHash = jsonObject.optString("contrasenaHash", null);

            return new Rescatista(id, nombre, rut, fechaNacimiento, direccion,
                    numeroTelefono, correoElectronico, contrasenaHash);

        } catch (JSONException | java.time.format.DateTimeParseException e) {
            System.err.println("Error al parsear JSONObject a Rescatista: " + e.getMessage());
            return null;
        }
    }
}