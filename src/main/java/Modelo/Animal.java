package Modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Animal implements Serializable {

    private String id;
    private String especie;
    private String raza;
    private String sexo;
    private String estadoSalud; // "disponible", "adoptado", "en_proceso_adopcion", "rescatado", etc.
    private String lugarEncontrado;
    private LocalDateTime fechaHoraRescate;
    private int edadAproximadaAnios;
    private String diagnostico;
    private String idRescatista;
    private String idAdoptante; // para el ID del adoptante

    public Animal(String especie, String raza, String sexo, String estadoSalud,
                  String lugarEncontrado, LocalDateTime fechaHoraRescate,
                  int edadAproximadaAnios, String idRescatista) {
        this.id = UUID.randomUUID().toString();
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.estadoSalud = estadoSalud;
        this.lugarEncontrado = lugarEncontrado;
        this.fechaHoraRescate = fechaHoraRescate;
        this.edadAproximadaAnios = edadAproximadaAnios;
        this.diagnostico = "No diagnosticado";
        this.idRescatista = idRescatista;
        this.idAdoptante = null; // hasta que sea adoptado
    }

    public Animal(String id, String especie, String raza, String sexo, String estadoSalud,
                  String lugarEncontrado, LocalDateTime fechaHoraRescate,
                  int edadAproximadaAnios, String diagnostico, String idRescatista, String idAdoptante) {
        this.id = id;
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.estadoSalud = estadoSalud;
        this.lugarEncontrado = lugarEncontrado;
        this.fechaHoraRescate = fechaHoraRescate;
        this.edadAproximadaAnios = edadAproximadaAnios;
        this.diagnostico = diagnostico;
        this.idRescatista = idRescatista;
        this.idAdoptante = idAdoptante;
    }

    public Animal() {
        // Constructor vacío
    }

    public String getId() {
        return id;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstadoSalud() {
        return estadoSalud;
    }

    public void setEstadoSalud(String estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    public String getLugarEncontrado() {
        return lugarEncontrado;
    }

    public void setLugarEncontrado(String lugarEncontrado) {
        this.lugarEncontrado = lugarEncontrado;
    }

    public LocalDateTime getFechaHoraRescate() {
        return fechaHoraRescate;
    }

    public void setFechaHoraRescate(LocalDateTime fechaHoraRescate) {
        this.fechaHoraRescate = fechaHoraRescate;
    }

    public int getEdadAproximadaAnios() {
        return edadAproximadaAnios;
    }

    public void setEdadAproximadaAnios(int edadAproximadaAnios) {
        this.edadAproximadaAnios = edadAproximadaAnios;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getIdRescatista() {
        return idRescatista;
    }

    public void setIdRescatista(String idRescatista) {
        this.idRescatista = idRescatista;
    }

    public String getIdAdoptante() {
        return idAdoptante;
    }

    public void setIdAdoptante(String idAdoptante) {
        this.idAdoptante = idAdoptante;
    }

    @Override
    public String toString() {
        return "Animal {" +
                "ID='" + id + '\'' +
                ", Especie='" + especie + '\'' +
                ", Raza='" + raza + '\'' +
                ", Sexo='" + sexo + '\'' +
                ", Estado Salud='" + estadoSalud + '\'' +
                ", Lugar Encontrado='" + lugarEncontrado + '\'' +
                ", Fecha/Hora Rescate=" + (fechaHoraRescate != null ? fechaHoraRescate.toLocalDate() + " " + fechaHoraRescate.toLocalTime() : "N/A") +
                ", Edad Aprox.=" + edadAproximadaAnios + " años" +
                ", Diagnostico='" + diagnostico + '\'' +
                ", ID Rescatista='" + (idRescatista != null ? idRescatista : "N/A") + '\'' +
                ", ID Adoptante='" + (idAdoptante != null ? idAdoptante : "N/A") + '\'' + // añadido
                '}';
    }
}