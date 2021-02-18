package br.com.zup.proposta.cartao.bloqueio;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.compartilhado.auditoria.OrigemDaRequisicao;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
}
