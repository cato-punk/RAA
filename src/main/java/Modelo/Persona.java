package Modelo;

import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public abstract class Persona {
    private String id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String sexo;  // Mantenido pero no usado en constructores
    private String direccion;
    private String numeroTelefono;
    private String correoElectronico;
    private String contrasena;
    // Constructor principal
    public Persona(String nombre, LocalDate fechaNacimiento, String direccion,
                   String numeroTelefono, String correoElectronico, String contrasena){
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
}
    // Constructor para carga desde JSON
    public Persona(String id, String nombre, String fechaNacimientoStr,
                   String direccion, String numeroTelefono, String correoElectronic,String contrasena){
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = LocalDate.parse(fechaNacimientoStr, DateTimeFormatter.ISO_LOCAL_DATE);
        this.direccion = direccion;
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;   }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    public String getContrasena() {
        return contrasena;
    }
    // Método para conversión a JSON
    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("nombre", nombre);
        json.put("fechaNacimiento", fechaNacimiento.format(DateTimeFormatter.ISO_LOCAL_DATE));
        json.put("direccion", direccion);
        json.put("numeroTelefono", numeroTelefono);
        json.put("correoElectronico", correoElectronico);
        json.put("contrasena", contrasena);
        json.put("sexo", sexo);

        return json;
    }

    public static Persona fromJSONObject(JSONObject json) {
        throw new UnsupportedOperationException("Debe implementarse en subclases");
    }

    @Override
    public String toString() {
        return String.format("Persona[id=%s, nombre=%s, fechaNacimiento=%s]",
                id, nombre, fechaNacimiento);
    }
}