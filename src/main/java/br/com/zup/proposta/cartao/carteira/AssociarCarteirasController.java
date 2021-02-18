package br.com.zup.proposta.cartao.carteira;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.CartaoRepository;
import br.com.zup.proposta.cartao.clients.CartaoClient;
import br.com.zup.proposta.cartao.clients.dtos.AssociarCarteiraClientRequest;
import br.com.zup.proposta.compartilhado.utils.Ofuscador;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/cartao")
public class AssociarCarteirasController {
    private static final Logger logger = LoggerFactory.getLogger(AssociarCarteirasController.class);
    private final CartaoRepository cartaoRepository;
    private final CartaoClient cartaoClient;

    public AssociarCarteirasController(CartaoRepository cartaoRepository,
                                       CartaoClient cartaoClient) {
        this.cartaoRepository = cartaoRepository;
        this.cartaoClient = cartaoClient;
    }

    @PostMapping("/{id}/carteira/paypal")
    public ResponseEntity<?> associarPaypal(@PathVariable Long id, @RequestBody @Valid AssociarCarteiraRequest request){
        associarCarteira(id, request, TipoCarteira.PAYPAL);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/carteira/samsung-pay")
    public ResponseEntity<?> associarSamsungPay(@PathVariable Long id,
                                             @RequestBody @Valid AssociarCarteiraRequest request){
        associarCarteira(id, request, TipoCarteira.SAMSUNG_PAY);
        return ResponseEntity.ok().build();
    }

    private void associarCarteira(Long id, AssociarCarteiraRequest request, TipoCarteira tipoCarteira) {
        Cartao cartao = cartaoRepository.findById(id)
                                        .orElseThrow(() -> {
                                            logger.error("Busca pelo cartão {} falhou.", id);
                                            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                              "Cartão não encontrado");
                                        });

        Carteira carteira = request.toCarteira(cartao, tipoCarteira);

        if(cartao.temCarteira(carteira)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "O cartão já tem uma carteira da " +
                    "categoria " + tipoCarteira);
        }

        try {
            cartaoClient.associarCarteira(cartao.getNumero(), new AssociarCarteiraClientRequest(request.getEmail(),
                                                                                                String.valueOf(tipoCarteira)));
            cartao.associarCarteira(carteira);
            cartaoRepository.save(cartao);
            logger.info("Carteira do tipo {} cadastrada para o cartão {}", tipoCarteira,
                        Ofuscador.ofuscar(cartao.getNumero(), 4));
        } catch (FeignException e) {
            logger.error("Falha na comunicação com o sistema legado de cartões na hora de cadastrar uma carteira.");
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Serviço indisponivel.");
        }
    }
}
