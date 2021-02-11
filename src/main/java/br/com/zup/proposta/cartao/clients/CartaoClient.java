package br.com.zup.proposta.cartao.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cartao", url = "${cartao.url}")
public interface CartaoClient {
    @PostMapping("/api/cartoes")
    void criar(@RequestBody  CriarCartaoRequest request);

    @GetMapping("/api/cartoes")
    CartaoInfoResponse consultarPorProposta(@RequestParam Long idProposta);
}
