package br.com.zup.proposta.cartao;

import br.com.zup.proposta.proposta.Proposta;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Cartao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Proposta proposta;
    @NotNull
    private String numero;

    @Deprecated
    public Cartao() {}

    public Cartao(Proposta proposta,
                  @NotNull String numero) {
        this.proposta = proposta;
        this.numero = numero;
    }
}
