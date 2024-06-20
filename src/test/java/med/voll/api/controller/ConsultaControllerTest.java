package med.voll.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class ConsultaControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Deve retornar o código 400 quando as informações são inválidas")
    @WithMockUser
    void agendar_cenario1() throws Exception {
        var res = mvc.perform(post("/consultas")).andReturn().getResponse();
        assertThat(res.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}