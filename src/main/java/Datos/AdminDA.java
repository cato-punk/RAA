package Datos;

import Modelo.Admin;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class AdminDA {

    private static final String FILE_PATH = "admins.json"; //para guardar los administradores
    private ObjectMapper objectMapper;

    public AdminDA() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        inicializarArchivo(); // el archivo JSON exista al inicio
    }

    private void inicializarArchivo() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            try {
                // crear un archivo vacío con una lista JSON vacía para evitar errores de lectura
                objectMapper.writeValue(file, new ArrayList<Admin>());
                System.out.println("Archivo de administradores inicializado: " + FILE_PATH);
            } catch (IOException e) {
                System.err.println("Error al inicializar el archivo de administradores: " + e.getMessage());
                throw new RuntimeException("No se pudo inicializar el archivo de administradores", e);
            }
        }
    }

    public void guardarAdmins(ArrayList<Admin> admins) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), admins);
            // System.out.println("Administradores guardados exitosamente en " + FILE_PATH); //
        } catch (IOException e) {
            System.err.println("Error al guardar administradores: " + e.getMessage());
            throw new RuntimeException("No se pudieron guardar los administradores", e);
        }
    }

    public ArrayList<Admin> cargarAdmins() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try {
            Admin[] adminsArray = objectMapper.readValue(file, Admin[].class);
            return new ArrayList<>(Arrays.asList(adminsArray));
        } catch (IOException e) {
            System.err.println("Error al cargar administradores: " + e.getMessage());
            throw new RuntimeException("No se pudieron cargar los administradores", e);
        }
    }

    //para buscar un administrador por ID
    public Admin buscarAdminPorId(String id) {
        ArrayList<Admin> admins = cargarAdmins();
        for (Admin admin : admins) {
            if (admin.getId().equals(id)) {
                return admin;
            }
        }
        return null;
    }

    //para buscar un administrador por correo (útil para login)
    public Optional<Admin> buscarAdminPorCorreo(String correo) {
        ArrayList<Admin> admins = cargarAdmins();
        for (Admin admin : admins) {
            if (admin.getCorreoElectronico().equalsIgnoreCase(correo)) {
                return Optional.of(admin);
            }
        }
        return Optional.empty();
    }

    //para agregar un nuevo administrador
    public void agregarAdmin(Admin nuevoAdmin) {
        ArrayList<Admin> admins = cargarAdmins();
        admins.add(nuevoAdmin);
        guardarAdmins(admins);
    }

    //para actualizar un administrador, devolviendo boolean
    public boolean actualizarAdmin(Admin adminActualizado) {
        ArrayList<Admin> admins = cargarAdmins();
        boolean encontrado = false;
        for (int i = 0; i < admins.size(); i++) {
            if (admins.get(i).getId().equals(adminActualizado.getId())) {
                admins.set(i, adminActualizado);
                encontrado = true;
                break;
            }
        }
        if (encontrado) {
            guardarAdmins(admins);
            return true;
        } else {
            System.err.println("Error: Administrador con ID " + adminActualizado.getId() + " no encontrado para actualizar.");
            return false; // No encontrado o no actualizado
        }
    }

    //para eliminar un administrador
    public boolean eliminarAdmin(String id) {
        ArrayList<Admin> admins = cargarAdmins();
        boolean removido = admins.removeIf(admin -> admin.getId().equals(id));
        if (removido) {
            guardarAdmins(admins);
            System.out.println("Administrador con ID " + id + " eliminado exitosamente.");
        } else {
            System.out.println("No se encontró el administrador con ID " + id + " para eliminar.");
        }
        return removido;
    }
}