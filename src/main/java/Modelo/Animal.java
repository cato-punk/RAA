package Modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Animal implements Serializable {
    private String id;
    private String especie;
    private String raza;
    private String sexo;
    private String estadoSalud;
    private String lugarEncontrado;
    private LocalDateTime fechaHoraRescate;
    private int edadAproximadaAnios;
    
    public Animal(String especie, String raza, String sexo, String estadoSalud,
                  String lugarEncontrado, LocalDateTime fechaHoraRescate, int edadAproximadaAnios) {
        this.id = UUID.randomUUID().toString();
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.estadoSalud = estadoSalud;
        this.lugarEncontrado = lugarEncontrado;
        this.fechaHoraRescate = fechaHoraRescate;
        this.edadAproximadaAnios = edadAproximadaAnios;
    }

    public Animal(String especie, String raza, String sexo, String estadoSalud,
                  String lugarEncontrado, LocalDateTime fechaHoraRescate, int edadAproximadaAnios, String id) {
        this(especie, raza, sexo, estadoSalud, lugarEncontrado, fechaHoraRescate, edadAproximadaAnios);
        this.id = id;
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

    @Override
    public String toString() {
        return "Animal {" +
                "ID='" + id + '\'' +
                ", Especie='" + especie + '\'' +
                ", Raza='" + raza + '\'' +
                ", Sexo='" + sexo + '\'' +
                ", Estado Salud='" + estadoSalud + '\'' +
                ", Lugar Encontrado='" + lugarEncontrado + '\'' +
                ", Fecha/Hora Rescate=" + fechaHoraRescate.toLocalDate() + " " + fechaHoraRescate.toLocalTime() +
                ", Edad Aprox.=" + edadAproximadaAnios + " años" +
                '}';
    }

}