package Modelo;

import java.time.LocalDate;
import org.json.JSONObject;
import org.json.JSONException;

public class Adoptante extends Persona {

    private String preferencias;
    private String informacionAdopcion;


    // constructor principal para crear un nuevo adoptante (sin contraseña)
    public Adoptante(String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                     String numeroTelefono, String correoElectronico, String preferencias,
                     String informacionAdopcion) { // no paramtero contraseña
        super(nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        this.preferencias = preferencias;
        this.informacionAdopcion = informacionAdopcion;
        // this.contrasenaHash = BCrypt.hashpw(contrasenaPlana, BCrypt.gensalt()); // se va por ahora
    }

    // constructor para cargar desde JSON (sin contraseña hasheada)
    public Adoptante(String id, String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                     String numeroTelefono, String correoElectronico, String preferencias,
                     String informacionAdopcion) {
        super(id, nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        this.preferencias = preferencias;
        this.informacionAdopcion = informacionAdopcion;
        // this.contrasenaHash = contrasenaHash; //se va
    }

    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }

    public String getInformacionAdopcion() {
        return informacionAdopcion;
    }

    public void setInformacionAdopcion(String informacionAdopcion) {
        this.informacionAdopcion = informacionAdopcion;
    }

    // public String getContrasenaHash() { return contrasenaHash; }
    // public void setContrasenaHash(String contrasenaHash) { this.contrasenaHash = contrasenaHash; }


    @Override
    public String toString() {
        return "Adoptante - ID: " + getId() + ", Nombre: " + getNombre() + ", RUT: " + getRut() +
                ", Correo: " + getCorreoElectronico() + ", Preferencias: " + preferencias +
                ", Info Adopción: " + informacionAdopcion;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject = super.toJSONObject();
        try {
            jsonObject.put("tipo", "adoptante");
            jsonObject.put("preferencias", preferencias);
            jsonObject.put("informacionAdopcion", informacionAdopcion);
            // jsonObject.put("contrasenaHash", contrasenaHash);
        } catch (JSONException e) {
            System.err.println("Error al crear JSONObject de Adoptante: " + e.getMessage());
        }
        return jsonObject;
    }

    public static Adoptante fromJSONObject(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("id");
            String nombre = jsonObject.getString("nombre");
            String rut = jsonObject.getString("rut");
            LocalDate fechaNacimiento = LocalDate.parse(jsonObject.getString("fechaNacimiento"));
            String direccion = jsonObject.getString("direccion");
            String numeroTelefono = jsonObject.getString("numeroTelefono");
            String correoElectronico = jsonObject.getString("correoElectronico");
            String preferencias = jsonObject.getString("preferencias");
            String informacionAdopcion = jsonObject.getString("informacionAdopcion");
            // String contrasenaHash = jsonObject.optString("contrasenaHash", "");

            // Usar el constructor sin el parámetro de contraseña
            return new Adoptante(id, nombre, rut, fechaNacimiento, direccion,
                    numeroTelefono, correoElectronico, preferencias, informacionAdopcion);
        } catch (JSONException | java.time.format.DateTimeParseException e) {
            System.err.println("Error al parsear JSONObject a Adoptante: " + e.getMessage());
            return null;
        }
    }
}