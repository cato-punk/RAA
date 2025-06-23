package Modelo;

import java.time.LocalDate;

public class Veterinario extends Persona {
    private String especialidad;
    private String licencia; // Nuevo atributo

    // Constructor completo, pasando todos los atributos de Persona y los propios de Veterinario
    public Veterinario(String nombre, String rut, LocalDate fechaNacimiento, String direccion,
                       String numeroTelefono, String correoElectronico, String especialidad, String licencia) {
        super(nombre, rut, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
        this.especialidad = especialidad;
        this.licencia = licencia;
    }


    public Veterinario(String nombre, String rut, String especialidad, String licencia) {
        super(nombre, rut); // Llama al constructor de Persona con nombre y rut
        this.especialidad = especialidad;
        this.licencia = licencia;
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

    @Override
    public String toString() {
        return "Veterinario [" + super.toString() +
                ", Especialidad: " + especialidad +
                ", Licencia: " + licencia + "]";
    }


}