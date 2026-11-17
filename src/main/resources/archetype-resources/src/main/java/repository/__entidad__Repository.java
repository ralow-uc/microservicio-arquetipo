package ${package}.repository;

import ${package}.model.${entidad};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA de ${entidad}. Al extender JpaRepository se obtiene el CRUD
 * contra Oracle sin escribir SQL; las consultas propias se derivan del nombre.
 */
@Repository
public interface ${entidad}Repository extends JpaRepository<${entidad}, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);

    List<${entidad}> findByActivoTrueOrderByNombreAsc();

    List<${entidad}> findAllByOrderByNombreAsc();
}
