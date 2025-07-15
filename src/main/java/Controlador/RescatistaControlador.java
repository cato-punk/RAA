package Controlador;

import Datos.AnimalDAO;
import Datos.RescatistaDAO;
import Modelo.Animal;
import Modelo.Rescatista;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class RescatistaControlador {
    private RescatistaDAO rescatistaDAO;
    private AnimalDAO animalDAO;

    public RescatistaControlador() {
        this.rescatistaDAO = new RescatistaDAO();
        this.animalDAO = new AnimalDAO();
    }

    /**
     * Registra un nuevo rescatista en el sistema.
     * Genera un ID único para el rescatista.
     * @param nombre Nombre completo del rescatista.
     * @param rut RUT del rescatista.
     * @param direccion Dirección del rescatista.
     * @param numeroTelefono Número de teléfono del rescatista.
     * @param correoElectronico Correo electrónico del rescatista.
     * @return El ID generado para el nuevo rescatista, o null si ya existe un rescatista con el mismo correo/ID.
     */
    public String registrarRescatista(String nombre, String rut, String direccion,
                                      String numeroTelefono, String correoElectronico) {

        if (nombre.isEmpty() || rut.isEmpty() || correoElectronico.isEmpty()) {
            return null;
        }

        // Verificar si el correo ya está en uso
        List<Rescatista> rescatistas = rescatistaDAO.cargarTodos();
        boolean correoExistente = rescatistas.stream()
                .anyMatch(r -> r.getCorreoElectronico().equalsIgnoreCase(correoElectronico));
        if (correoExistente) {
            return "CorreoExistente"; // Indicador
        }

        String newId = IdGenerator.generateUniqueId();
        Rescatista nuevoRescatista = new Rescatista(newId, nombre, rut, direccion, numeroTelefono, correoElectronico);
        if (rescatistaDAO.agregar(nuevoRescatista)) {
            return newId;
        }
        return null;
    }

    /**
     * Intenta iniciar sesión como rescatista.
     * @param id ID del rescatista.
     * @param correoElectronico Correo del rescatista.
     * @return El objeto Rescatista si las credenciales son correctas, o null en caso contrario.
     */
    public Rescatista iniciarSesion(String id, String correoElectronico) {
        Optional<Rescatista> rescatistaOptional = rescatistaDAO.buscarPorId(id);
        if (rescatistaOptional.isPresent()) {
            Rescatista rescatista = rescatistaOptional.get();
            if (rescatista.getCorreoElectronico().equals(correoElectronico)) {
                return rescatista;
            }
        }
        return null;
    }

    /**
     * Informa un nuevo rescate y registra el animal.
     * Asocia el animal al rescatista que lo informó.
     * El estado inicial del animal es RESCATADO.
     * @param rescatista El objeto Rescatista que informa el rescate.
     * @param raza Raza del animal.
     * @param especie Especie del animal.
     * @param sexo Sexo del animal.
     * @param estadoSalud Estado de salud del animal.
     * @param lugarEncuentro Lugar donde se encontró.
     * @param horaRescate Hora del rescate (HH:MM).
     * @param fechaRescate Fecha del rescate (DD-MM-AAAA).
     * @param edadAproximada Edad aproximada.
     * @return El ID del animal rescatado si el registro fue exitoso, o null en caso de error.
     */
    public String informarRescate(Rescatista rescatista, String raza, String especie, String sexo,
                                  String estadoSalud, String lugarEncuentro, String horaRescate,
                                  String fechaRescate, String edadAproximada) {

        if (!isValidTimeFormat(horaRescate) || !isValidDateFormat(fechaRescate)) {
            return null;
        }
        if (raza.isEmpty() || especie.isEmpty() || sexo.isEmpty() || estadoSalud.isEmpty()) {
            return null;
        }

        String newAnimalId = IdGenerator.generateUniqueId();
        Animal nuevoAnimal = new Animal(newAnimalId, raza, especie, sexo, estadoSalud, lugarEncuentro,
                horaRescate, fechaRescate, edadAproximada,
                rescatista.getNombre(), rescatista.getId(),
                Animal.EstadoAdopcion.RESCATADO, null); // Sin veterinario asignado al inicio

        if (animalDAO.agregar(nuevoAnimal)) {
            // Actualizar la lista de animales rescatados del rescatista
            rescatista.addAnimalesRescatadosId(newAnimalId);
            rescatistaDAO.actualizar(rescatista); // Guardar los cambios del rescatista
            return newAnimalId;
        }
        return null;
    }

    /**
     * Busca un rescatista por su ID.
     * @param id ID del rescatista a buscar.
     * @return Optional que contiene el rescatista si se encuentra.
     */
    public Optional<Rescatista> buscarRescatistaPorId(String id) {
        return rescatistaDAO.buscarPorId(id);
    }

    private boolean isValidTimeFormat(String time) {
        try {
            LocalTime.parse(time);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidDateFormat(String date) {
        try {
            String[] parts = date.split("-");
            if (parts.length != 3) return false;
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            return day >= 1 && day <= 31 && month >= 1 && month <= 12 && year > 1900; // Validación s
        } catch (NumberFormatException e) {
            return false;
        }
    }
}