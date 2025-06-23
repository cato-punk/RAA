package Modelo;

import java.time.LocalDate;
import java.util.UUID;
import org.json.JSONObject;
import org.json.JSONException;

public abstract class Persona {
    private String id;
    private String nombre;
    private String rut; // tributo RUT para todas las personas
    private LocalDate fechaNacimiento;
    // private String sexo; // ELIMINAMOS
    private String direccion;
    private String numeroTelefono;
    private String correoElectronico;

    // Constructor principal
    public Persona(String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                   String numeroTelefono, String correoElectronico) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.rut = rut; // asignar el RUT
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
    }

    // Constructor para cargar desde JSON (incluyendo ID y RUT)
    public Persona(String id, String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                   String numeroTelefono, String correoElectronico) {
        this.id = id;
        this.nombre = nombre;
        this.rut = rut; // asignar el RUT
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
    }

    // --- Getters y Setters ---
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getRut() { return rut; } // getter para RUT
    public void setRut(String rut) { this.rut = rut; } // setter para RUT
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getNumeroTelefono() { return numeroTelefono; }
    public void setNumeroTelefono(String numeroTelefono) { this.numeroTelefono = numeroTelefono; }
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }


    public abstract JSONObject toJSONObject(); // sigue siendo abstracto para que las subclases añadan sus propios campos
    // Aunque rut y los demás están en Persona, cada subclase necesita implementar su toJSONObject
    // para añadir el campo "tipo" y quizás otros campos específicos si los tuvieran mas adelante
}