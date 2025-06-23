package Modelo;

import java.time.LocalDate;
import org.json.JSONObject;
import org.json.JSONException;

public class Veterinario extends Persona {

    private String especialidad;
    private String licencia;
    // private String contrasenaHash; //

    // Constructor principal
    public Veterinario(String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                       String numeroTelefono, String correoElectronico,
                       String especialidad, String licencia) { //
        super(nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        this.especialidad = especialidad;
        this.licencia = licencia;
        // this.contrasenaHash = BCrypt.hashpw(contrasenaPlana, BCrypt.gensalt());
    }

    //cargar desde JSON (sin contraseña hasheada)
    public Veterinario(String id, String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                       String numeroTelefono, String correoElectronico,
                       String especialidad, String licencia) { // ¡SIN PARÁMETRO DE CONTRASEÑA!
        super(id, nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        this.especialidad = especialidad;
        this.licencia = licencia;
        // this.contrasenaHash = contrasenaHash;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    // public String getContrasenaHash() { return contrasenaHash; }
    // public void setContrasenaHash(String contrasenaHash) { this.contrasenaHash = contrasenaHash; }

    @Override
    public String toString() {
        return "Veterinario - ID: " + getId() + ", Nombre: " + getNombre() + ", RUT: " + getRut() +
                ", Correo: " + getCorreoElectronico() + ", Especialidad: " + especialidad +
                ", Licencia: " + licencia;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject = super.toJSONObject(); //obtiene el JSONObject de Persona (que ya incluye RUT)
        try {
            jsonObject.put("tipo", "veterinario");
            jsonObject.put("especialidad", especialidad);
            jsonObject.put("licencia", licencia);
            // jsonObject.put("contrasenaHash", contrasenaHash);
        } catch (JSONException e) {
            System.err.println("Error al crear JSONObject de Veterinario: " + e.getMessage());
        }
        return jsonObject;
    }

    public static Veterinario fromJSONObject(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("id");
            String nombre = jsonObject.getString("nombre");
            String rut = jsonObject.getString("rut");
            LocalDate fechaNacimiento = LocalDate.parse(jsonObject.getString("fechaNacimiento"));
            String direccion = jsonObject.getString("direccion");
            String numeroTelefono = jsonObject.getString("numeroTelefono");
            String correoElectronico = jsonObject.getString("correoElectronico");
            String especialidad = jsonObject.getString("especialidad");
            String licencia = jsonObject.getString("licencia");
            // String contrasenaHash = jsonObject.optString("contrasenaHash", "");

            //constructor sin el parámetro de contraseña
            return new Veterinario(id, nombre, rut, fechaNacimiento, direccion,
                    numeroTelefono, correoElectronico,
                    especialidad, licencia);
        } catch (JSONException | java.time.format.DateTimeParseException e) {
            System.err.println("Error al parsear JSONObject a Veterinario: " + e.getMessage());
            return null;
        }
    }
}
