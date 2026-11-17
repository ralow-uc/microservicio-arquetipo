# microservicio-arquetipo

Arquetipo **Maven** propio para los microservicios del caso *Sistema de Foros
en Línea*. Genera en un solo comando un microservicio **Spring Boot 3** listo
para trabajar contra **Oracle Cloud**, con la misma estructura, seguridad y
convenciones que `foro-usuarios` y `foro-temas`.

**Asignatura:** Desarrollo Full Stack III (DSY2205) · Experiencia 2, Semana 5.

| | |
|---|---|
| Coordenadas | `com.foro:microservicio-arquetipo:1.0.0` |
| Java / Spring Boot | 21 LTS / 3.5.16 |
| Maven | 3.9.16 (wrapper incluido) |
| Plugin de arquetipos | `maven-archetype-plugin` 3.4.0 |

---

## 1. ¿Qué es y por qué existe?

Un **arquetipo** es una plantilla de proyecto: define la estructura de carpetas,
las dependencias y la configuración inicial para que cada microservicio nuevo
arranque igual que los demás. Es distinto de un **patrón de diseño**, que
resuelve un problema puntual *dentro* del código (ver
`foro-moderacion/docs/patrones-de-diseno.md`).

El arquetipo se construyó **extrayendo lo que ya compartían** los dos
microservicios de la Semana 2 y se usó para generar el tercero,
`foro-moderacion`:

| Lo que el arquetipo deja resuelto | Dónde queda en el proyecto generado |
|---|---|
| Arquitectura en capas `controller → service → repository → model`, DTOs separados de las entidades | `src/main/java/<paquete>/...` |
| Un CRUD de ejemplo completo y probado sobre una entidad configurable | `${entidad}Controller`, `${entidad}Service`, ... |
| Validación del **JWT** que emite `foro-usuarios` (HS256, secreto compartido), roles con `@PreAuthorize`, distinción **401 / 403** | `security/` |
| Manejador global de errores con `ProblemDetail` y detalle por campo en los `400` | `exception/` |
| Perfiles `local` (H2 modo Oracle) y `oracle` (Autonomous Database, `ddl-auto=validate`, esquema `FORO`) | `src/main/resources/` |
| Booleanos `NUMBER(1)` con `NumericBooleanConverter`, secuencias con `allocationSize = 1` | entidad de ejemplo |
| `GET /api/estado` que informa el motor real de base de datos | `controller/EstadoController` |
| Scripts para crear la tabla en Oracle, ejecutar SQL por JDBC con el Wallet y arrancar con credenciales del Llavero | `scripts/` |
| **Dockerfile** multi-etapa; el Wallet se monta al ejecutar, nunca va en la imagen | `Dockerfile`, `.dockerignore` |
| Integración continua: pruebas + `docker build` en cada push/PR a `main` | `.github/workflows/ci.yml` |
| Reglas de ramas **Trunk Based Development** | `CONTRIBUTING.md` |
| Pruebas: arranque del contexto y CRUD HTTP con tokens firmados en la prueba | `src/test/` |

## 2. Instalar el arquetipo en el repositorio local

```bash
./mvnw clean install
```

Queda en `~/.m2/repository/com/foro/microservicio-arquetipo/1.0.0/` y disponible
para cualquier proyecto del equipo.

## 3. Generar un microservicio

```bash
mvn archetype:generate \
  -DarchetypeCatalog=local \
  -DarchetypeGroupId=com.foro \
  -DarchetypeArtifactId=microservicio-arquetipo \
  -DarchetypeVersion=1.0.0 \
  -DgroupId=com.foro \
  -DartifactId=foro-moderacion \
  -Dpackage=com.foro.moderacion \
  -Dversion=1.0.0 \
  -DnombreClase=ForoModeracion \
  -Dentidad=Norma \
  -Drecurso=normas \
  -Dtabla=NORMA \
  -Dpuerto=8083 \
  -Ddescripcion="Microservicio de moderacion del foro" \
  -DinteractiveMode=false

cd foro-moderacion
chmod +x mvnw scripts/*.sh     # el empaquetado no conserva el bit de ejecución
./mvnw test                    # 5 pruebas en verde recién generado
./mvnw spring-boot:run         # perfil local, puerto 8083
```

Sin `-DinteractiveMode=false`, Maven pregunta cada propiedad.

### Propiedades

| Propiedad | Ejemplo | Para qué |
|---|---|---|
| `nombreClase` | `ForoModeracion` | Clase principal `ForoModeracionApplication` |
| `entidad` | `Norma` | Entidad de ejemplo con CRUD (singular, PascalCase) |
| `recurso` | `normas` | Ruta REST `/api/normas` |
| `tabla` | `NORMA` | Tabla y secuencia `NORMA_SEQ` en Oracle |
| `puerto` | `8083` | `server.port` y `EXPOSE` del Dockerfile (por omisión 8080) |
| `descripcion` | texto | `pom.xml`, README y comentario de la tabla |

## 4. Estructura del arquetipo

```
microservicio-arquetipo/
├── pom.xml                                   packaging maven-archetype
└── src/main/resources/
    ├── META-INF/maven/archetype-metadata.xml  propiedades y archivos que copia
    └── archetype-resources/                   la plantilla (plantillas Velocity)
        ├── pom.xml, Dockerfile, README.md, CONTRIBUTING.md
        ├── __gitignore__                      se renombra a .gitignore al generar
        ├── .github/workflows/ci.yml
        ├── scripts/
        └── src/{main,test}/java/
            ├── __nombreClase__Application.java
            ├── controller/__entidad__Controller.java
            ├── service/..., repository/..., model/..., dto/...
            └── security/, exception/
```

### Detalles de implementación que costaron

- **`__gitignore__`**: el empaquetador del plugin descarta los `.gitignore`
  aunque se desactiven los *default excludes*. Se guarda con otro nombre y la
  propiedad `gitignore` (por omisión `.gitignore`) lo renombra al generar.
- **`${...}` de Spring dentro de plantillas Velocity**: las propiedades como
  `${SPRING_PROFILES_ACTIVE:local}` se escriben como `${d}{...}` con
  `#set( $d = "$" )` al inicio del archivo, para que Velocity no intente
  resolverlas.
- **Encabezados Markdown**: en Velocity `##` es un comentario de línea, así que
  el README de la plantilla usa `${h2}` para los títulos de segundo nivel.

## 5. Versionado

Trunk Based Development (ver [CONTRIBUTING.md](CONTRIBUTING.md)). Las versiones
del arquetipo son etiquetas semánticas sobre `main`; un cambio en la plantilla
que rompa a los proyectos generados sube la versión mayor.

| Versión | Cambios |
|---|---|
| `v1.0.0` | Primera versión, extraída de `foro-usuarios` y `foro-temas` (S2). Usada para generar `foro-moderacion`. |
