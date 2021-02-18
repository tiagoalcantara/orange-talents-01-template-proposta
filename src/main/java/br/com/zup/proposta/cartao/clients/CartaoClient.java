package br.com.zup.proposta.cartao.clients;

import br.com.zup.proposta.cartao.clients.dtos.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cartao", url = "${cartao.url}")
public interface CartaoClient {
    @PostMapping("/api/cartoes")
    void criar(@RequestBody CriarCartaoClientRequest request);

    @GetMapping("/api/cartoes")
    CartaoInfoClientResponse consultarPorProposta(@RequestParam Long idProposta);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    void bloquear(@PathVariable String id, @RequestBody BloquearCartaoClientRequest request);

    @PostMapping("/api/cartoes/{id}/avisos")
    void avisar(@PathVariable String id, @RequestBody AvisarViagemClientRequest request);

    @PostMapping("/api/cartoes/{id}/carteiras")
    void associarCarteira(@PathVariable String id, @RequestBody AssociarCarteiraClientRequest request);
}
