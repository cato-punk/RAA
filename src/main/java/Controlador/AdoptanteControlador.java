package Controlador;

import Datos.AdoptanteDAO;
import Datos.AnimalDAO;
import Modelo.Adoptante;
import Modelo.Animal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdoptanteControlador {
    private AdoptanteDAO adoptanteDAO;
    private AnimalDAO animalDAO;

    public AdoptanteControlador() {
        this.adoptanteDAO = new AdoptanteDAO();
        this.animalDAO = new AnimalDAO();
    }

    /**
     * Registra un nuevo adoptante en el sistema.
     * Genera un ID único para el adoptante.
     * Por defecto, el permiso de adopción es NO.
     * @param nombre Nombre completo del adoptante.
     * @param rut RUT del adoptante.
     * @param direccion Dirección del adoptante.
     * @param numeroTelefono Número de teléfono del adoptante.
     * @param correoElectronico Correo electrónico del adoptante.
     * @param preferencias Preferencias de adopción.
     * @param informacionAdopcion Información adicional para la adopción.
     * @return El ID generado para el nuevo adoptante, o null si ya existe un adoptante con el mismo correo/ID.
     */
    public String registrarAdoptante(String nombre, String rut, String direccion, String numeroTelefono,
                                     String correoElectronico, String preferencias, String informacionAdopcion) {

        if (nombre.isEmpty() || rut.isEmpty() || correoElectronico.isEmpty()) {
            return null;
        }

        //si el correo ya está en uso
        List<Adoptante> adoptantes = adoptanteDAO.cargarTodos();
        boolean correoExistente = adoptantes.stream()
                .anyMatch(a -> a.getCorreoElectronico().equalsIgnoreCase(correoElectronico));
        if (correoExistente) {
            return "CorreoExistente"; // Indicador
        }

        String newId = IdGenerator.generateUniqueId();
        Adoptante nuevoAdoptante = new Adoptante(newId, nombre, rut, direccion, numeroTelefono,
                correoElectronico, preferencias, informacionAdopcion);
        // Por defecto, permisoAdopcion es false en el constructor
        if (adoptanteDAO.agregar(nuevoAdoptante)) {
            return newId;
        }
        return null;
    }

    /**
     * Intenta iniciar sesión como adoptante.
     * Verifica ID, correo y permiso de adopción.
     * @param id ID del adoptante.
     * @param correoElectronico Correo del adoptante.
     * @return El objeto Adoptante si las credenciales son correctas y tiene permiso,
     * "SinPermiso" si las credenciales son correctas pero no tiene permiso,
     * o null si las credenciales son incorrectas.
     */
    public Adoptante iniciarSesion(String id, String correoElectronico) {
        Optional<Adoptante> adoptanteOptional = adoptanteDAO.buscarPorId(id);
        if (adoptanteOptional.isPresent()) {
            Adoptante adoptante = adoptanteOptional.get();
            if (adoptante.getCorreoElectronico().equals(correoElectronico)) {
                if (adoptante.isPermisoAdopcion()) {
                    return adoptante;
                } else {
                    return null; // Credenciales correctas pero sin permiso, indicará desde la GUI
                }
            }
        }
        return null; // Credenciales incorrectas
    }

    /**
     * Obtiene una lista de animales que están en estado "EN_ADOPCION".
     * @return Lista de animales disponibles para adopción.
     */
    public List<Animal> obtenerAnimalesEnAdopcion() {
        return animalDAO.cargarTodos().stream()
                .filter(animal -> animal.getEstadoAdopcion() == Animal.EstadoAdopcion.EN_ADOPCION)
                .collect(Collectors.toList());
    }

    /**
     * Permite a un adoptante adoptar un animal.
     * Cambia el estado del animal a "ADOPTADO" y lo asocia al adoptante.
     * @param adoptanteId ID del adoptante que adopta.
     * @param animalId ID del animal a adoptar.
     * @return true si la adopción fue exitosa, false en caso contrario (animal no encontrado, no en adopción, etc.).
     */
    public boolean adoptarAnimal(String adoptanteId, String animalId) {
        Optional<Animal> animalOptional = animalDAO.buscarPorId(animalId);
        Optional<Adoptante> adoptanteOptional = adoptanteDAO.buscarPorId(adoptanteId);

        if (animalOptional.isPresent() && adoptanteOptional.isPresent()) {
            Animal animal = animalOptional.get();
            Adoptante adoptante = adoptanteOptional.get();

            // Solo se puede adoptar si el animal está "EN_ADOPCION"
            if (animal.getEstadoAdopcion() == Animal.EstadoAdopcion.EN_ADOPCION) {
                animal.setEstadoAdopcion(Animal.EstadoAdopcion.ADOPTADO);
                boolean animalActualizado = animalDAO.actualizar(animal);

                if (animalActualizado) {
                    adoptante.agregarAnimalAdoptadoId(animal.getId());
                    adoptanteDAO.actualizar(adoptante);
                    return true;
                }
            }
        }
        return false;
    }
}