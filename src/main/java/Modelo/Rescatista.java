package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Rescatista extends Persona {
    private List<String> animalesRescatadosIds; // Lista de IDs de animales que el rescatista ha rescatado

    public Rescatista(String id, String nombre, String rut, String direccion, String numeroTelefono, String correoElectronico) {
        super(id, nombre, rut, direccion, numeroTelefono, correoElectronico);
        this.animalesRescatadosIds = new ArrayList<>();
    }

    // Constructor para cargar desde JSON (si se usa)
    public Rescatista(String id, String nombre, String rut, String direccion, String numeroTelefono, String correoElectronico, List<String> animalesRescatadosIds) {
        super(id, nombre, rut, direccion, numeroTelefono, correoElectronico);
        this.animalesRescatadosIds = animalesRescatadosIds != null ? animalesRescatadosIds : new ArrayList<>();
    }

    public List<String> getAnimalesRescatadosIds() {
        return animalesRescatadosIds;
    }

    public void setAnimalesRescatadosIds(List<String> animalesRescatadosIds) {
        this.animalesRescatadosIds = animalesRescatadosIds;
    }

    /**
     * Añade un ID de animal a la lista de animales rescatados por este rescatista.
     * Si la lista es null, la inicializa.
     * @param animalId El ID del animal a añadir.
     */
    public void addAnimalesRescatadosId(String animalId) {
        if (this.animalesRescatadosIds == null) {
            this.animalesRescatadosIds = new ArrayList<>();
        }
        this.animalesRescatadosIds.add(animalId);
    }

    @Override
    public String toString() {
        return "Rescatista{" +
                "id='" + getId() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", rut='" + getRut() + '\'' +
                ", direccion='" + getDireccion() + '\'' +
                ", numeroTelefono='" + getNumeroTelefono() + '\'' +
                ", correoElectronico='" + getCorreoElectronico() + '\'' +
                ", animalesRescatadosIds=" + animalesRescatadosIds +
                '}';
    }
}
