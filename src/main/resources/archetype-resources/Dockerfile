# ---------------------------------------------------------------------------
#  Imagen Docker de ${artifactId}  (generado con microservicio-arquetipo)
#
#  Etapa 1: compila con el Maven Wrapper del proyecto (no hace falta Maven
#           instalado en la maquina que construye).
#  Etapa 2: solo el JRE y el .jar, ejecutado con un usuario sin privilegios.
#
#  El Wallet de Oracle NO va dentro de la imagen: se monta al ejecutar en
#  /opt/oracle/wallet y las credenciales llegan por variables de entorno.
#
#    docker build -t ${artifactId} .
#    docker run -p ${puerto}:${puerto} -e SPRING_PROFILES_ACTIVE=local ${artifactId}
# ---------------------------------------------------------------------------
FROM eclipse-temurin:21-jdk AS compilacion
WORKDIR /app

# Primero solo lo necesario para descargar dependencias: esta capa queda en
# cache mientras no cambie el pom.xml.
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -q -B dependency:go-offline

COPY src/ src/
RUN ./mvnw -q -B package -DskipTests && cp target/*.jar app.jar


FROM eclipse-temurin:21-jre
WORKDIR /app

RUN groupadd --system app && useradd --system --gid app --home /app app \
    && mkdir -p /opt/oracle/wallet && chown app:app /opt/oracle/wallet
COPY --from=compilacion /app/app.jar app.jar

# Perfil por omision: Oracle. Para probar sin base de datos: SPRING_PROFILES_ACTIVE=local
ENV SPRING_PROFILES_ACTIVE=oracle \
    TNS_ADMIN=/opt/oracle/wallet \
    JAVA_OPTS="-XX:MaxRAMPercentage=75"

EXPOSE ${puerto}
USER app

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
