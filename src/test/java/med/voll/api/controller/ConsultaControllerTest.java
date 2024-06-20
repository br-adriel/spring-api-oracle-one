package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    @MockBean
    private AgendaDeConsultas agendaDeConsultas;


    @Test
    @DisplayName("Deve retornar o código 400 quando as informações são inválidas")
    @WithMockUser
    void agendar_cenario1() throws Exception {
        var res = mvc.perform(post("/consultas")).andReturn().getResponse();
        assertThat(res.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar o código 200 quando as informações são válidas")
    @WithMockUser
    void agendar_cenario2() throws Exception {
        var dataAgendamento = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dadosAgendamento = new DadosAgendamentoConsulta(2L, 5L, dataAgendamento, especialidade);
        var jsonRequisicao = dadosAgendamentoConsultaJson.write(dadosAgendamento).getJson();

        var dadosDetalhamentoEsperado = new DadosDetalhamentoConsulta(null, 2L, 5L, dataAgendamento);
        var jsonRespostaEsperado = dadosDetalhamentoConsultaJson.write(dadosDetalhamentoEsperado).getJson();

        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamentoEsperado);


        var res = mvc
                .perform(
                        post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequisicao)
                )
                .andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(res.getContentAsString()).isEqualTo(jsonRespostaEsperado);
    }
}
