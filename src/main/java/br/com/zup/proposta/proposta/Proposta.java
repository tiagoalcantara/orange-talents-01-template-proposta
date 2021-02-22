package br.com.zup.proposta.proposta;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.compartilhado.validators.CpfCnpj;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Proposta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @CpfCnpj
    @Column(nullable = false, unique = true, columnDefinition = "bytea")
    /*
    * TODO: Ainda não consegui colocar as configurações via docker-compose,
    *  por enquanto foi feito manualmente.
    * */
    @ColumnTransformer(read = "pgp_sym_decrypt(documento, current_setting('crypto.key'))",
                       write = "pgp_sym_encrypt(?, current_setting('crypto.key'))")
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

    @OneToOne(mappedBy = "proposta", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Cartao cartao;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NAO_AVALIADO;

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

    public String getEmail() {
        return email;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public Status getStatus() {
        return status;
    }

    public void atualizarStatus(Status resultadoSolicitacao) {
        this.status = resultadoSolicitacao;
    }

    public void associarCartao(String numero) {
        Assert.hasLength(numero, "O numero do cartão é obrigatório.");
        this.cartao = new Cartao(this, numero);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Proposta proposta = (Proposta) o;
        return documento.equals(proposta.documento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documento);
    }
}
