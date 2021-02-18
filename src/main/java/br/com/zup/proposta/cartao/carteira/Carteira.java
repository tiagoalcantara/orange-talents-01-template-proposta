package br.com.zup.proposta.cartao.carteira;

import br.com.zup.proposta.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Carteira {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCarteira tipoCarteira;
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Carteira(){}
    public Carteira(@NotBlank @Email String email,
                    @NotNull TipoCarteira tipoCarteira, Cartao cartao) {
        this.email = email;
        this.tipoCarteira = tipoCarteira;
        this.cartao = cartao;
    }

    public TipoCarteira getTipoCarteira() {
        return tipoCarteira;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Carteira carteira = (Carteira) o;
        return tipoCarteira == carteira.tipoCarteira;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoCarteira);
    }
}
