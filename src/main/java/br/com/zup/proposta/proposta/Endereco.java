package br.com.zup.proposta.proposta;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Embeddable
public class Endereco {
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

    @Deprecated
    public Endereco() { }

    public Endereco(@NotBlank String cep,
                    @NotBlank String logradouro,
                    String complemento,
                    @NotBlank String numero,
                    @NotBlank String bairro,
                    @NotBlank String cidade,
                    @NotBlank @Size(min = 2, max = 2, message = "deve ser a sigla do estado.") String estado) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }
}
