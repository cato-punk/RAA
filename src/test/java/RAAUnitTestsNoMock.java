import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import Modelo.*;
import Datos.*;
import Controlador.*;

public class RAAUnitTestsNoMock {

    private AdminControlador adminControlador;
    private AdoptanteControlador adoptanteControlador;
    private RescatistaControlador rescatistaControlador;


    // Lista de nombres de archivos JSON que los DAOs utilizan
    private static final String[] DAO_FILE_NAMES = {
            "admins.json",
            "veterinarios.json",
            "adoptantes.json",
            "rescatistas.json",
            "animales.json"
    };

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @BeforeEach
    void setUp() {
        // 1. Limpiar o asegurar que los archivos JSON estén vacíos para cada test
        for (String fileName : DAO_FILE_NAMES) {
            try {
                File file = new File(fileName);
                if (file.exists()) {
                    // Vaciar el archivo
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write("[]"); // Escribir un array JSON vacío
                    }
                } else {
                    // Si el archivo no existe, crearlo vacío.
                    // Esto se alinea con la gestión de errores de "crear archivo vacío si no existe"
                    file.createNewFile();
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write("[]");
                    }
                }
            } catch (IOException e) {
                fail("No se pudo preparar el archivo " + fileName + " para la prueba: " + e.getMessage());
            }
        }

        // 2. Instanciar los controladores (que a su vez instanciarán los DAOs reales)
        adminControlador = new AdminControlador();
        adoptanteControlador = new AdoptanteControlador();
        rescatistaControlador = new RescatistaControlador();

    }

    /**
     * Helper para escribir datos directamente en un archivo JSON.
     * Útil para pre-cargar datos para pruebas.
     */
    private <T> void writeDataToFile(String fileName, List<T> dataList) {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(dataList, writer);
        } catch (IOException e) {
            fail("No se pudo escribir en el archivo " + fileName + ": " + e.getMessage());
        }
    }

    /**
     * Helper para leer datos directamente de un archivo JSON.
     */
    private <T> List<T> readDataFromFile(String fileName, Class<T[]> type) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(fileName)));
            if (jsonContent.trim().isEmpty() || jsonContent.trim().equals("null")) {
                return new ArrayList<>(); // Archivo vacío o nulo
            }
            return Arrays.asList(gson.fromJson(jsonContent, type));
        } catch (IOException e) {
            // Si el archivo no existe o hay un problema de lectura, se considera vacío
            return new ArrayList<>();
        }
    }


    // --- Caso de Prueba 1: Registro Exitoso de Veterinario (AdminControlador) ---
    // Prueba para Fuente de Error 5 (Falta de Permisos o Lógica de Negocio Violada) - indirectamente
    // Y Fuente de Error 1 (Archivos JSON) - verificando la persistencia
    @Test
    @DisplayName("CP1: Debería registrar un veterinario exitosamente y persistirlo en el archivo")
    void testRegistrarVeterinarioExitoso() {
        String idVeterinario = adminControlador.registrarVeterinario(
                "Dr. Vet", "12.345.678-9", "Av. Principal 123", "912345678",
                "vet@clinica.com", "Consulta General", "VET-001");

        assertNotNull(idVeterinario, "El ID del veterinario no debería ser nulo");
        assertFalse(idVeterinario.isEmpty(), "El ID del veterinario no debería estar vacío");

        // Verificar la persistencia: leer directamente el archivo veterinarios.json
        List<Veterinario> veterinariosEnArchivo = readDataFromFile("veterinarios.json", Veterinario[].class);
        assertFalse(veterinariosEnArchivo.isEmpty(), "El archivo de veterinarios no debería estar vacío");
        assertEquals(1, veterinariosEnArchivo.size(), "Debería haber un veterinario en el archivo");
        assertEquals(idVeterinario, veterinariosEnArchivo.get(0).getId(), "El ID del veterinario en archivo debe coincidir");
        assertEquals("Dr. Vet", veterinariosEnArchivo.get(0).getNombre(), "El nombre del veterinario en archivo debe coincidir");
    }

    // --- Caso de Prueba 2: Inicio de Sesión de Admin con Credenciales Incorrectas (AdminControlador) ---
    // Prueba para Fuente de Error 4 (Credenciales de Inicio de Sesión Incorrectas)
    @Test
    @DisplayName("CP2: Debería fallar el inicio de sesión del admin con credenciales incorrectas")
    void testIniciarSesionAdminCredencialesIncorrectas() {
        // Pre-cargar un admin válido en el archivo admins.json
        // CONSTRUCTOR CORREGIDO DE ADMIN  Y JSON DE EJEMPLO
        Admin adminExistente = new Admin("admin1", "Super Admin", "11.222.333-4", "Calle Falsa 123", "987654321", "admin@raa.com", "contrasena123");
        writeDataToFile("admins.json", Arrays.asList(adminExistente));

        // Intentar iniciar sesión con contraseña incorrecta
        Admin adminLogueado = adminControlador.iniciarSesion("admin@raa.com", "admin1", "contrasena_incorrecta");
        assertNull(adminLogueado, "El admin logueado debería ser nulo para contraseña incorrecta");

        // Intentar iniciar sesión con ID inexistente
        adminLogueado = adminControlador.iniciarSesion("otro@email.com", "admin2", "cualquiercosa");
        assertNull(adminLogueado, "El admin logueado debería ser nulo para ID inexistente");
    }

    // --- Caso de Prueba 3: Adopción de Animal Exitoso (AdoptanteControlador) ---
    // Prueba para Fuente de Error 5 (Lógica de Negocio Violada) - verificación de estado
    @Test
    @DisplayName("CP3: Debería permitir la adopción de un animal disponible por un adoptante con permiso y actualizar archivos")
    void testAdoptarAnimalExitoso() {
        // Pre-cargar un adoptante con permiso y un animal disponible en los archivos
        Adoptante adoptante = new Adoptante("adoptante1", "Laura Perez", "11.111.111-1", "Calle Falsa", "987654321", "laura@email.com", "perros", "casa");
        adoptante.setPermisoAdopcion(true); // Asegurarse de que tiene permiso
        writeDataToFile("adoptantes.json", Arrays.asList(adoptante));

        // CONSTRUCTOR DE ANIMAL CORREGIDO
        Animal animal = new Animal(
                "animal1",       // id
                "Labrador",      // raza
                "Perro",         // especie
                "Macho",         // sexo
                "Sano",          // estadoSalud
                "Parque Central",// lugarEncuentro (ejemplo)
                "10:30",         // horaRescate (ejemplo)
                "15-07-2025",    // fechaRescate (ejemplo)
                "3 años",        // edadAproximada
                "Pedro Rescatista", // nombreRescatistaEncontro (ejemplo)
                "resc123",       // idRescatistaEncontro (ejemplo)
                Animal.EstadoAdopcion.EN_ADOPCION, // estadoAdopcion (Enum)
                ""               // veterinarioAtiendeId (ejemplo, puede ser vacío si no hay asignado)
        );
        writeDataToFile("animales.json", Arrays.asList(animal));

        // Ejecutar la operación de adopción
        boolean resultadoAdopcion = adoptanteControlador.adoptarAnimal("adoptante1", "animal1");
        assertTrue(resultadoAdopcion, "La adopción debería ser exitosa");

        // Verificar el estado actualizado en los archivos JSON
        List<Animal> animalesEnArchivo = readDataFromFile("animales.json", Animal[].class);
        assertEquals(1, animalesEnArchivo.size(), "Debería haber un animal en el archivo");
        assertEquals(Animal.EstadoAdopcion.ADOPTADO, animalesEnArchivo.get(0).getEstadoAdopcion(), "El estado del animal debe ser ADOPTADO");

        List<Adoptante> adoptantesEnArchivo = readDataFromFile("adoptantes.json", Adoptante[].class);
        assertEquals(1, adoptantesEnArchivo.size(), "Debería haber un adoptante en el archivo");

    }

    // --- Caso de Prueba 4: Informe de Rescate con Formato de Fecha/Hora Inválido (RescatistaControlador) ---
    // Prueba para Fuente de Error 2 (Entrada de Usuario Inválida)
    @Test
    @DisplayName("CP4: No debería registrar un rescate con formato de hora inválido")
    void testInformarRescateHoraInvalida() {
        // Pre-cargar un rescatista (no es estrictamente necesario para este test de validación,
        // pero se usa como parámetro del metodo)
        Rescatista rescatista = new Rescatista("resc1", "Juan Rescatista", "22.222.222-2", "Calle Falsa 456", "998877665", "juan@rescate.com");
        writeDataToFile("rescatistas.json", Arrays.asList(rescatista));

        // Intentar informar un rescate con una hora inválida
        String animalIdGenerado = rescatistaControlador.informarRescate(
                rescatista, "Mestizo", "Gato", "Hembra", "Bien", "Jardín",
                "25:00", // Hora inválida
                "16-07-2025", "1 año");

        assertNull(animalIdGenerado, "El ID del animal debería ser nulo si la hora es inválida");

        // Verificar que no se añadió ningún animal al archivo
        List<Animal> animalesEnArchivo = readDataFromFile("animales.json", Animal[].class);
        assertTrue(animalesEnArchivo.isEmpty(), "No debería haber animales en el archivo después de un rescate fallido por validación");
    }

    // --- Caso de Prueba 5: Eliminación de Entidad No Existente (AdminControlador) ---
    // Prueba para Fuente de Error 3 (ID No Encontrado)

    @Test
    @DisplayName("CP5: Debería retornar false al eliminar una entidad con ID inexistente y no modificar archivos")
    void testEliminarEntidadIdNoExistente() {
        // Pre-cargar algunos datos para asegurar que los archivos no estén vacíos inicialmente,
        // pero que el ID a eliminar no exista.
        // CONSTRUCTOR DE ADMIN
        Admin existingAdmin = new Admin("adminTest", "Test Admin", "00.000.000-0", "Direccion Test 1", "000000000", "test@admin.com", "pass123");
        writeDataToFile("admins.json", Arrays.asList(existingAdmin));

        // Intentar eliminar un ID que definitivamente no existe
        boolean eliminacionExitosa = adminControlador.eliminarEntidad("Admin", "ID_INEXISTENTE_XYZ");
        assertFalse(eliminacionExitosa, "La eliminación de una entidad no existente debería retornar false");

        // Verificar que el archivo de admins no ha cambiado
        List<Admin> adminsEnArchivo = readDataFromFile("admins.json", Admin[].class);
        assertEquals(1, adminsEnArchivo.size(), "El número de admins en el archivo no debería cambiar");
        assertEquals("adminTest", adminsEnArchivo.get(0).getId(), "El admin original debería seguir en el archivo");

        // Intentar eliminar de otro tipo de entidad con ID inexistente
        boolean animalEliminado = adminControlador.eliminarEntidad("Animal", "ANIMAL_XYZ_NO_EXISTE");
        assertFalse(animalEliminado, "La eliminación de un animal no existente debería retornar false");
        List<Animal> animalesEnArchivo = readDataFromFile("animales.json", Animal[].class);
        assertTrue(animalesEnArchivo.isEmpty(), "El archivo de animales debería seguir vacío");
    }
}
