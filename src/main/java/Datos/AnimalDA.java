package Datos;

import Modelo.Animal;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnimalDA {
    private final String RUTA_ARCHIVO = "Animales.json";


    public void guardarAnimal(Animal animal) {
        JSONArray jsonArray = JsonUtil.leerArchivoJson(RUTA_ARCHIVO);
        jsonArray.put(animal.toJSONObject());
        JsonUtil.escribirArchivoJson(RUTA_ARCHIVO, jsonArray);
    }


    public ArrayList<Animal> cargarAnimales() {
        ArrayList<Animal> animales = new ArrayList<>();
        JSONArray jsonArray = JsonUtil.leerArchivoJson(RUTA_ARCHIVO);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            Animal animal = Animal.fromJSONObject(json);
            animales.add(animal);
        }

        return animales;
    }

    public Animal buscarAnimalPorId(String id) {
        JSONArray jsonArray = JsonUtil.leerArchivoJson(RUTA_ARCHIVO);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            if (json.getString("id").equals(id)) {
                return Animal.fromJSONObject(json);
            }
        }

        return null;
    }


    public void actualizarAnimal(Animal animalActualizado) {
        JSONArray jsonArray = JsonUtil.leerArchivoJson(RUTA_ARCHIVO);
        JSONArray nuevoArray = new JSONArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            if (json.getString("id").equals(animalActualizado.getId())) {
                nuevoArray.put(animalActualizado.toJSONObject());
            } else {
                nuevoArray.put(json);
            }
        }

        JsonUtil.escribirArchivoJson(RUTA_ARCHIVO, nuevoArray);
    }

    /**
     * Elimina un animal del archivo JSON por su ID
     */
    public void eliminarAnimal(String id) {
        JSONArray jsonArray = JsonUtil.leerArchivoJson(RUTA_ARCHIVO);
        JSONArray nuevoArray = new JSONArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            if (!json.getString("id").equals(id)) {
                nuevoArray.put(json);
            }
        }

        JsonUtil.escribirArchivoJson(RUTA_ARCHIVO, nuevoArray);
    }

    /**
     * Filtra animales por cualquier atributo
     */
    public List<Animal> filtrarAnimales(String criterio, String valor) {
        List<Animal> animales = cargarAnimales();
        return animales.stream()
                .filter(animal -> {
                    switch (criterio.toLowerCase()) {
                        case "especie": return animal.getEspecie().equalsIgnoreCase(valor);
                        case "raza": return animal.getRaza().equalsIgnoreCase(valor);
                        case "sexo": return animal.getSexo().equalsIgnoreCase(valor);
                        case "edad": return String.valueOf(animal.getEdad()).equals(valor);
                        case "estado": return animal.getEstadoSalud().equalsIgnoreCase(valor);
                        case "lugar": return animal.getLugarEncontrado().contains(valor);
                        default: return true;
                    }
                })
                .collect(Collectors.toList());
    }
}