package br.com.zup.proposta.cartao.clients.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class BloquearCartaoClientResponse {
    private String resultado;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BloquearCartaoClientResponse(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }
}
