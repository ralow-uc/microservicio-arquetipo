#set( $h2 = "##" )
#set( $h3 = "###" )
# ${artifactId}

${descripcion}.

Generado con el arquetipo **`com.foro:microservicio-arquetipo:1.0.0`**.

| | |
|---|---|
| Puerto | `${puerto}` |
| Paquete | `${package}` |
| Entidad de ejemplo | `${entidad}` → tabla `${tabla}` → `/api/${recurso}` |

${h2} Estructura

```
src/main/java/.../
├── controller/   ${entidad}Controller, EstadoController
├── service/      contrato + impl con la logica de negocio
├── repository/   ${entidad}Repository (JPA)
├── model/        ${entidad}
├── dto/          ${entidad}Request, ${entidad}Response
├── security/     validacion del JWT de foro-usuarios
└── exception/    excepciones propias + manejador global (ProblemDetail)
scripts/          SQL de la tabla, ejecucion contra Oracle y pruebas
Dockerfile        imagen multi-etapa, el Wallet se monta al ejecutar
.github/          integracion continua
```

${h2} Ejecucion

```bash
chmod +x mvnw scripts/*.sh        # el arquetipo no conserva el bit de ejecucion
./mvnw spring-boot:run            # perfil local (H2), sin credenciales
./scripts/run.sh                  # perfil oracle, credenciales del Llavero
./scripts/test.sh                 # pruebas automatizadas
```

${h3} Docker

```bash
docker build -t ${artifactId} .
docker run -p ${puerto}:${puerto} -e SPRING_PROFILES_ACTIVE=local ${artifactId}
```

${h2} API de ejemplo

| Metodo | Ruta | Acceso |
|---|---|---|
| `GET` | `/api/${recurso}` | publico |
| `GET` | `/api/${recurso}/{id}` | publico |
| `POST` | `/api/${recurso}` | `ADMIN` |
| `PUT` | `/api/${recurso}/{id}` | `ADMIN` |
| `DELETE` | `/api/${recurso}/{id}` | `ADMIN` |
| `GET` | `/api/estado` | publico |

${h2} Ramas

Trunk Based Development: ver [CONTRIBUTING.md](CONTRIBUTING.md).
