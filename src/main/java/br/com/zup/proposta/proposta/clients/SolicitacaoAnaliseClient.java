package br.com.zup.proposta.proposta.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "solicitacaoAnalise", url = "${solicitacaoAnalise.url}")
public interface SolicitacaoAnaliseClient {

    @PostMapping("/api/solicitacao")
    AnalisaStatusResponse analisaStatus(@RequestBody AnalisaStatusRequest request);
}
