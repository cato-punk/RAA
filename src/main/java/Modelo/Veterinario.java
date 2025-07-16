package Modelo;

public class Veterinario extends Persona {
    private String especialidad;
    private String licencia; // Activa/Inactiva

    public Veterinario() {
        super();
    }

    public Veterinario(String id, String nombre, String rut, String direccion, String numeroTelefono, String correoElectronico, String especialidad, String licencia) {
        super(id, nombre, rut, direccion, numeroTelefono, correoElectronico);
        this.especialidad = especialidad;
        this.licencia = licencia;
    }

    // Getters y Setters
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

    @Override
    public String toString() {
        return "Veterinario - " + super.toString() +
                ", Especialidad: " + especialidad +
                ", Licencia: " + licencia;
    }
}
