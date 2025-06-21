package Modelo;

import java.time.LocalDate;

public class Adoptante extends Persona {
    public Adoptante(String nombre, LocalDate fechaNacimiento, String sexo, String direccion, String numeroTelefono, String correoElectronico) {
        super(nombre, fechaNacimiento, sexo, direccion, numeroTelefono, correoElectronico);
    }

    // constructor para cargar desde JSON
    public Adoptante(String id, String nombre, LocalDate fechaNacimiento, String sexo, String direccion, String numeroTelefono, String correoElectronico) {
        super(id, nombre, fechaNacimiento, sexo, direccion, numeroTelefono, correoElectronico);
    }

    @Override
    public String toString() {
        return "Adoptante - ID: " + getId() + ", Nombre: " + getNombre() + ", Correo: " + getCorreoElectronico();
    }

}