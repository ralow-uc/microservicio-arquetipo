package ${package}.service;

import ${package}.dto.${entidad}Request;
import ${package}.dto.${entidad}Response;

import java.util.List;

/**
 * Contrato de la capa de servicio de ${entidad}. El controlador depende de esta
 * interfaz y no de la implementacion (inversion de dependencias).
 */
public interface ${entidad}Service {

    List<${entidad}Response> listar(boolean soloActivos);

    ${entidad}Response obtenerPorId(Long id);

    ${entidad}Response crear(${entidad}Request solicitud);

    ${entidad}Response actualizar(Long id, ${entidad}Request solicitud);

    void eliminar(Long id);
}
