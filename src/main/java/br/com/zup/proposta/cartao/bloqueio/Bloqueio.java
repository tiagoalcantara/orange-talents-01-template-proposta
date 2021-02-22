package br.com.zup.proposta.cartao.bloqueio;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.compartilhado.auditoria.OrigemDaRequisicao;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Bloqueio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @OneToOne
    private Cartao cartao;
    @Valid
    @Embedded
    private OrigemDaRequisicao origemDaRequisicao;
    @CreationTimestamp
    private LocalDateTime data;

    @Deprecated
    public Bloqueio() { }

    public Bloqueio(@NotNull Cartao cartao,
                    @Valid OrigemDaRequisicao origemDaRequisicao) {
        this.cartao = cartao;
        this.origemDaRequisicao = origemDaRequisicao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bloqueio bloqueio = (Bloqueio) o;
        return Objects.equals(id, bloqueio.id) && Objects.equals(cartao,
                                                                 bloqueio.cartao) && Objects.equals(
                origemDaRequisicao,
                bloqueio.origemDaRequisicao) && Objects.equals(data, bloqueio.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cartao, origemDaRequisicao, data);
    }
}
