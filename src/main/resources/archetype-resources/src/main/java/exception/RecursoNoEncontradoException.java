package ${package}.exception;

/**
 * Se lanza cuando se pide un recurso que no existe. Se responde 404.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String recurso, Long id) {
        super("No existe " + recurso + " con id " + id);
    }

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
