#set( $var = "${entidad.substring(0,1).toLowerCase()}${entidad.substring(1)}" )
#set( $sec = "${var}_seq_gen" )
package ${package}.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import org.hibernate.type.NumericBooleanConverter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad JPA de ejemplo generada por el arquetipo. Se mapea a la tabla
 * ${tabla} (ver scripts/01_crear_tablas.sql).
 *
 * Convenciones que el arquetipo deja fijadas para todas las entidades:
 *   - ID por secuencia de Oracle con allocationSize = 1.
 *   - Booleanos como NUMBER(1) con NumericBooleanConverter, para que el mapeo
 *     valide contra Oracle 23 (que tiene un BOOLEAN nativo distinto).
 *   - La fecha de creacion la fija la aplicacion, no el cliente.
 */
@Entity
@Table(name = "${tabla}")
public class ${entidad} {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "${sec}")
    @SequenceGenerator(name = "${sec}", sequenceName = "${tabla}_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 400)
    private String descripcion;

    @Column(name = "ACTIVO", nullable = false)
    @Convert(converter = NumericBooleanConverter.class)
    private boolean activo = true;

    @Column(name = "FECHA_CREACION", nullable = false)
    private LocalDateTime fechaCreacion;

    protected ${entidad}() {
        // Constructor requerido por JPA.
    }

    public ${entidad}(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    @PrePersist
    void alInsertar() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ${entidad} otro)) {
            return false;
        }
        return id != null && id.equals(otro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
