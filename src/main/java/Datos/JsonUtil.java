package Datos;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileNotFoundException;

public class JsonUtil {

    public static JSONArray leerArchivoJson(String rutaArchivo) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
            return new JSONArray(contenido);
        } catch (FileNotFoundException e) {
            System.out.println("El archivo " + rutaArchivo + " no se ha encontrado. Se va a crear uno nuevo si se guardan datos.");
            return new JSONArray(); // retorna un array vacio si el archivo no existe
        } catch (IOException e) {
            System.err.println("Error de I/O al leer el archivo " + rutaArchivo + ": " + e.getMessage());
            return new JSONArray();
        } catch (JSONException e) {
            System.err.println("Error al parsear el contenido JSON del archivo " + rutaArchivo + ": " + e.getMessage());
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
