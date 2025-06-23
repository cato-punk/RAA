package Modelo;

import java.time.LocalDate;
import org.json.JSONObject;
import org.json.JSONException;
import org.mindrot.jbcrypt.BCrypt; // Importar BCrypt

public class Veterinario extends Persona {

    private String especialidad;
    private String licencia;
    private String contrasenaHash; // para almacenar la contraseña hasheada

    // constructor principal para crear un nuevo veterinario, ahora con contraseña plana
    public Veterinario(String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                       String numeroTelefono, String correoElectronico,
                       String especialidad, String licencia, String contrasenaPlana) { //Contraseña plana
        super(nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        this.especialidad = especialidad;
        this.licencia = licencia;
        this.contrasenaHash = BCrypt.hashpw(contrasenaPlana, BCrypt.gensalt()); // Hashing de la contraseña
    }

    // constructor incluyendo ID y contraseña hasheada
    public Veterinario(String id, String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                       String numeroTelefono, String correoElectronico,
                       String especialidad, String licencia, String contrasenaHash) { //  ya hasheada la contraseña
        super(id, nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        this.especialidad = especialidad;
        this.licencia = licencia;
        this.contrasenaHash = contrasenaHash; // asignar la contraseña hasheada
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

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    @Override
    public String toString() {
        return "Veterinario - ID: " + getId() + ", Nombre: " + getNombre() + ", RUT: " + getRut() +
                ", Correo: " + getCorreoElectronico() + ", Especialidad: " + especialidad +
                ", Licencia: " + licencia;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject = super.toJSONObject();
        try {
            jsonObject.put("tipo", "veterinario");
            jsonObject.put("especialidad", especialidad);
            jsonObject.put("licencia", licencia);
            jsonObject.put("contrasenaHash", contrasenaHash);
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
            String contrasenaHash = jsonObject.getString("contrasenaHash");

            return new Veterinario(id, nombre, rut, fechaNacimiento, direccion,
                    numeroTelefono, correoElectronico,
                    especialidad, licencia, contrasenaHash);
        } catch (JSONException | java.time.format.DateTimeParseException e) {
            System.err.println("Error al parsear JSONObject a Veterinario: " + e.getMessage());
            return null;
        }
    }
}