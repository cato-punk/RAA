package Controlador;


import Datos.AdoptanteDAO;
import Datos.AnimalDAO;
import Datos.VeterinarioDAO;
import Datos.RescatistaDAO;
import Modelo.Adoptante;
import Modelo.Animal;
import Modelo.Rescatista;
import Modelo.Veterinario;

import java.util.List;
import java.util.Optional;

public class VeterinarioControlador {
    private VeterinarioDAO veterinarioDAO;
    private AdoptanteDAO adoptanteDAO;
    private RescatistaDAO rescatistaDAO;
    private AnimalDAO animalDAO;

    public VeterinarioControlador() {
        this.veterinarioDAO = new VeterinarioDAO();
        this.adoptanteDAO = new AdoptanteDAO();
        this.rescatistaDAO = new RescatistaDAO();
        this.animalDAO = new AnimalDAO();
    }

    /**
     * Intenta iniciar sesión como veterinario.
     * @param correoElectronico Correo del veterinario.
     * @param id ID del veterinario.
     * @return El objeto Veterinario si las credenciales son correctas, o null en caso contrario.
     */
    public Veterinario iniciarSesion(String correoElectronico, String id) {
        Optional<Veterinario> veterinarioOptional = veterinarioDAO.buscarPorId(id);
        if (veterinarioOptional.isPresent()) {
            Veterinario veterinario = veterinarioOptional.get();
            if (veterinario.getCorreoElectronico().equals(correoElectronico)) {
                return veterinario;
            }
        }
        return null;
    }

    /**
     * Busca un rescatista por su ID para visualización por el veterinario.
     * @param id ID del rescatista.
     * @return Optional que contiene el Rescatista si se encuentra.
     */
    public Optional<Rescatista> verRescatistaPorId(String id) {
        return rescatistaDAO.buscarPorId(id);
    }

    /**
     * Busca un adoptante por su ID para visualización por el veterinario.
     * @param id ID del adoptante.
     * @return Optional que contiene el Adoptante si se encuentra.
     */
    public Optional<Adoptante> verAdoptantePorId(String id) {
        return adoptanteDAO.buscarPorId(id);
    }

    /**
     * Busca un animal por su ID para visualización por el veterinario.
     * @param id ID del animal.
     * @return Optional que contiene el Animal si se encuentra.
     */
    public Optional<Animal> verAnimalPorId(String id) {
        return animalDAO.buscarPorId(id);
    }

    /**
     * Cambia el permiso de adopción de un adoptante.
     * @param adoptanteId ID del adoptante a modificar.
     * @param tienePermiso true para SI, false para NO.
     * @return true si se actualizó con éxito, false si el adoptante no fue encontrado.
     */
    public boolean cambiarPermisoAdopcion(String adoptanteId, boolean tienePermiso) {
        Optional<Adoptante> adoptanteOptional = adoptanteDAO.buscarPorId(adoptanteId);
        if (adoptanteOptional.isPresent()) {
            Adoptante adoptante = adoptanteOptional.get();
            adoptante.setPermisoAdopcion(tienePermiso);
            return adoptanteDAO.actualizar(adoptante);
        }
        return false;
    }

    /**
     * Actualiza los atributos de un animal.
     * @param animalId ID del animal a actualizar.
     * @param raza Nueva raza.
     * @param estadoSalud Nuevo estado de salud.
     * @param edadAproximada Nueva edad aproximada.
     * @param estadoAdopcion Nuevo estado de adopción.
     * @param veterinarioAtiendeId ID del veterinario que ahora lo atiende (puede ser el mismo o cambiar).
     * @return true si se actualizó con éxito, false si el animal no fue encontrado.
     */
    public boolean cambiarDatosAnimal(String animalId, String raza, String estadoSalud,
                                      String edadAproximada, Animal.EstadoAdopcion estadoAdopcion,
                                      String veterinarioAtiendeId) {
        Optional<Animal> animalOptional = animalDAO.buscarPorId(animalId);
        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();
            animal.setRaza(raza);
            animal.setEstadoSalud(estadoSalud);
            animal.setEdadAproximada(edadAproximada);
            animal.setEstadoAdopcion(estadoAdopcion);
            animal.setVeterinarioAtiendeId(veterinarioAtiendeId); // Asigna el ID del veterinario
            return animalDAO.actualizar(animal);
        }
        return false;
    }
}