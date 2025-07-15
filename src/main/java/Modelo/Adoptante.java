package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Adoptante extends Persona {
    private String preferencias;
    private String informacionAdopcion;
    private boolean permisoAdopcion; // true (Si) / false (No)
    private List<String> animalesAdoptadosIds; // IDs de animales que ha adoptado

    public Adoptante() {
        super();
        this.permisoAdopcion = false; // Por defecto es NO
        this.animalesAdoptadosIds = new ArrayList<>();
    }

    public Adoptante(String id, String nombre, String rut, String direccion, String numeroTelefono, String correoElectronico, String preferencias, String informacionAdopcion) {
        super(id, nombre, rut, direccion, numeroTelefono, correoElectronico);
        this.preferencias = preferencias;
        this.informacionAdopcion = informacionAdopcion;
        this.permisoAdopcion = false; // Por defecto es NO
        this.animalesAdoptadosIds = new ArrayList<>();
    }

    // Getters y Setters
    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }

    public String getInformacionAdopcion() {
        return informacionAdopcion;
    }

    public void setInformacionAdopcion(String informacionAdopcion) {
        this.informacionAdopcion = informacionAdopcion;
    }

    public boolean isPermisoAdopcion() {
        return permisoAdopcion;
    }

    public void setPermisoAdopcion(boolean permisoAdopcion) {
        this.permisoAdopcion = permisoAdopcion;
    }

    public List<String> getAnimalesAdoptadosIds() {
        return animalesAdoptadosIds;
    }

    public void setAnimalesAdoptadosIds(List<String> animalesAdoptadosIds) {
        this.animalesAdoptadosIds = animalesAdoptadosIds;
    }

    public void agregarAnimalAdoptadoId(String animalId) {
        if (!this.animalesAdoptadosIds.contains(animalId)) {
            this.animalesAdoptadosIds.add(animalId);
        }
    }

    @Override
    public String toString() {
        return "Adoptante - " + super.toString() +
                ", Preferencias: " + preferencias +
                ", Info Adopcion: " + informacionAdopcion +
                ", Permiso Adopcion: " + (permisoAdopcion ? "SI" : "NO") +
                ", Animales Adoptados IDs: " + (animalesAdoptadosIds.isEmpty() ? "Ninguno" : animalesAdoptadosIds);
    }
}