package Controlador;

import Modelo.Rescatista;
import Modelo.Persona;
import Datos.PersonaDA;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class RescatistaControlador {

    private PersonaDA personaDA;
    private Rescatista rescatistaActual; // Para mantener el rescatista logueado

    public RescatistaControlador(PersonaDA personaDA) {
        this.personaDA = personaDA;
    }

    //registrar un nuevo rescatista (sin parámetro de contraseña)
    public boolean registrarRescatista(String nombre, String rut, LocalDate fechaNacimiento,
                                       String direccion, String numeroTelefono,
                                       String correoElectronico) {
        //si el correo ya existe
        if (personaDA.buscarPersonaPorCorreo(correoElectronico).isPresent()) {
            System.out.println("Error: El correo electrónico ya está registrado.");
            return false;
        }
        Rescatista nuevoRescatista = new Rescatista(nombre, rut, fechaNacimiento, direccion,
                numeroTelefono, correoElectronico);
        personaDA.guardarPersona(nuevoRescatista);
        System.out.println("Rescatista registrado exitosamente con ID: " + nuevoRescatista.getId());
        return true;
    }

    // meodo para iniciar sesión solo verifica existencia de correo (ojo solo pra el rescatista)
    public boolean iniciarSesion(String correo) {
        Optional<Persona> personaOptional = personaDA.buscarPersonaPorCorreo(correo);

        if (personaOptional.isPresent()) {
            Persona persona = personaOptional.get();
            if (persona instanceof Rescatista) {
                this.rescatistaActual = (Rescatista) persona;
                return true;
            }
        }
        return false;
    }

        public void cerrarSesion() {
            this.rescatistaActual = null;
        }

        public Rescatista getRescatistaActual () {
            return rescatistaActual;
        }

        //para buscar un rescatista por ID
        public Rescatista buscarRescatistaPorId (String id){
            Persona persona = personaDA.buscarPersonaPorId(id);
            if (persona instanceof Rescatista) {
                return (Rescatista) persona;
            }
            return null;
        }

        // para listar todos los rescatistas
        public ArrayList<Rescatista> listarTodosRescatistas () {
            ArrayList<Rescatista> rescatistas = new ArrayList<>();
            for (Persona p : personaDA.cargarPersonas()) {
                if (p instanceof Rescatista) {
                    rescatistas.add((Rescatista) p);
                }
            }
            return rescatistas;
        }

        // para que el Rescatista actualice sus propios datos (sin contraseña)
        public boolean actualizarRescatista (String id, String nombre, String rut, LocalDate fechaNacimiento,
                String direccion, String numeroTelefono, String correoElectronico){
            if (rescatistaActual == null || !rescatistaActual.getId().equals(id)) {
                System.out.println("Error: No tiene permiso para actualizar este perfil o no ha iniciado sesión.");
                return false;
            }

            Rescatista rescatistaAActualizar = buscarRescatistaPorId(id);
            if (rescatistaAActualizar == null) {
                return false; // No se encontró el rescatista (aunque ya se validó el ID arriba)
            }

            // Actualizar campos comunes
            rescatistaAActualizar.setNombre(nombre);
            rescatistaAActualizar.setRut(rut);
            rescatistaAActualizar.setFechaNacimiento(fechaNacimiento);
            rescatistaAActualizar.setDireccion(direccion);
            rescatistaAActualizar.setNumeroTelefono(numeroTelefono);
            rescatistaAActualizar.setCorreoElectronico(correoElectronico);

            return personaDA.actualizarPersona(rescatistaAActualizar);
        }

}