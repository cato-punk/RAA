package Datos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class BaseDAO<T> implements GenericDAO<T> {
    protected final String filePath;
    protected final Gson gson;
    protected final Type type; // Tipo para Gson (ej. TypeToken<List<Persona>>().getType())
    protected final Function<T, String> idExtractor; // Función para extraer el ID de un objeto T

    public BaseDAO(String fileName, Type type, Function<T, String> idExtractor) {
        this.filePath = fileName; // El archivo estará en la raíz del proyecto
        this.gson = new GsonBuilder().setPrettyPrinting().create(); // Formato legible
        this.type = type;
        this.idExtractor = idExtractor;
        inicializarArchivo(); // Asegurarse de que el archivo exista al inicializar
    }

    private void inicializarArchivo() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                // Escribir una lista JSON vacía si el archivo es nuevo
                try (FileWriter writer = new FileWriter(file)) {
                    gson.toJson(new ArrayList<>(), writer);
                }
                System.out.println("Archivo JSON creado: " + filePath);
            } catch (IOException e) {
                System.err.println("Error al crear el archivo JSON: " + filePath + " - " + e.getMessage());
            }
        }
    }

    @Override
    public List<T> cargarTodos() {
        try (FileReader reader = new FileReader(filePath)) {
            List<T> objetos = gson.fromJson(reader, type);
            return objetos != null ? objetos : new ArrayList<>();
        } catch (IOException e) {

            // Para mantener la robustez, se devuelve una lista vacía y se registra el error.
            System.err.println("Error al cargar datos desde " + filePath + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void guardarTodos(List<T> objetos) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(objetos, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar datos en " + filePath + ": " + e.getMessage());
        }
    }

    @Override
    public Optional<T> buscarPorId(String id) {
        return cargarTodos().stream()
                .filter(obj -> idExtractor.apply(obj).equals(id))
                .findFirst();
    }

    @Override
    public boolean agregar(T nuevoObjeto) {
        List<T> objetos = cargarTodos();
        String nuevoId = idExtractor.apply(nuevoObjeto);
        if (objetos.stream().anyMatch(obj -> idExtractor.apply(obj).equals(nuevoId))) {
            return false; // Ya existe un objeto con el mismo ID
        }
        objetos.add(nuevoObjeto);
        guardarTodos(objetos);
        return true;
    }

    @Override
    public boolean actualizar(T objetoActualizado) {
        List<T> objetos = cargarTodos();
        String idActualizado = idExtractor.apply(objetoActualizado);
        for (int i = 0; i < objetos.size(); i++) {
            if (idExtractor.apply(objetos.get(i)).equals(idActualizado)) {
                objetos.set(i, objetoActualizado);
                guardarTodos(objetos);
                return true;
            }
        }
        return false; // Objeto no encontrado para actualizar
    }

    @Override
    public boolean eliminar(String id) {
        List<T> objetos = cargarTodos();
        boolean removido = objetos.removeIf(obj -> idExtractor.apply(obj).equals(id));
        if (removido) {
            guardarTodos(objetos);
        }
        return removido;
    }
}