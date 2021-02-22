package br.com.zup.proposta.cartao;

import br.com.zup.proposta.cartao.bloqueio.Bloqueio;
import br.com.zup.proposta.cartao.clients.CartaoClient;
import br.com.zup.proposta.compartilhado.auditoria.OrigemDaRequisicao;
import br.com.zup.proposta.proposta.Proposta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

class CartaoControllerTest {
    private final CartaoRepository cartaoRepository = Mockito.mock(CartaoRepository.class);
    private final CartaoClient cartaoClient = Mockito.mock(CartaoClient.class);
    private final CartaoController controller = new CartaoController(cartaoRepository, cartaoClient);

    @Test
    void deveriaBloquearUmCartaoQueNaoEstaBloqueado(){
        Proposta proposta = Mockito.mock(Proposta.class);
        Cartao cartao = Mockito.spy(new Cartao(proposta, "1234-1234-1234-1234"));
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        Mockito.when(cartaoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cartao));

        ResponseEntity<?> response = controller.bloquear(Mockito.anyLong(), request);

        Mockito.verify(cartao).bloquear(Mockito.any(Bloqueio.class));
        Mockito.verify(cartaoRepository).save(cartao);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void naoDeveriaBloquearUmCartaoQueJaEstaBloqueado(){
        Proposta proposta = Mockito.mock(Proposta.class);
        Cartao cartao = Mockito.spy(new Cartao(proposta, "1234-1234-1234-1234"));
        Bloqueio bloqueio = new Bloqueio(cartao, new OrigemDaRequisicao("ip", "userAgent"));
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        cartao.bloquear(bloqueio);

        Mockito.when(cartaoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cartao));


        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
           controller.bloquear(Mockito.anyLong(), request);
        });

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getStatus());
    }

}