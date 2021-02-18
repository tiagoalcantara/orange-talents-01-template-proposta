package br.com.zup.proposta.cartao.viagem;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.compartilhado.auditoria.OrigemDaRequisicao;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoDeViagem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String destino;
    @NotNull
    @FutureOrPresent
    @Column(nullable = false)
    private LocalDate dataFinal;
    @Embedded
    @Valid
    private OrigemDaRequisicao origemDaRequisicao;
    @CreationTimestamp
    private LocalDateTime dataAviso;
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public AvisoDeViagem(){}

    public AvisoDeViagem(@NotBlank String destino,
                         @NotNull @Future LocalDate dataFinal,
                         @Valid OrigemDaRequisicao origemDaRequisicao,
                         @Valid Cartao cartao) {
        this.destino = destino;
        this.dataFinal = dataFinal;
        this.origemDaRequisicao = origemDaRequisicao;
        this.cartao = cartao;
    }
}
