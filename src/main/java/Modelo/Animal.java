package Modelo;

import java.time.LocalDateTime;
import java.util.UUID; //generar IDs únicos
//import org.json.JSONObject; // Dependencia para JSON (mas adelante)
//import org.json.JSONException; // para manejo de excepciones JSON (mas adelante)

public class Animal {
    private String id; // ID único para el animal
    private String especie;
    private String raza;
    private String sexo;
    private String estadoSalud;
    private String lugarEncontrado;
    private LocalDateTime fechaHoraRescate;
    private int edad;

    public Animal(String especie, String raza, String sexo, String estadoSalud, String lugarEncontrado, LocalDateTime fechaHoraRescate, int edad) {
        this.id = UUID.randomUUID().toString(); // Generar un ID único al crear el animal
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.estadoSalud = estadoSalud;
        this.lugarEncontrado = lugarEncontrado;
        this.fechaHoraRescate = fechaHoraRescate;
        this.edad = edad;
    }



    // Getters y Setters
    public String getId() {
        return id;
    }

    // No hay setter para ID una vez creado

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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Especie: " + especie +
                ", Raza: " + raza +
                ", Sexo: " + sexo +
                ", Estado de Salud: " + estadoSalud +
                ", Lugar Encontrado: " + lugarEncontrado +
                ", Fecha/Hora Rescate: " + fechaHoraRescate.toLocalDate() + " " + fechaHoraRescate.toLocalTime() +
                ", Edad: " + edad + " años.";
    }

}