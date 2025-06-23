package Datos;

import Modelo.Animal;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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


    public List<Animal> cargarAnimales() {
        List<Animal> lista = new ArrayList<>();
        try {
            String contenido = new String(Files.readAllBytes(Paths.get("src/main/resources/animales.json")));
            JSONArray jsonArray = new JSONArray(contenido);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                Animal animal = Animal.fromJSONObject(json);
                lista.add(animal);
            }
        } catch (IOException e) {
            System.err.println("Error al leer animales.json: " + e.getMessage());
        }
        return lista;
    }


    public Animal buscarAnimalPorId(String idBuscado) {
        List<Animal> animales = cargarAnimales();
        for (Animal a : animales) {
            if (a.getId().equalsIgnoreCase(idBuscado)) {
                return a;
            }
        }
        return null;
    }


    public void actualizarAnimal(Animal animalActualizado) {
        try {
            String ruta = "src/main/resources/animales.json";
            String contenido = new String(Files.readAllBytes(Paths.get(ruta)));
            JSONArray jsonAnimales = new JSONArray(contenido);

            boolean encontrado = false;
            for (int i = 0; i < jsonAnimales.length(); i++) {
                JSONObject json = jsonAnimales.getJSONObject(i);
                if (json.getString("id").equalsIgnoreCase(animalActualizado.getId())) {
                    jsonAnimales.put(i, animalActualizado.toJSONObject());
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                Files.write(Paths.get(ruta),
                        jsonAnimales.toString(2).getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Animal actualizado correctamente.");
            } else {
                System.out.println("Animal no encontrado para actualizar.");
            }

        } catch (IOException e) {
            System.err.println("Error al actualizar animal: " + e.getMessage());
        }
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