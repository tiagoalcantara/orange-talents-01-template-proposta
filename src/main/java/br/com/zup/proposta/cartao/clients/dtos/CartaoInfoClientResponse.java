package br.com.zup.proposta.cartao.clients.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CartaoInfoClientResponse {
    private String id;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CartaoInfoClientResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
