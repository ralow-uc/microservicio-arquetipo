#set( $var = "${entidad.substring(0,1).toLowerCase()}${entidad.substring(1)}" )
package ${package}.controller;

import ${package}.dto.${entidad}Request;
import ${package}.repository.${entidad}Repository;
import ${package}.security.GeneradorDeTokensDePrueba;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Pruebas HTTP del CRUD de ${entidad} generado por el arquetipo, incluidas las
 * reglas de autorizacion (401 sin token, 403 sin rol ADMIN).
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ${entidad}ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GeneradorDeTokensDePrueba tokens;

    @Autowired
    private ${entidad}Repository ${var}Repository;

    private String tokenUsuario;
    private String tokenAdmin;

    @BeforeEach
    void prepararBase() {
        ${var}Repository.deleteAll();
        tokenUsuario = tokens.para(3L, "feliks", "USUARIO");
        tokenAdmin = tokens.para(1L, "admin", "ADMIN");
    }

    private String json(Object cuerpo) throws Exception {
        return objectMapper.writeValueAsString(cuerpo);
    }

    @Test
    @DisplayName("CRUD completo con ADMIN: crear, leer, actualizar y eliminar")
    void crudCompletoConAdmin() throws Exception {
        String respuesta = mockMvc.perform(post("/api/${recurso}").header("Authorization", tokenAdmin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(new ${entidad}Request("Ejemplo", "Registro de prueba", true))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Ejemplo"))
                .andReturn().getResponse().getContentAsString();
        long id = objectMapper.readTree(respuesta).get("id").asLong();

        mockMvc.perform(get("/api/${recurso}/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activo").value(true));

        mockMvc.perform(put("/api/${recurso}/{id}", id).header("Authorization", tokenAdmin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(new ${entidad}Request("Ejemplo editado", null, false))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ejemplo editado"));

        mockMvc.perform(delete("/api/${recurso}/{id}", id).header("Authorization", tokenAdmin))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/${recurso}/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Escribir exige token (401) y rol ADMIN (403)")
    void escribirExigeAdmin() throws Exception {
        String cuerpo = json(new ${entidad}Request("Ejemplo", null, true));

        mockMvc.perform(post("/api/${recurso}").contentType(MediaType.APPLICATION_JSON).content(cuerpo))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/api/${recurso}").header("Authorization", tokenUsuario)
                        .contentType(MediaType.APPLICATION_JSON).content(cuerpo))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Datos invalidos responden 400 con el detalle por campo")
    void datosInvalidos() throws Exception {
        mockMvc.perform(post("/api/${recurso}").header("Authorization", tokenAdmin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(new ${entidad}Request("x", null, null))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.nombre").exists())
                .andExpect(jsonPath("$.errores.activo").exists());
    }

    @Test
    @DisplayName("GET /api/estado es publico")
    void estadoEsPublico() throws Exception {
        mockMvc.perform(get("/api/estado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("OK"));
    }
}
