package Datos;

import Modelo.Rescatista;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.function.Function;

public class RescatistaDAO extends BaseDAO<Rescatista> {
    private static final Type RESCATISTA_LIST_TYPE = new TypeToken<java.util.ArrayList<Rescatista>>() {}.getType();
    private static final Function<Rescatista, String> ID_EXTRACTOR = Rescatista::getId;

    public RescatistaDAO() {
        super("rescatistas.json", RESCATISTA_LIST_TYPE, ID_EXTRACTOR); // Nombre del archivo JSON
    }
}
