package Datos;

import Modelo.Animal;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.function.Function;

public class AnimalDAO extends BaseDAO<Animal> {
    private static final Type ANIMAL_LIST_TYPE = new TypeToken<java.util.ArrayList<Animal>>() {}.getType();
    private static final Function<Animal, String> ID_EXTRACTOR = Animal::getId;

    public AnimalDAO() {
        super("animales.json", ANIMAL_LIST_TYPE, ID_EXTRACTOR); // Nombre del archivo JSON
    }
}
