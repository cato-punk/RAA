package Datos;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileNotFoundException;

public class JsonUtil {

    public static JSONArray leerArchivoJson(String rutaArchivo) {
        try {
            // Corrige la ruta para Windows
            if (rutaArchivo.startsWith("/") && rutaArchivo.contains(":")) {
                rutaArchivo = rutaArchivo.substring(1);
            }

            Path path = Paths.get(rutaArchivo);
            if (!Files.exists(path)) {
                return new JSONArray();
            }
            String contenido = Files.readString(path);
            return new JSONArray(contenido);
        } catch (IOException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
            return new JSONArray();
        }
    }


    public static void escribirArchivoJson(String rutaArchivo, JSONArray jsonArray) {
        try (FileWriter file = new FileWriter(rutaArchivo)) {
            file.write(jsonArray.toString(4)); // toString(4) para un formato legible
            System.out.println("Datos guardados exitosamente en " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error de I/O al escribir en el archivo " + rutaArchivo + ": " + e.getMessage());
        }
    }
}