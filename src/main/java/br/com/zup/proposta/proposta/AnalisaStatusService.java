package br.com.zup.proposta.proposta;

import br.com.zup.proposta.proposta.clients.AnalisaStatusRequest;
import br.com.zup.proposta.proposta.clients.AnalisaStatusResponse;
import br.com.zup.proposta.proposta.clients.SolicitacaoAnaliseClient;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class AnalisaStatusService {
    private final SolicitacaoAnaliseClient solicitacaoAnaliseClient;

    public AnalisaStatusService(SolicitacaoAnaliseClient solicitacaoAnaliseClient) {
        this.solicitacaoAnaliseClient = solicitacaoAnaliseClient;
    }

    public Status executa(AnalisaStatusRequest analisaStatusRequest){
        try {
            AnalisaStatusResponse analisaStatusResponse = solicitacaoAnaliseClient.analisaStatus(
                    analisaStatusRequest);
            String statusResponse = analisaStatusResponse.getResultadoSolicitacao();
            return Status.mapearStatusPeloValor(statusResponse);
        } catch (FeignException.UnprocessableEntity exception) {
            return Status.NAO_ELEGIVEL;
        }
    }


}
