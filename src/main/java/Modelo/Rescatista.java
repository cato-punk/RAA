package Modelo;

import java.time.LocalDate;
import org.json.JSONObject;
import org.json.JSONException;
import org.mindrot.jbcrypt.BCrypt; // importamos BCrypt con las actualizaciones de min

public class Rescatista extends Persona {

    private String contrasenaHash; // NUEVOOO Para almacenar la contraseña hasheada

    // constructor principal para crear un nuevo rescatista, ahora con contraseña plana
    public Rescatista(String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                      String numeroTelefono, String correoElectronico, String contrasenaPlana) { // ¡NUEVO! Contraseña plana
        super(nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        this.contrasenaHash = BCrypt.hashpw(contrasenaPlana, BCrypt.gensalt());
    }

    // constructor para cargar desde JSON (incluyendo ID y contraseña hasheada)
    public Rescatista(String id, String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                      String numeroTelefono, String correoElectronico, String contrasenaHash) { // Contraseña ya hasheada
        super(id, nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        this.contrasenaHash = contrasenaHash; // Asignar la contraseña hasheada
    }


    // no setter público por seguridad)
    public String getContrasenaHash() {
        return contrasenaHash;
    }

    @Override
    public String toString() {
        return "Rescatista - ID: " + getId() + ", Nombre: " + getNombre() + ", RUT: " + getRut() +
                ", Correo: " + getCorreoElectronico();
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject = super.toJSONObject();
        try {
            jsonObject.put("tipo", "rescatista"); // Para diferenciar al cargar
            jsonObject.put("contrasenaHash", contrasenaHash); // ñadir contraseña hasheada al JSON
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
            String contrasenaHash = jsonObject.getString("contrasenaHash"); // obtener contraseña hasheada

            // usar el constructor con ID para cargar correctamente
            return new Rescatista(id, nombre, rut, fechaNacimiento, direccion,
                    numeroTelefono, correoElectronico, contrasenaHash);
        } catch (JSONException | java.time.format.DateTimeParseException e) {
            System.err.println("Error al parsear JSONObject a Rescatista: " + e.getMessage());
            return null;
        }
    }
}
