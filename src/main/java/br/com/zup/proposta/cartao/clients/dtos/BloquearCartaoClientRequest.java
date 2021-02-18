package br.com.zup.proposta.cartao.clients.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class BloquearCartaoClientRequest {
    private String sistemaResponsavel;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BloquearCartaoClientRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
