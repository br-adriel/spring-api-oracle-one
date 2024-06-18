package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class Validador24hAntecedencia implements ValidadorCancelamentoDeConsulta {
    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        var agora = LocalDateTime.now();
        var horarioConsulta = repository.getReferenceById(dados.idConsulta());
        var horasDeAntecedencia = Duration.between(agora, horarioConsulta.getData()).toHours();

        if (horasDeAntecedencia < 24) {
            throw new ValidacaoException("Cancelamentos só podem ser feitos com no mínimo 24h de antecedência");
        }
    }
}
