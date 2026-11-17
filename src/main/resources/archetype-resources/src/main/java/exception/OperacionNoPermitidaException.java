package ${package}.exception;

/**
 * Se lanza cuando un usuario autenticado intenta algo que sus privilegios no
 * le permiten. Se responde 403.
 */
public class OperacionNoPermitidaException extends RuntimeException {

    public OperacionNoPermitidaException(String mensaje) {
        super(mensaje);
    }
}
