package Modelo;

import org.json.JSONObject;
import org.json.JSONException;
import java.time.LocalDate;

public class Veterinario extends Persona {
    private String especialidad;
    private String licencia;
    // private String rut; // ELIMINADO ahora esta en Persona

    // constructor principal, ahora con rut pasado a super
    public Veterinario(String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                       String numeroTelefono, String correoElectronico, String especialidad, String licencia) {
        // llama al constructor de Persona con sus atributos, incluyendo rut
        super(nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        // this.rut = rut; // Ya no es necesario, está en Persona
        this.especialidad = especialidad;
        this.licencia = licencia;
    }

    // constructor para cargar desde JSON (similar al de Rescatista y Adoptante), ahora con rut pasado a super
    public Veterinario(String id, String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                       String numeroTelefono, String correoElectronico, String especialidad, String licencia) {
        // llama al constructor de Persona con sus atributos y el ID, incluyendo rut
        super(id, nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        // this.rut = rut; // Ya no es necesario, está en Persona
        this.especialidad = especialidad;
        this.licencia = licencia;
    }

    // getters y setters para atributos (rut ya está en Persona)
    // public String getRut() { return super.getRut(); } //  accederlo a traves del Veterinario, poder usar super.getRut()
    // public void setRut(String rut) { super.setRut(rut); } //quieres modificarlo... a traves del Veterinario

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public String getLicencia() { return licencia; }
    public void setLicencia(String licencia) { this.licencia = licencia; }

    @Override
    public String toString() {
        return "Veterinario - ID: " + getId() + ", Nombre: " + getNombre() +
                ", RUT: " + getRut() + ", Especialidad: " + especialidad + // Acceder a RUT desde Persona
                ", Licencia: " + licencia + ", Correo: " + getCorreoElectronico();
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject = super.toJSONObject(); // obtiene el JSONObject de Persona (que ya incluye RUT)
        try {
            // jsonObject.put("rut", this.getRut()); //no es necesario, super.toJSONObject() ya lo incluye
            jsonObject.put("especialidad", this.especialidad);
            jsonObject.put("licencia", this.licencia);
            jsonObject.put("tipo", "veterinario"); // asegura que el tipo sea 'veterinario'
        } catch (JSONException e) {
            System.err.println("Error al crear JSONObject de Veterinario: " + e.getMessage());
        }
        return jsonObject;
    }

    public static Veterinario fromJSONObject(JSONObject jsonObject) {
        try {
            //
            String id = jsonObject.getString("id");
            String nombre = jsonObject.getString("nombre");
            String rut = jsonObject.getString("rut"); // Obtener RUT del JSON
            LocalDate fechaNacimiento = LocalDate.parse(jsonObject.getString("fechaNacimiento"));
            String direccion = jsonObject.getString("direccion");
            String numeroTelefono = jsonObject.getString("numeroTelefono");
            String correoElectronico = jsonObject.getString("correoElectronico");

            // Atributos de Veterinario
            String especialidad = jsonObject.getString("especialidad");
            String licencia = jsonObject.getString("licencia");

            return new Veterinario(id, nombre, rut, fechaNacimiento, direccion, numeroTelefono,
                    correoElectronico, especialidad, licencia);
        } catch (JSONException | java.time.format.DateTimeParseException e) {
            System.err.println("Error al parsear JSONObject a Veterinario: " + e.getMessage());
            return null;
        }
    }
}