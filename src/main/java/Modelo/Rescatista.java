package Modelo;

import java.time.LocalDate;


public class Rescatista extends Persona { //la dependencia
    public Rescatista(String nombre, LocalDate fechaNacimiento, String direccion, String numeroTelefono, String correoElectronico) {
        super(nombre, fechaNacimiento, direccion, numeroTelefono, correoElectronico);
    }

    // constructor para cargar desde JSON
    public Rescatista(String id, String nombre, LocalDate fechaNacimiento, String sexo, String direccion, String numeroTelefono, String correoElectronico) {
        super(id, nombre, fechaNacimiento, sexo, direccion, numeroTelefono, correoElectronico);
    }

    @Override
    public String toString() {
        return "Rescatista - ID: " + getId() + ", Nombre: " + getNombre() + ", Correo: " + getCorreoElectronico();
    }

}
