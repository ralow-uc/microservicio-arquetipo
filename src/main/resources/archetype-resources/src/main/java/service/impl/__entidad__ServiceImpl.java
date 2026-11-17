#set( $var = "${entidad.substring(0,1).toLowerCase()}${entidad.substring(1)}" )
package ${package}.service.impl;

import ${package}.dto.${entidad}Request;
import ${package}.dto.${entidad}Response;
import ${package}.exception.DatosDuplicadosException;
import ${package}.exception.RecursoNoEncontradoException;
import ${package}.model.${entidad};
import ${package}.repository.${entidad}Repository;
import ${package}.service.${entidad}Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Logica de negocio de ${entidad}: el nombre es unico sin distinguir mayusculas.
 */
@Service
public class ${entidad}ServiceImpl implements ${entidad}Service {

    private static final Logger log = LoggerFactory.getLogger(${entidad}ServiceImpl.class);

    private final ${entidad}Repository ${var}Repository;

    public ${entidad}ServiceImpl(${entidad}Repository ${var}Repository) {
        this.${var}Repository = ${var}Repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<${entidad}Response> listar(boolean soloActivos) {
        List<${entidad}> resultado = soloActivos
                ? ${var}Repository.findByActivoTrueOrderByNombreAsc()
                : ${var}Repository.findAllByOrderByNombreAsc();
        return resultado.stream().map(${entidad}Response::desde).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ${entidad}Response obtenerPorId(Long id) {
        return ${entidad}Response.desde(buscarOFallar(id));
    }

    @Override
    @Transactional
    public ${entidad}Response crear(${entidad}Request solicitud) {
        String nombre = solicitud.nombre().trim();
        if (${var}Repository.existsByNombreIgnoreCase(nombre)) {
            throw new DatosDuplicadosException("Ya existe un registro con el nombre '" + nombre + "'");
        }
        ${entidad} nuevo = new ${entidad}(nombre, solicitud.descripcion());
        nuevo.setActivo(solicitud.activo());
        ${entidad} guardado = ${var}Repository.save(nuevo);
        log.debug("${entidad} {} creado", guardado.getId());
        return ${entidad}Response.desde(guardado);
    }

    @Override
    @Transactional
    public ${entidad}Response actualizar(Long id, ${entidad}Request solicitud) {
        ${entidad} existente = buscarOFallar(id);
        String nombre = solicitud.nombre().trim();
        if (${var}Repository.existsByNombreIgnoreCaseAndIdNot(nombre, id)) {
            throw new DatosDuplicadosException("Ya existe un registro con el nombre '" + nombre + "'");
        }
        existente.setNombre(nombre);
        existente.setDescripcion(solicitud.descripcion());
        existente.setActivo(solicitud.activo());
        return ${entidad}Response.desde(${var}Repository.save(existente));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        ${var}Repository.delete(buscarOFallar(id));
        log.debug("${entidad} {} eliminado", id);
    }

    private ${entidad} buscarOFallar(Long id) {
        return ${var}Repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("${entidad}", id));
    }
}
