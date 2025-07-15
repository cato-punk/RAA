package Modelo;

public class Persona {
    protected String id; // ID único para cada persona
    protected String nombre; // nombre + apellido
    protected String rut;
    protected String direccion;
    protected String numeroTelefono;
    protected String correoElectronico;

    // Constructor vacío requerido por Gson para deserialización
    public Persona() {
    }

    public Persona(String id, String nombre, String rut, String direccion, String numeroTelefono, String correoElectronico) {
        this.id = id;
        this.nombre = nombre;
        this.rut = rut;
        this.direccion = direccion;
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
    }

    // Getters y Setters (necesarios para Gson y para acceso a atributos)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
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

    @Override //tenemos que sobreescribir
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", RUT: " + rut +
                ", Dirección: " + direccion + ", Teléfono: " + numeroTelefono +
                ", Correo: " + correoElectronico;
    }
}
