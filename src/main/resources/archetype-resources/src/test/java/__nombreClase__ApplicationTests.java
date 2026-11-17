package ${package};

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Comprueba que el contexto de Spring arranca con JPA, seguridad y controladores.
 */
@SpringBootTest
@ActiveProfiles("test")
class ${nombreClase}ApplicationTests {

    @Test
    @DisplayName("El contexto de la aplicacion se levanta sin errores")
    void elContextoSeLevanta() {
        // El propio arranque del contexto es la verificacion.
    }
}
