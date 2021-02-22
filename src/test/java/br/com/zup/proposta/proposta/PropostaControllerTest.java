package br.com.zup.proposta.proposta;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

class PropostaControllerTest {

    private final PropostaRepository propostaRepository = Mockito.mock(PropostaRepository.class);
    private final AnalisaStatusService analisaStatusService = Mockito.mock(AnalisaStatusService.class);
    private final PropostaController controller = new PropostaController(propostaRepository, analisaStatusService);

    private final String documento = "12345678910";
    private final UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
    private final NovaPropostaRequest request = new NovaPropostaRequest(documento, "teste@teste.com.br", "Teste testson",
                                                                        new NovoEnderecoRequest("12345678", "Av 1", "Apto 2",
                                                                                                "123", "Centro", "Testown", "MG"),
                                                                        new BigDecimal("1000"));;
    @Test
    void deveriaCriarPropostaComDadosValidos(){
        ResponseEntity<?> response = controller.criar(request, uriBuilder);
        Proposta novaProposta = request.toProposta();

        Mockito.verify(propostaRepository, Mockito.atLeastOnce()).save(novaProposta);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void naoDeveriaCriarPropostaComDocumentoRepetido(){

        Mockito.when(propostaRepository.existsByDocumento(Mockito.anyString())).thenReturn(true);

        Assertions.assertThrows(ResponseStatusException.class, () -> {
           controller.criar(request, uriBuilder);
        });
    }
}