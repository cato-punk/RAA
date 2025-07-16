package Datos;

import Modelo.Admin;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.function.Function;

public class AdminDAO extends BaseDAO<Admin> {
    //  tipo para Gson para una lista de Admin
    private static final Type ADMIN_LIST_TYPE = new TypeToken<java.util.ArrayList<Admin>>() {}.getType();
    // Función para extraer el ID de un objeto Admin
    private static final Function<Admin, String> ID_EXTRACTOR = Admin::getId;

    public AdminDAO() {
        super("admin.json", ADMIN_LIST_TYPE, ID_EXTRACTOR); // Nombre del archivo JSON
    }
}