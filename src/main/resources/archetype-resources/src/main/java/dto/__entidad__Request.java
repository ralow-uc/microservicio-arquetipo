package ${package}.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Datos de entrada para crear o actualizar un ${entidad}.
 * Las reglas de formato se validan aqui con Bean Validation.
 */
public record ${entidad}Request(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombre,

        @Size(max = 400, message = "La descripcion no puede superar los 400 caracteres")
        String descripcion,

        @NotNull(message = "Indica si esta activo")
        Boolean activo) {
}
