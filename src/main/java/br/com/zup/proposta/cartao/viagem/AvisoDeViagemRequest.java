package br.com.zup.proposta.cartao.viagem;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.compartilhado.auditoria.OrigemDaRequisicao;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoDeViagemRequest {
    @NotBlank
    private String destino;
    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate dataFinal;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AvisoDeViagemRequest(@NotBlank String destino,
                                @NotNull @Future LocalDate dataFinal) {
        this.destino = destino;
        this.dataFinal = dataFinal;
    }

    public AvisoDeViagem toViagem(OrigemDaRequisicao origemDaRequisicao, Cartao cartao) {
        return new AvisoDeViagem(destino, dataFinal, origemDaRequisicao, cartao);
    }
}
