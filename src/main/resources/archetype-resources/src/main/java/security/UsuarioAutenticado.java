package ${package}.security;

import java.util.List;

/**
 * Identidad del usuario que viene dentro del JWT emitido por foro-usuarios.
 *
 * Este microservicio no consulta la tabla USUARIO ni llama al servicio de
 * usuarios: todo lo que necesita saber de quien hace la peticion viaja firmado
 * en el token.
 */
public record UsuarioAutenticado(Long id, String nombreUsuario, List<String> roles) {

    public static final String ROL_ADMIN = "ADMIN";
    public static final String ROL_MODERADOR = "MODERADOR";

    /** Un ADMIN puede intervenir sobre cualquier recurso. */
    public boolean esAdmin() {
        return roles != null && roles.contains(ROL_ADMIN);
    }

    /** Un MODERADOR o un ADMIN pueden moderar contenido. */
    public boolean puedeModerar() {
        return esAdmin() || (roles != null && roles.contains(ROL_MODERADOR));
    }
}
