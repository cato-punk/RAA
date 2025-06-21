package Modelo;
import java.time.LocalDate;


public class Veterinario extends Persona {
    private String numeroLicencia; // atributo de Veterinario

    public Veterinario(String nombre, LocalDate fechaNacimiento, String sexo, String direccion, String numeroTelefono, String correoElectronico, String numeroLicencia) {
        super(nombre, fechaNacimiento, sexo, direccion, numeroTelefono, correoElectronico);
        this.numeroLicencia = numeroLicencia;
    }

    // constructor para cargar desde JSON
    public Veterinario(String id, String nombre, LocalDate fechaNacimiento, String sexo, String direccion, String numeroTelefono, String correoElectronico, String numeroLicencia) {
        super(id, nombre, fechaNacimiento, sexo, direccion, numeroTelefono, correoElectronico);
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

}
