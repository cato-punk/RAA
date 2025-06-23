package Modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID; //generar IDs únicos
import org.json.JSONObject; // Dependencia para JSON (mas adelante)
import org.json.JSONException; // para manejo de excepciones JSON (mas adelante)

public class Animal {
    private String id; // ID único para el animal
    private String especie;
    private String raza;
    private String sexo;
    private String estadoSalud;
    private String lugarEncontrado;
    private LocalDateTime fechaHoraRescate;
    private int edad;

    public Animal(String id, String especie, String raza, String sexo, String estadoSalud, String lugarEncontrado, LocalDateTime fechaHoraRescate, int edad) {
        this.id = id;
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
    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("especie", this.especie);
        json.put("raza", this.raza);
        json.put("sexo", this.sexo);
        json.put("estadoSalud", this.estadoSalud);
        json.put("lugarEncontrado", this.lugarEncontrado);
        json.put("fechaHoraRescate", this.fechaHoraRescate.toString());
        json.put("edad", this.edad);
        return json;
    }



    //para construir un objeto Animal desde un JSONObject
    public static Animal fromJSONObject(JSONObject jsonObject) {
        try {
            String id = jsonObject.getString("id");
            String especie = jsonObject.getString("especie");
            String raza = jsonObject.getString("raza");
            String sexo = jsonObject.getString("sexo");
            String estadoSalud = jsonObject.getString("estadoSalud");
            String lugarEncontrado = jsonObject.getString("lugarEncontrado");
            LocalDateTime fechaHoraRescate = LocalDateTime.parse(jsonObject.getString("fechaHoraRescate")); // Convertir String a LocalDateTime
            int edad = jsonObject.getInt("edad");
            return new Animal(id, especie, raza, sexo, estadoSalud, lugarEncontrado, fechaHoraRescate, edad);
        } catch (JSONException | java.time.format.DateTimeParseException e) {
            System.err.println("Error al parsear JSONObject a Animal: " + e.getMessage());
            return null;
        }
    }


}