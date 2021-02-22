package br.com.zup.proposta.proposta;

import br.com.zup.proposta.compartilhado.utils.Ofuscador;
import br.com.zup.proposta.proposta.clients.AcompanharPropostaResponse;
import br.com.zup.proposta.proposta.clients.AnalisaStatusRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

    private final PropostaRepository propostaRepository;
    private final AnalisaStatusService analisaStatusService;
    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    public PropostaController(PropostaRepository propostaRepository,
                              AnalisaStatusService analisaStatusService) {
        this.propostaRepository = propostaRepository;
        this.analisaStatusService = analisaStatusService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid NovaPropostaRequest request,
                                   UriComponentsBuilder uriComponentsBuilder) {

        if (propostaRepository.existsByDocumento(request.getDocumento())) {
            logger.error("Já existe uma proposta com o documento " + Ofuscador.ofuscar(request.getDocumento(), 3));
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                                              "Já existe uma proposta com o mesmo documento");
        }

        Proposta proposta = request.toProposta();
        propostaRepository.save(proposta);

        Status statusAvaliado = analisaStatusService.executa(new AnalisaStatusRequest(proposta));

        proposta.atualizarStatus(statusAvaliado);
        propostaRepository.save(proposta);

        URI uri = uriComponentsBuilder.path("/proposta/{id}")
                                      .buildAndExpand(proposta.getId())
                                      .toUri();

        logger.info("Criada a proposta " + proposta.getId());

        return ResponseEntity.created(uri)
                             .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcompanharPropostaResponse> acompanhar(@PathVariable Long id) {
        Proposta proposta = propostaRepository.findById(id)
                                              .orElseThrow(() -> {
                                                  logger.info("Busca pela proposta {} falhou.", id);
                                                  throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proposta não encontrada.");
                                              });


        AcompanharPropostaResponse response = new AcompanharPropostaResponse(proposta);
        logger.info("Busca pela proposta {} realizada com sucesso.",
                    proposta.getId());
        return ResponseEntity.ok(response);
    }
}
