package br.com.zup.proposta.cartao;

import br.com.zup.proposta.cartao.clients.CartaoClient;
import br.com.zup.proposta.cartao.clients.dtos.CartaoInfoClientResponse;
import br.com.zup.proposta.cartao.clients.dtos.CriarCartaoClientRequest;
import br.com.zup.proposta.compartilhado.utils.Ofuscador;
import br.com.zup.proposta.proposta.Proposta;
import br.com.zup.proposta.proposta.PropostaRepository;
import br.com.zup.proposta.proposta.Status;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartaoScheduler {
    private final PropostaRepository propostaRepository;
    private final CartaoClient cartaoClient;
    private static final Logger logger = LoggerFactory.getLogger(CartaoController.class);

    public CartaoScheduler(PropostaRepository propostaRepository,
                           CartaoClient cartaoClient) {
        this.propostaRepository = propostaRepository;
        this.cartaoClient = cartaoClient;
    }

    // TODO: Refatorar o método para ficar amigável pra multiplas instancias
    @Scheduled(fixedDelayString = "${cartao-associar-propostas.delay}")
    public void associar() {
        List<Proposta> propostas = propostaRepository.buscarSemCartaoAssociado(Status.ELEGIVEL);
        logger.info("Associando {} propostas", propostas.size());

        for (Proposta proposta : propostas) {
            try {
                cartaoClient.criar(new CriarCartaoClientRequest(proposta));
                CartaoInfoClientResponse cartaoInfo = cartaoClient.consultarPorProposta(proposta.getId());

                proposta.associarCartao(cartaoInfo.getId());
                proposta.atualizarStatus(Status.CRIADO);
                propostaRepository.save(proposta);
                logger.info("Cartao {} associado à proposta {}", Ofuscador.ofuscar(cartaoInfo.getId(), 4),
                            proposta.getId());
            } catch (FeignException.FeignClientException e) {
                logger.error(e.getLocalizedMessage());
            }
        }
    }
}
