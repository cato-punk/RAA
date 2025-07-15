package Modelo;

public class Admin extends Persona {
    private String contrasena;  // Contraseña única de administrador

    public Admin() {
        super();
    }

    public Admin(String id, String nombre, String rut, String direccion, String numeroTelefono, String correoElectronico, String contrasena) {
        super(id, nombre, rut, direccion, numeroTelefono, correoElectronico);
        this.contrasena = contrasena;
    }

    // Getter y Setter
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Admin - " + super.toString() +
                ", Contraseña: [OCULTA]"; // No mostrar la contraseña directamente
    }
}