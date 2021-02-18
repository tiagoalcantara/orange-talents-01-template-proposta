package br.com.zup.proposta.cartao.clients.dtos;

import br.com.zup.proposta.proposta.Proposta;

public class CriarCartaoRequest {
    private String documento;
    private String nome;
    private Long idProposta;

    public CriarCartaoRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public Long getIdProposta() {
        return idProposta;
    }
}
