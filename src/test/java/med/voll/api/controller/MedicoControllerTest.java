package med.voll.api.controller;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.*;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJacksonTester;

    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJacksonTester;

    @MockBean
    private MedicoRepository medicoRepository;

    @Test
    @DisplayName("Dados inválidos retornam código 400")
    @WithMockUser
    void cadastrar_cenario1() throws Exception {
        var res = mvc.perform(post("/medicos")).andReturn().getResponse();
        assertThat(res.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Dados válidos retornam código 201 e objeto criado")
    @WithMockUser
    void cadastrar_cenario2() throws Exception {
        var nome = "Medico";
        var crm = "123456";
        var telefone = "84912345678";
        var especialidade = Especialidade.CARDIOLOGIA;

        var dadosEndereco = new DadosEndereco("Rua do Limoeiro", "Presidente Bidu", "12300000", "Mauricio de souza", "SP", "", "123");
        var endereco = new Endereco(dadosEndereco);

        var dadosCadastro = new DadosCadastroMedico(nome, "medico@medico.com", telefone, crm, especialidade, dadosEndereco);
        var requestBody = dadosCadastroMedicoJacksonTester.write(dadosCadastro);

        var dadosRetornados = new DadosDetalhamentoMedico(null, nome, crm, telefone, especialidade, endereco);
        var responseBody = dadosDetalhamentoMedicoJacksonTester.write(dadosRetornados).getJson();

        when(medicoRepository.save(any())).thenReturn(new Medico(dadosCadastro));

        var res = mvc.perform(
                post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.getJson())
        ).andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(res.getContentAsString()).isEqualTo(responseBody);
    }
}