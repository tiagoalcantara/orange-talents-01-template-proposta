package br.com.zup.proposta.cartao;

import br.com.zup.proposta.cartao.clients.BloquearCartaoRequest;
import br.com.zup.proposta.cartao.clients.CartaoClient;
import br.com.zup.proposta.compartilhado.auditoria.OrigemDaRequisicao;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cartao")
public class CartaoController {

    private final CartaoRepository cartaoRepository;
    private final CartaoClient cartaoClient;
    private static final Logger logger = LoggerFactory.getLogger(CartaoController.class);

    public CartaoController(CartaoRepository cartaoRepository,
                            CartaoClient cartaoClient) {
        this.cartaoRepository = cartaoRepository;
        this.cartaoClient = cartaoClient;
    }

    @PatchMapping("/{id}/bloquear")
    public ResponseEntity<?> bloquear(@PathVariable Long id, HttpServletRequest request) {

        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> {
            logger.info("Busca pelo cartão {} falhou.", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado");
        });

        if(cartao.estaBloqueado()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "O cartão já está bloqueado");
        }

        try {
            cartaoClient.bloquear(cartao.getNumero(), new BloquearCartaoRequest(
                    "propostas"));
            OrigemDaRequisicao origemDaRequisicao = OrigemDaRequisicao.pegarDadosDeOrigemDaRequest(request);
            Bloqueio bloqueio = new Bloqueio(cartao, origemDaRequisicao);

            logger.info("Pedido de bloqueio para o cartão {}, vindo de {} - {}", id, origemDaRequisicao.getIp(),
                        origemDaRequisicao.getUserAgent());

            cartao.bloquear(bloqueio);
            cartaoRepository.save(cartao);
        } catch (FeignException.FeignClientException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cartão com dados inválidos");
        } catch (FeignException.FeignServerException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "O serviço está indisponível");
        }

        return ResponseEntity.ok().build();
    }


}
