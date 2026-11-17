-- ===========================================================================
--  Microservicio ${artifactId}
--  Elimina SUS objetos del esquema FORO para poder recrearlos desde cero.
-- ===========================================================================
DROP TABLE ${tabla} CASCADE CONSTRAINTS;
DROP SEQUENCE ${tabla}_SEQ;
