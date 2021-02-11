package br.com.zup.proposta.proposta;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NovoEnderecoRequest {
    @NotBlank
    private String cep;
    @NotBlank
    private String logradouro;
    private String complemento;
    @NotBlank
    private String numero;
    @NotBlank
    private String bairro;
    @NotBlank
    private String cidade;
    @NotBlank
    @Size(min = 2, max = 2, message = "deve ser a sigla do estado.")
    private String estado;

    public NovoEnderecoRequest(@NotBlank String cep,
                               @NotBlank String logradouro,
                               String complemento,
                               @NotBlank String numero,
                               @NotBlank String bairro,
                               @NotBlank String cidade, @NotBlank String estado) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public Endereco toEndereco() {
        return new Endereco(cep, logradouro, complemento, numero, bairro, cidade, estado);
    }
}
