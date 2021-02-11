package br.com.zup.proposta.proposta;

import br.com.zup.proposta.compartilhado.exceptions.ApiException;
import br.com.zup.proposta.proposta.clients.AnalisaStatusRequest;
import br.com.zup.proposta.proposta.clients.SolicitacaoAnaliseClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

    private final PropostaRepository propostaRepository;
    private final SolicitacaoAnaliseClient solicitacaoAnaliseClient;
    private final AnalisaStatusService analisaStatusService;

    public PropostaController(PropostaRepository propostaRepository,
                              SolicitacaoAnaliseClient solicitacaoAnaliseClient,
                              AnalisaStatusService analisaStatusService) {
        this.propostaRepository = propostaRepository;
        this.solicitacaoAnaliseClient = solicitacaoAnaliseClient;
        this.analisaStatusService = analisaStatusService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid NovaPropostaRequest request,
                               UriComponentsBuilder uriComponentsBuilder) {

        if(propostaRepository.existsByDocumento(request.getDocumento())) {
            throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe uma proposta com o mesmo documento");
        }

        Proposta proposta = request.toProposta();
        propostaRepository.save(proposta);

        Status statusAvaliado = analisaStatusService.executa(new AnalisaStatusRequest(proposta));
        proposta.atualizarStatus(statusAvaliado);
        propostaRepository.save(proposta);

        URI uri = uriComponentsBuilder.path("/proposal/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
