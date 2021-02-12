package br.com.zup.proposta.proposta.clients;

import br.com.zup.proposta.proposta.Proposta;
import br.com.zup.proposta.proposta.Status;

import java.math.BigDecimal;

public class AcompanharPropostaResponse {
    private Status status;
    private String documento;
    private String email;
    private String nome;
    private BigDecimal salario;
    private DetalharEnderecoResponse endereco;
    private String cartao;

    public AcompanharPropostaResponse(Proposta proposta) {
        this.status = proposta.getStatus();
        this.documento = proposta.getDocumento();
        this.email = proposta.getEmail();
        this.nome = proposta.getNome();
        this.salario = proposta.getSalario();
        this.endereco = new DetalharEnderecoResponse(proposta.getEndereco());
        this.cartao = proposta.getCartao().getNumero();
    }

    public Status getStatus() {
        return status;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public DetalharEnderecoResponse getEndereco() {
        return endereco;
    }

    public String getCartao() {
        return cartao;
    }
}
