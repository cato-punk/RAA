package Datos;

import Modelo.Adoptante;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.function.Function;

public class AdoptanteDAO extends BaseDAO<Adoptante> {
    private static final Type ADOPTANTE_LIST_TYPE = new TypeToken<java.util.ArrayList<Adoptante>>() {}.getType();
    private static final Function<Adoptante, String> ID_EXTRACTOR = Adoptante::getId;

    public AdoptanteDAO() {
        super("adoptantes.json", ADOPTANTE_LIST_TYPE, ID_EXTRACTOR); // Nombre del archivo JSON
    }
}