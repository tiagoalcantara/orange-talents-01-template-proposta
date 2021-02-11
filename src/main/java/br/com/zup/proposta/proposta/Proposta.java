package br.com.zup.proposta.proposta;

import br.com.zup.proposta.compartilhado.validators.CpfCnpj;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @CpfCnpj
    @Column(nullable = false, unique = true)
    private String documento;
    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;
    @NotBlank
    @Column(nullable = false)
    private String nome;
    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal salario;
    @NotNull
    @Valid
    @Embedded
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Deprecated
    public Proposta() { }

    public Proposta(@NotBlank String documento,
                    @NotBlank @Email String email,
                    @NotBlank String nome,
                    @NotNull @Valid Endereco endereco,
                    @NotNull @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public void atualizarStatus(Status resultadoSolicitacao) {
        this.status = resultadoSolicitacao;
    }
}
