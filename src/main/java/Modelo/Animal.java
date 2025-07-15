package Modelo;

public class Animal {
    private String id; // ID único del animal
    private String raza;
    private String especie;
    private String sexo;
    private String estadoSalud;
    private String lugarEncuentro;
    private String horaRescate; // Formato HH:MM
    private String fechaRescate; // Formato DD-MM-AAAA
    private String edadAproximada; // Dada por el rescatista
    private String nombreRescatistaEncontro;
    private String idRescatistaEncontro; // Visible solo para el veterinario
    private EstadoAdopcion estadoAdopcion; // Enum: RESCATADO, EN_ADOPCION, ADOPTADO
    private String veterinarioAtiendeId; // ID del veterinario que lo atiende

    // Enum para el estado de adopción
    public enum EstadoAdopcion {
        RESCATADO,
        EN_ADOPCION,
        ADOPTADO
    }

    // Constructor vacío requerido por Gson
    public Animal() {
    }

    public Animal(String id, String raza, String especie, String sexo, String estadoSalud, String lugarEncuentro,
                  String horaRescate, String fechaRescate, String edadAproximada, String nombreRescatistaEncontro,
                  String idRescatistaEncontro, EstadoAdopcion estadoAdopcion, String veterinarioAtiendeId) {
        this.id = id;
        this.raza = raza;
        this.especie = especie;
        this.sexo = sexo;
        this.estadoSalud = estadoSalud;
        this.lugarEncuentro = lugarEncuentro;
        this.horaRescate = horaRescate;
        this.fechaRescate = fechaRescate;
        this.edadAproximada = edadAproximada;
        this.nombreRescatistaEncontro = nombreRescatistaEncontro;
        this.idRescatistaEncontro = idRescatistaEncontro;
        this.estadoAdopcion = estadoAdopcion;
        this.veterinarioAtiendeId = veterinarioAtiendeId;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
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

    public String getLugarEncuentro() {
        return lugarEncuentro;
    }

    public void setLugarEncuentro(String lugarEncuentro) {
        this.lugarEncuentro = lugarEncuentro;
    }

    public String getHoraRescate() {
        return horaRescate;
    }

    public void setHoraRescate(String horaRescate) {
        this.horaRescate = horaRescate;
    }

    public String getFechaRescate() {
        return fechaRescate;
    }

    public void setFechaRescate(String fechaRescate) {
        this.fechaRescate = fechaRescate;
    }

    public String getEdadAproximada() {
        return edadAproximada;
    }

    public void setEdadAproximada(String edadAproximada) {
        this.edadAproximada = edadAproximada;
    }

    public String getNombreRescatistaEncontro() {
        return nombreRescatistaEncontro;
    }

    public void setNombreRescatistaEncontro(String nombreRescatistaEncontro) {
        this.nombreRescatistaEncontro = nombreRescatistaEncontro;
    }

    public String getIdRescatistaEncontro() {
        return idRescatistaEncontro;
    }

    public void setIdRescatistaEncontro(String idRescatistaEncontro) {
        this.idRescatistaEncontro = idRescatistaEncontro;
    }

    public EstadoAdopcion getEstadoAdopcion() {
        return estadoAdopcion;
    }

    public void setEstadoAdopcion(EstadoAdopcion estadoAdopcion) {
        this.estadoAdopcion = estadoAdopcion;
    }

    public String getVeterinarioAtiendeId() {
        return veterinarioAtiendeId;
    }

    public void setVeterinarioAtiendeId(String veterinarioAtiendeId) {
        this.veterinarioAtiendeId = veterinarioAtiendeId;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "ID='" + id + '\'' +
                ", Raza='" + raza + '\'' +
                ", Especie='" + especie + '\'' +
                ", Sexo='" + sexo + '\'' +
                ", Estado Salud='" + estadoSalud + '\'' +
                ", Lugar Encuentro='" + lugarEncuentro + '\'' +
                ", Hora Rescate='" + horaRescate + '\'' +
                ", Fecha Rescate='" + fechaRescate + '\'' +
                ", Edad Aproximada='" + edadAproximada + '\'' +
                ", Rescatista='" + nombreRescatistaEncontro + '\'' +
                ", ID Rescatista='" + idRescatistaEncontro + '\'' +
                ", Estado Adopcion=" + estadoAdopcion +
                ", Veterinario Atiende ID='" + veterinarioAtiendeId + '\'' +
                '}';
    }
}

