package com.fourcatsdev.entitycrudrest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourcatsdev.entitycrudrest.modelo.Estudante;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EstudanteControleIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void shouldCreateEstudante() throws Exception {
        Estudante estudante = new Estudante(null, "Carlos", 20);

        mockMvc.perform(post("/estudantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estudante)))
                .andExpect(status().isCreated())
                .andDo(print());
    }


    @Test
    @Order(2)
    void shouldReturnEstudanteById() throws Exception {
        Long estudanteId = 1L;

        mockMvc.perform(get("/estudantes/{id}", estudanteId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carlos"))
                .andDo(print());
    }

    @Test
    @Order(3)
    void shouldReturnAllEstudantes() throws Exception {
        mockMvc.perform(get("/estudantes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andDo(print());
    }

    @Test
    @Order(4)
    void shouldUpdateEstudante() throws Exception {
        Estudante estudante = new Estudante(1L, "Carlos Silva", 25);

        mockMvc.perform(put("/estudantes/{id}", estudante.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estudante)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carlos Silva"))
                .andExpect(jsonPath("$.idade").value(25))
                .andDo(print());
    }

    @Test
    @Order(5)
    void shouldDeleteEstudante() throws Exception {
        Long estudanteId = 1L;

        mockMvc.perform(delete("/estudantes/{id}", estudanteId))
                .andExpect(status().isOk())
                .andExpect(content().string("Removido com sucesso."))
                .andDo(print());
    }
}
