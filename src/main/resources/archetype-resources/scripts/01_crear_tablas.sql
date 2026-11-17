-- ===========================================================================
--  Microservicio ${artifactId}
--  Creacion de SUS tablas en el esquema FORO de Oracle Autonomous Database.
--
--  Ejecutar con el usuario FORO:
--    ./scripts/sql.sh foro scripts/01_crear_tablas.sql
--
--  Generado por microservicio-arquetipo con la tabla de ejemplo ${tabla}.
--  Cada microservicio es dueno de sus tablas y no toca las de los demas.
-- ===========================================================================

CREATE TABLE ${tabla} (
    ID              NUMBER(19)      NOT NULL,
    NOMBRE          VARCHAR2(100)   NOT NULL,
    DESCRIPCION     VARCHAR2(400),
    ACTIVO          NUMBER(1)       DEFAULT 1 NOT NULL,
    FECHA_CREACION  TIMESTAMP       DEFAULT SYSTIMESTAMP NOT NULL,
    CONSTRAINT PK_${tabla}         PRIMARY KEY (ID),
    CONSTRAINT UK_${tabla}_NOMBRE  UNIQUE (NOMBRE),
    CONSTRAINT CK_${tabla}_ACTIVO  CHECK (ACTIVO IN (0, 1))
);

COMMENT ON TABLE ${tabla} IS '${descripcion}';

CREATE SEQUENCE ${tabla}_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
