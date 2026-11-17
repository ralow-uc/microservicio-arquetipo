package ${package}.exception;

/**
 * Se lanza cuando un dato que debe ser unico ya existe. Se responde 409.
 */
public class DatosDuplicadosException extends RuntimeException {

    public DatosDuplicadosException(String mensaje) {
        super(mensaje);
    }
}
