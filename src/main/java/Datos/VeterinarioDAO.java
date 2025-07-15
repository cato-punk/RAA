package Datos;

import Modelo.Veterinario;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.function.Function;

public class VeterinarioDAO extends BaseDAO<Veterinario> {
    private static final Type VETERINARIO_LIST_TYPE = new TypeToken<java.util.ArrayList<Veterinario>>() {}.getType();
    private static final Function<Veterinario, String> ID_EXTRACTOR = Veterinario::getId;

    public VeterinarioDAO() {
        super("veterinarios.json", VETERINARIO_LIST_TYPE, ID_EXTRACTOR); // Nombre del archivo JSON
    }
}
