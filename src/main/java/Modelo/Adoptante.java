package Modelo;
import org.json.JSONObject;
import java.time.LocalDate;

public class Adoptante extends Persona {
    public Adoptante(String nombre, LocalDate fechaNacimiento, String direccion, String numeroTelefono, String correoElectronico) {
        super(nombre, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
    }

    // constructor para cargar desde JSON
    public Adoptante(String id, String nombre, LocalDate fechaNacimiento, String direccion, String numeroTelefono, String correoElectronico) {
        super(id, nombre, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
    }

    @Override
    public String toString() {
        return "Adoptante - ID: " + getId() + ", Nombre: " + getNombre() + ", Correo: " + getCorreoElectronico();
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
        jsonObject.put("tipo", "adoptante");
        return jsonObject;
    }

    public static Adoptante fromJSONObject(JSONObject jsonObject) {
        String id = jsonObject.getString("id");
        String nombre = jsonObject.getString("nombre");
        LocalDate fechaNacimiento = LocalDate.parse(jsonObject.getString("fechaNacimiento"));
        //String sexo = jsonObject.getString("sexo");
        String direccion = jsonObject.getString("direccion");
        String numeroTelefono = jsonObject.getString("numeroTelefono");
        String correoElectronico = jsonObject.getString("correoElectronico");
        return new Adoptante(id, nombre, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
    }
}