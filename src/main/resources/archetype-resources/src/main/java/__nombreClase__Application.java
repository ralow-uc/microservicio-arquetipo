package ${package};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ${descripcion}.
 *
 * Generado con el arquetipo com.foro:microservicio-arquetipo. La estructura en
 * capas (controller, service, repository, model, dto) y la seguridad por JWT
 * son las mismas en todos los microservicios del foro.
 */
@SpringBootApplication
public class ${nombreClase}Application {

    public static void main(String[] args) {
        SpringApplication.run(${nombreClase}Application.class, args);
    }
}
