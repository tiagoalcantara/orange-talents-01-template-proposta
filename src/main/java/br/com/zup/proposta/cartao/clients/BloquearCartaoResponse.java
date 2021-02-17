package br.com.zup.proposta.cartao.clients;

import com.fasterxml.jackson.annotation.JsonCreator;

public class BloquearCartaoResponse {
    private String resultado;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BloquearCartaoResponse(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }
}
