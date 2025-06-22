package Modelo; //clase abstracta es clase padre o Super

import java.time.LocalDate;
import java.util.UUID;
import org.json.JSONObject;

public abstract class Persona {
    private String id;
    private String nombre;
    private LocalDate fechaNacimiento;
    //private String sexo; (no veo que esto sea una carcteristica importante)
    private String direccion;
    private String numeroTelefono;
    private String correoElectronico;

    public Persona(String nombre, LocalDate fechaNacimiento, String direccion, String numeroTelefono, String correoElectronico) {
        this.id = UUID.randomUUID().toString(); // genera iD unico
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        //this.sexo = sexo;
        this.direccion = direccion;
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
    }

    // nos faltan los constructores de json , una ve que JSONutil se haga
    public Persona(String id, String nombre, LocalDate fechaNacimiento, String direccion, String numeroTelefono, String correoElectronico) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        //this.sexo = sexo;
        this.direccion = direccion;
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
    }
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

    //public String getSexo() {
    //    return sexo;
    // }

    //public void setSexo(String sexo) {
    //    this.sexo = sexo;
    //}

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
    public abstract JSONObject toJSONObject();
}