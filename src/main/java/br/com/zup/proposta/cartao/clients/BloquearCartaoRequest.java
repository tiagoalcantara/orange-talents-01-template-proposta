package br.com.zup.proposta.cartao.clients;

import com.fasterxml.jackson.annotation.JsonCreator;

public class BloquearCartaoRequest {
    private String sistemaResponsavel;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BloquearCartaoRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
