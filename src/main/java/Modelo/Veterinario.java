package Modelo;
import java.time.LocalDate;
import org.json.JSONObject;

public class Veterinario extends Persona {
    private String numeroLicencia; // atributo de Veterinario

    public Veterinario(String nombre, LocalDate fechaNacimiento, String sexo, String direccion, String numeroTelefono, String correoElectronico, String numeroLicencia) {
        super(nombre, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        this.numeroLicencia = numeroLicencia;
    }

    // constructor para cargar desde JSON
    public Veterinario(String id, String nombre, LocalDate fechaNacimiento, String direccion, String numeroTelefono, String correoElectronico, String numeroLicencia) {
        super(id, nombre, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        this.numeroLicencia = numeroLicencia;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    @Override
    public String toString() {
        return "Veterinario - ID: " + getId() + ", Nombre: " + getNombre() + ", Licencia: " + numeroLicencia;
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", getId());
        jsonObject.put("nombre", getNombre());
        jsonObject.put("fechaNacimiento", getFechaNacimiento().toString());
        //jsonObject.put("sexo", getSexo());
        jsonObject.put("direccion", getDireccion());
        jsonObject.put("numeroTelefono", getNumeroTelefono());
        jsonObject.put("correoElectronico", getCorreoElectronico());
        jsonObject.put("numeroLicencia", numeroLicencia);
        jsonObject.put("tipo", "veterinario"); // Para diferenciar al cargar
        return jsonObject;
    }

    public static Veterinario fromJSONObject(JSONObject jsonObject) {
        String id = jsonObject.getString("id");
        String nombre = jsonObject.getString("nombre");
        LocalDate fechaNacimiento = LocalDate.parse(jsonObject.getString("fechaNacimiento"));
        //String sexo = jsonObject.getString("sexo");
        String direccion = jsonObject.getString("direccion");
        String numeroTelefono = jsonObject.getString("numeroTelefono");
        String correoElectronico = jsonObject.getString("correoElectronico");
        String numeroLicencia = jsonObject.getString("numeroLicencia");
        return new Veterinario(id, nombre, fechaNacimiento, direccion, numeroTelefono, correoElectronico, numeroLicencia);
    }
}
