package br.com.zup.proposta.proposta;

import br.com.zup.proposta.compartilhado.validators.CpfCnpj;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;

public class NovaPropostaRequest {
    @NotBlank
    @CpfCnpj
    private String documento;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String nome;
    @NotNull
    @Valid
    private NovoEnderecoRequest endereco;
    @NotNull
    @Positive
    private BigDecimal salario;

    public NovaPropostaRequest(@NotBlank String documento,
                               @NotBlank @Email String email,
                               @NotBlank String nome,
                               @NotNull @Valid NovoEnderecoRequest endereco,
                               @NotNull @PositiveOrZero BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public String getDocumento() {
        return documento;
    }

    public NovoEnderecoRequest getEndereco() {
        return endereco;
    }

    public Proposta toProposta() {
        return new Proposta(documento, email, nome, endereco.toEndereco(), salario);
    }
}
