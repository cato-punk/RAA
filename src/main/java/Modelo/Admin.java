package Modelo;

import java.time.LocalDate;
import org.json.JSONObject;
import org.json.JSONException;
import org.mindrot.jbcrypt.BCrypt; // importar BCrypt de lo que hizo min

public class Admin extends Persona {

    private String contrasenaHash; // Solo el Admin tendrá contraseña hasheada (por ahora)

    // constructor para crear un nuevo Admin
    public Admin(String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                 String numeroTelefono, String correoElectronico, String contrasenaPlana) {
        super(nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        //hashing de la contraseña para seguridad
        this.contrasenaHash = BCrypt.hashpw(contrasenaPlana, BCrypt.gensalt());
    }

    // constructor para cargar un Admin desde JSON (la contraseña ya viene hasheada)
    public Admin(String id, String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                 String numeroTelefono, String correoElectronico, String contrasenaHash) {
        super(id, nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        this.contrasenaHash = contrasenaHash;
    }

    // --- Getters y Setters para de Admin ---
    public String getContrasenaHash() {
        return contrasenaHash;
    }

    // Setter para la contraseña (util si un admin actualiza su propia contraseña)
    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    @Override
    public String toString() {
        return "Admin - ID: " + getId() + ", Nombre: " + getNombre() + ", RUT: " + getRut() +
                ", Correo: " + getCorreoElectronico();
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject = super.toJSONObject();
        try {
            jsonObject.put("tipo", "admin"); // para identificar el tipo al cargar
            jsonObject.put("contrasenaHash", contrasenaHash); // guardar la contraseña hasheada
        } catch (JSONException e) {
            System.err.println("Error al crear JSONObject de Admin: " + e.getMessage());
        }
        return jsonObject;
    }

    public static Admin fromJSONObject(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("id");
            String nombre = jsonObject.getString("nombre");
            String rut = jsonObject.getString("rut");
            LocalDate fechaNacimiento = LocalDate.parse(jsonObject.getString("fechaNacimiento"));
            String direccion = jsonObject.getString("direccion");
            String numeroTelefono = jsonObject.getString("numeroTelefono");
            String correoElectronico = jsonObject.getString("correoElectronico");
            String contrasenaHash = jsonObject.getString("contrasenaHash");

            return new Admin(id, nombre, rut, fechaNacimiento, direccion,
                    numeroTelefono, correoElectronico, contrasenaHash);
        } catch (JSONException | java.time.format.DateTimeParseException e) {
            System.err.println("Error al parsear JSONObject a Admin: " + e.getMessage());
            return null;
        }
    }
}