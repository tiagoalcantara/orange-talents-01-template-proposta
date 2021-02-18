package br.com.zup.proposta.cartao;

import br.com.zup.proposta.cartao.bloqueio.Bloqueio;
import br.com.zup.proposta.cartao.clients.dtos.AvisarViagemRequest;
import br.com.zup.proposta.cartao.clients.dtos.BloquearCartaoRequest;
import br.com.zup.proposta.cartao.clients.CartaoClient;
import br.com.zup.proposta.cartao.viagem.AvisoDeViagem;
import br.com.zup.proposta.cartao.viagem.AvisoDeViagemRequest;
import br.com.zup.proposta.compartilhado.auditoria.OrigemDaRequisicao;
import br.com.zup.proposta.compartilhado.utils.Ofuscador;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;

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

        Cartao cartao = cartaoRepository.findById(id)
                                        .orElseThrow(() -> {
                                            logger.error("Busca pelo cartão {} falhou.", id);
                                            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                              "Cartão não encontrado");
                                        });

        if (cartao.estaBloqueado()) {
            logger.error("Tentou bloquear o cartão {} mas ele já está bloqueado.", id);
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
        } catch (FeignException e) {
            logger.error("Falha na comunicação com o sistema legado de cartões na hora de bloquear um cartão.");
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Serviço indisponivel.");
        }

        return ResponseEntity.ok()
                             .build();
    }

    @PatchMapping("/{id}/viagem")
    public ResponseEntity<?> avisoDeViagem(@PathVariable Long id,
                                           @RequestBody @Valid AvisoDeViagemRequest avisoDeViagemRequest,
                                           HttpServletRequest request) {
        Cartao cartao = cartaoRepository.findById(id)
                                        .orElseThrow(() -> {
                                            logger.error("Busca pelo cartão {} falhou.", id);
                                            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                              "Cartão não encontrado");
                                        });

        OrigemDaRequisicao origemDaRequisicao = OrigemDaRequisicao.pegarDadosDeOrigemDaRequest(request);
        AvisoDeViagem avisoDeViagem = avisoDeViagemRequest.toViagem(origemDaRequisicao, cartao);

        try {
            cartaoClient.avisar(cartao.getNumero(), new AvisarViagemRequest(avisoDeViagemRequest.getDestino(),
                                                                            avisoDeViagemRequest.getDataFinal()
                                                                                                .format(
                                                                                                        DateTimeFormatter.ofPattern(
                                                                                                                "yyyy-MM-dd"))));
            cartao.avisarViagem(avisoDeViagem);
            cartaoRepository.save(cartao);

            logger.info("Criado um aviso de viagem para o cartão {}",
                        Ofuscador.ofuscar(cartao.getNumero(), 4));
        } catch (FeignException e) {
            logger.error("Falha na comunicação com o sistema legado de cartões na hora de avisar uma viagem.");
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Serviço indisponivel.");
        }


        return ResponseEntity.ok()
                             .build();
    }
}
