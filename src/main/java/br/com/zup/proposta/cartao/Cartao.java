package br.com.zup.proposta.cartao;

import br.com.zup.proposta.biometria.Biometria;
import br.com.zup.proposta.proposta.Proposta;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cartao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Proposta proposta;
    @NotNull
    private String numero;
    @ElementCollection
    private Set<Biometria> biometrias = new HashSet<>();

    @Deprecated
    public Cartao() {}

    public Cartao(Proposta proposta,
                  @NotNull String numero) {
        this.proposta = proposta;
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void adicionarBiometria(Biometria biometria) {
        this.biometrias.add(biometria);
    }
}
