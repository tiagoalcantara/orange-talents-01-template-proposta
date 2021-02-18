package br.com.zup.proposta.cartao.clients;

import br.com.zup.proposta.cartao.clients.dtos.AvisarViagemRequest;
import br.com.zup.proposta.cartao.clients.dtos.BloquearCartaoRequest;
import br.com.zup.proposta.cartao.clients.dtos.CartaoInfoResponse;
import br.com.zup.proposta.cartao.clients.dtos.CriarCartaoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cartao", url = "${cartao.url}")
public interface CartaoClient {
    @PostMapping("/api/cartoes")
    void criar(@RequestBody CriarCartaoRequest request);

    @GetMapping("/api/cartoes")
    CartaoInfoResponse consultarPorProposta(@RequestParam Long idProposta);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    void bloquear(@PathVariable String id, @RequestBody BloquearCartaoRequest request);

    @PostMapping("/api/cartoes/{id}/avisos")
    void avisar(@PathVariable String id, @RequestBody AvisarViagemRequest request);
}
