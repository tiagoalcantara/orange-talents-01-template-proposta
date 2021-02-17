package br.com.zup.proposta.cartao;

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
import javax.transaction.Transactional;

@RestController
@RequestMapping("/cartao")
public class CartaoController {

    private final CartaoRepository cartaoRepository;
    private static final Logger logger = LoggerFactory.getLogger(CartaoController.class);

    public CartaoController(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @PatchMapping("/{id}/bloquear")
    @Transactional
    public ResponseEntity<?> bloquear(@PathVariable Long id, HttpServletRequest request) {

        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> {
            logger.info("Busca pelo cartão {} falhou.", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado");
        });

        if(cartao.estaBloqueado()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "O cartão já está bloqueado");
        }

        String userAgent = request.getHeader("User-Agent");
        String ip = request.getRemoteAddr();

        logger.info("Pedido de bloqueio para o cartão {}, vindo de {} - {}", id, ip, userAgent);

        Bloqueio bloqueio = new Bloqueio(cartao, ip, userAgent);
        cartao.bloquear(bloqueio);

        return ResponseEntity.ok().build();
    }
}
