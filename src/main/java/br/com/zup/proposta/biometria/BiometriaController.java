package br.com.zup.proposta.biometria;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class BiometriaController {

    private final CartaoRepository cartaoRepository;
    private final static Logger logger = LoggerFactory.getLogger(BiometriaController.class);

    public BiometriaController(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping("/cartao/{id}/biometria")
    @Transactional
    public ResponseEntity<?> criar(@RequestBody @Valid CriarBiometriaRequest request, @PathVariable Long id,
                                   UriComponentsBuilder uriComponentsBuilder) {
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> {
            logger.info("Busca pelo cartão {} falhou.", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado");
        });

        if(!request.validarBase64()) {
            logger.error("A entrada é uma Base64 inválida");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo digital deve estar em Base64");
        }

        Biometria biometria = request.toBiometria();
        cartao.adicionarBiometria(biometria);
        logger.info("Biometria cadastrada para o cartão {}", id);

        URI uri = uriComponentsBuilder.path("/cartao/{id}/biometria").buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).build();
    }
}
