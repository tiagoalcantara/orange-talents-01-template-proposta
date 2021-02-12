package br.com.zup.proposta.cartao.clients;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CartaoInfoResponse {
    private String id;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CartaoInfoResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
