package br.com.zup.proposta.proposal;

import br.com.zup.proposta.shared.exceptions.ApiException;
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
@RequestMapping("/proposal")
public class ProposalController {

    private final ProposalRepository proposalRepository;

    public ProposalController(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid CreateProposalRequest request, UriComponentsBuilder uriComponentsBuilder) {

        if(proposalRepository.findByDocument(request.getDocument()).isPresent()) {
            throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe uma proposta com o mesmo documento");
        }

        Proposal proposal = request.toProposal();
        proposalRepository.save(proposal);

        URI uri = uriComponentsBuilder.path("/proposal/{id}").buildAndExpand(proposal.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
