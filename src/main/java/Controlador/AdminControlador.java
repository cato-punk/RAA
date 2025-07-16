package Controlador;

import Datos.*;
import Modelo.*;

import java.util.List;
import java.util.Optional;

public class AdminControlador {
    private AdminDAO adminDAO;
    private VeterinarioDAO veterinarioDAO;
    private AdoptanteDAO adoptanteDAO;
    private RescatistaDAO rescatistaDAO;
    private AnimalDAO animalDAO;

    public AdminControlador() {
        this.adminDAO = new AdminDAO();
        this.veterinarioDAO = new VeterinarioDAO();
        this.adoptanteDAO = new AdoptanteDAO();
        this.rescatistaDAO = new RescatistaDAO();
        this.animalDAO = new AnimalDAO();
    }

    /**
     * Intenta iniciar sesión como administrador.
     * @param correoElectronico Correo del admin.
     * @param id ID del admin.
     * @param contrasena Contraseña del admin.
     * @return El objeto Admin si las credenciales son correctas, o null en caso contrario.
     */
    public Admin iniciarSesion(String correoElectronico, String id, String contrasena) {
        Optional<Admin> adminOptional = adminDAO.buscarPorId(id);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (admin.getCorreoElectronico().equals(correoElectronico) && admin.getContrasena().equals(contrasena)) {
                return admin;
            }
        }
        return null;
    }

    /**
     * Registra un nuevo veterinario en el sistema.
     * Genera un ID único para el veterinario.
     * @param nombre Nombre completo del veterinario.
     * @param rut RUT del veterinario.
     * @param direccion Dirección del veterinario.
     * @param numeroTelefono Número de teléfono del veterinario.
     * @param correoElectronico Correo electrónico del veterinario.
     * @param especialidad Especialidad del veterinario.
     * @param licencia Estado de la licencia (Activa/Inactiva).
     * @return El ID generado para el nuevo veterinario, o null si ya existe un veterinario con el mismo correo/ID.
     */
    public String registrarVeterinario(String nombre, String rut, String direccion, String numeroTelefono,
                                       String correoElectronico, String especialidad, String licencia) {

        if (nombre.isEmpty() || rut.isEmpty() || correoElectronico.isEmpty()) {
            return null;
        }

        List<Veterinario> veterinarios = veterinarioDAO.cargarTodos();
        boolean correoExistente = veterinarios.stream()
                .anyMatch(v -> v.getCorreoElectronico().equalsIgnoreCase(correoElectronico));
        if (correoExistente) {
            return "CorreoExistente"; // Indicador de que el correo ya está registrado
        }

        String newId = IdGenerator.generateUniqueId();
        Veterinario nuevoVeterinario = new Veterinario(newId, nombre, rut, direccion, numeroTelefono,
                correoElectronico, especialidad, licencia);
        if (veterinarioDAO.agregar(nuevoVeterinario)) {
            return newId;
        }
        return null;
    }

    /**
     * Elimina una entidad del sistema por su ID y tipo.
     * @param tipoEntidad El tipo de entidad a eliminar (animal, veterinario, adoptante, rescatista).
     * @param id ID de la entidad a eliminar.
     * @return true si se eliminó con éxito, false si no se encontró o el tipo es inválido.
     */
    public boolean eliminarEntidad(String tipoEntidad, String id) {
        switch (tipoEntidad.toLowerCase()) {
            case "animal":
                return animalDAO.eliminar(id);
            case "veterinario":
                return veterinarioDAO.eliminar(id);
            case "adoptante":
                return adoptanteDAO.eliminar(id);
            case "rescatista":
                return rescatistaDAO.eliminar(id);
            default:
                return false; // Tipo de entidad no reconocido
        }
    }

    // Métodos para que el admin pueda ver listados completos (para la GUI)
    public List<Adoptante> obtenerTodosAdoptantes() {
        return adoptanteDAO.cargarTodos();
    }

    public List<Veterinario> obtenerTodosVeterinarios() {
        return veterinarioDAO.cargarTodos();
    }

    public List<Animal> obtenerTodosAnimales() {
        return animalDAO.cargarTodos();
    }

    public List<Rescatista> obtenerTodosRescatistas() {
        return rescatistaDAO.cargarTodos();
    }
}
