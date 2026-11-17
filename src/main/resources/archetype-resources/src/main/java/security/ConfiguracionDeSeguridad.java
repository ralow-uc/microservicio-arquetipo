#set( $d = "$" )
package ${package}.security;

import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Configuracion de seguridad del microservicio (generada por el arquetipo).
 *
 * - Sin estado: no hay sesion ni CSRF, la identidad viaja en el JWT.
 * - Lectura publica de /api/${recurso}; el resto exige un token valido.
 * - Las reglas por rol se declaran en los controladores con @PreAuthorize.
 */
@Configuration
@EnableMethodSecurity
public class ConfiguracionDeSeguridad {

    private final FiltroDeAutenticacionJwt filtroJwt;
    private final List<String> origenesPermitidos;

    public ConfiguracionDeSeguridad(FiltroDeAutenticacionJwt filtroJwt,
                                    @Value("${d}{foro.cors.origenes}") String origenes) {
        this.filtroJwt = filtroJwt;
        this.origenesPermitidos = Arrays.stream(origenes.split(","))
                .map(String::trim).filter(o -> !o.isEmpty()).toList();
    }

    @Bean
    public SecurityFilterChain cadenaDeFiltros(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(peticiones -> peticiones
                        // El reenvio interno a /error tiene que pasar sin autenticar;
                        // si no, un 403 de @PreAuthorize termina convertido en 401.
                        .dispatcherTypeMatchers(DispatcherType.ERROR, DispatcherType.ASYNC).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/${recurso}/**").permitAll()
                        .requestMatchers("/api/estado", "/actuator/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(e -> e.authenticationEntryPoint(
                        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .headers(h -> h.frameOptions(f -> f.sameOrigin()))
                .addFilterBefore(filtroJwt, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * CORS: los origenes permitidos llegan por la variable FORO_CORS_ORIGENES
     * (separados por coma), para poder agregar la URL publica del frontend al
     * desplegar en la nube sin recompilar.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuracion = new CorsConfiguration();
        configuracion.setAllowedOriginPatterns(origenesPermitidos);
        configuracion.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuracion.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource fuente = new UrlBasedCorsConfigurationSource();
        fuente.registerCorsConfiguration("/**", configuracion);
        return fuente;
    }
}
