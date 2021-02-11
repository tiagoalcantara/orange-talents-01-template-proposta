package br.com.zup.proposta.proposta;

import br.com.zup.proposta.compartilhado.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

    private final PropostaRepository propostaRepository;

    public PropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criar(@RequestBody @Valid NovaPropostaRequest request,
                               UriComponentsBuilder uriComponentsBuilder) {

        if(propostaRepository.existsByDocumento(request.getDocumento())) {
            throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe uma proposta com o mesmo documento");
        }

        Proposta proposta = request.toProposta();
        propostaRepository.save(proposta);

        URI uri = uriComponentsBuilder.path("/proposal/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
