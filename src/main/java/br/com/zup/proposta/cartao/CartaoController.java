package br.com.zup.proposta.cartao;

import br.com.zup.proposta.cartao.clients.CartaoClient;
import br.com.zup.proposta.cartao.clients.CartaoInfoResponse;
import br.com.zup.proposta.cartao.clients.CriarCartaoRequest;
import br.com.zup.proposta.proposta.Proposta;
import br.com.zup.proposta.proposta.PropostaRepository;
import br.com.zup.proposta.proposta.Status;
import feign.FeignException;
import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cartao")
public class CartaoController {

    private final PropostaRepository propostaRepository;
    private final CartaoClient cartaoClient;
    private static final Logger logger = LoggerFactory.getLogger(CartaoController.class);

    public CartaoController(PropostaRepository propostaRepository,
                            CartaoClient cartaoClient) {
        this.propostaRepository = propostaRepository;
        this.cartaoClient = cartaoClient;
    }

    @GetMapping("/associar-propostas")
    @Scheduled(fixedDelayString = "${cartao-associar-propostas.delay}")
    public void associar() {
        List<Proposta> propostas = propostaRepository.buscarSemCartaoAssociado(Status.ELEGIVEL);
        logger.info("Associando {} propostas", propostas.size());

        for (Proposta proposta : propostas) {
            try {
                cartaoClient.criar(new CriarCartaoRequest(proposta));
                CartaoInfoResponse cartaoInfo = cartaoClient.consultarPorProposta(proposta.getId());

                proposta.associarCartao(cartaoInfo.getId());
                propostaRepository.save(proposta);
                logger.info("Cartao {} associado à proposta {}", cartaoInfo.getIdOfuscado(), proposta.getId());
            } catch (FeignException.FeignClientException e) {
                logger.error(e.getLocalizedMessage());
            }
        }
    }
}
