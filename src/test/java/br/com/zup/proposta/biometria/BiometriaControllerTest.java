package br.com.zup.proposta.biometria;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.CartaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

class BiometriaControllerTest {

    private final CartaoRepository cartaoRepository = Mockito.mock(CartaoRepository.class);
    private final UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
    private final BiometriaController controller = new BiometriaController(cartaoRepository);
    private final Cartao cartao = Mockito.mock(Cartao.class);

    @Test
    void deveriaCadastrarBiometriaValida() {
        CriarBiometriaRequest request = new CriarBiometriaRequest("dGlhZ28=");
        Biometria novaBiometria = request.toBiometria();

        Mockito.when(cartaoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cartao));
        ResponseEntity<?> response = controller.criar(request, Mockito.anyLong(), uriBuilder);

        Mockito.verify(cartao).adicionarBiometria(novaBiometria);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void naoDeveriaCadastrarBiometriaInvalida() {
        CriarBiometriaRequest request = new CriarBiometriaRequest("NãoÉValido");
        Mockito.when(cartaoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cartao));

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            controller.criar(request, Mockito.anyLong(), uriBuilder);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void deveriaRetornar404CasoNaoEncontreCartao(){
        CriarBiometriaRequest request = new CriarBiometriaRequest("dGlhZ28=");
        Mockito.when(cartaoRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());


        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            controller.criar(request, Mockito.anyLong(), uriBuilder);
        });

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}