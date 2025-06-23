package Modelo;

import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Adoptante extends Persona {
    private String preferenciasAnimal;
    private List<String> historialAdopciones;

    public Adoptante(String nombre, LocalDate fechaNacimiento, String direccion,
                     String numeroTelefono, String correoElectronico,
                     String preferenciasAnimal, String contrasena) {
        super(nombre, fechaNacimiento, direccion, numeroTelefono, correoElectronico, contrasena);
        this.preferenciasAnimal = preferenciasAnimal;
        this.historialAdopciones = new ArrayList<>();
    }

    public void agregarAdopcion(String idAnimal) {
        this.historialAdopciones.add(idAnimal);
    }

    // Método para obtener el historial de adopciones (copia defensiva)
    public List<String> getHistorialAdopciones() {
        return new ArrayList<>(historialAdopciones);
    }

    // Getter y Setter para preferenciasAnimal
    public String getPreferenciasAnimal() {
        return preferenciasAnimal;
    }

    public void setPreferenciasAnimal(String preferenciasAnimal) {
        this.preferenciasAnimal = preferenciasAnimal;
    }

    @Override
    public String toString() {
        return "Adoptante{" +
                "id='" + getId() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", preferencias='" + preferenciasAnimal + '\'' +
                ", totalAdopciones=" + historialAdopciones.size() +
                '}';
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject json = super.toJSONObject();
        json.put("tipo", "adoptante");
        json.put("preferenciasAnimal", preferenciasAnimal);
        json.put("historialAdopciones", new JSONArray(historialAdopciones));
        return json;
    }

    public static Adoptante fromJSONObject(JSONObject json) {
        Adoptante adoptante = new Adoptante(
                json.getString("nombre"),
                LocalDate.parse(json.getString("fechaNacimiento")),
                json.getString("direccion"),
                json.getString("numeroTelefono"),
                json.getString("correoElectronico"),
                json.getString("preferenciasAnimal"),
                json.getString("contrasena")
        );

        JSONArray historialJson = json.getJSONArray("historialAdopciones");
        for (int i = 0; i < historialJson.length(); i++) {
            adoptante.agregarAdopcion(historialJson.getString(i));
        }
        return adoptante;
    }
}