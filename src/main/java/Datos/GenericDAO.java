package Datos;

import java.util.List;
import java.util.Optional; // Para manejar casos donde no se encuentra un objeto por ID

public interface GenericDAO<T> {

    List<T> cargarTodos();


    void guardarTodos(List<T> objetos);


    Optional<T> buscarPorId(String id);


    boolean agregar(T nuevoObjeto);


    boolean actualizar(T objetoActualizado);


    boolean eliminar(String id);
}