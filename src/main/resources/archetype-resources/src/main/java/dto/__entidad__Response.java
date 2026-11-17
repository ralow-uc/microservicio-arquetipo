package ${package}.dto;

import ${package}.model.${entidad};

import java.time.LocalDateTime;

/**
 * ${entidad} devuelto por la API. El controlador nunca expone la entidad JPA.
 */
public record ${entidad}Response(
        Long id,
        String nombre,
        String descripcion,
        boolean activo,
        LocalDateTime fechaCreacion) {

    public static ${entidad}Response desde(${entidad} entidad) {
        return new ${entidad}Response(
                entidad.getId(),
                entidad.getNombre(),
                entidad.getDescripcion(),
                entidad.isActivo(),
                entidad.getFechaCreacion());
    }
}
