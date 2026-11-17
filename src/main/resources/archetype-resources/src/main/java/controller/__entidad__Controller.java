#set( $var = "${entidad.substring(0,1).toLowerCase()}${entidad.substring(1)}" )
package ${package}.controller;

import ${package}.dto.${entidad}Request;
import ${package}.dto.${entidad}Response;
import ${package}.service.${entidad}Service;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Controlador REST de ${entidad} generado por el arquetipo.
 *
 *   GET    /api/${recurso}          Lista (publico). ?soloActivos=true filtra.
 *   GET    /api/${recurso}/{id}     Detalle (publico).
 *   POST   /api/${recurso}          Crea. Solo ADMIN.
 *   PUT    /api/${recurso}/{id}     Actualiza. Solo ADMIN.
 *   DELETE /api/${recurso}/{id}     Elimina. Solo ADMIN.
 */
@RestController
@RequestMapping("/api/${recurso}")
public class ${entidad}Controller {

    private final ${entidad}Service ${var}Service;

    public ${entidad}Controller(${entidad}Service ${var}Service) {
        this.${var}Service = ${var}Service;
    }

    @GetMapping
    public ResponseEntity<List<${entidad}Response>> listar(
            @RequestParam(name = "soloActivos", defaultValue = "false") boolean soloActivos) {
        return ResponseEntity.ok(${var}Service.listar(soloActivos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<${entidad}Response> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(${var}Service.obtenerPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<${entidad}Response> crear(@Valid @RequestBody ${entidad}Request solicitud,
                                                    UriComponentsBuilder uriBuilder) {
        ${entidad}Response creado = ${var}Service.crear(solicitud);
        return ResponseEntity
                .created(uriBuilder.path("/api/${recurso}/{id}").buildAndExpand(creado.id()).toUri())
                .body(creado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<${entidad}Response> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody ${entidad}Request solicitud) {
        return ResponseEntity.ok(${var}Service.actualizar(id, solicitud));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ${var}Service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
