package br.com.zup.proposta.compartilhado.handlers;

import java.util.ArrayList;
import java.util.List;

public class ErroDeValidacaoResponse {
    private final List<String> erros;

    public ErroDeValidacaoResponse(List<String> erros) {
        this.erros = erros;
    }

    public ErroDeValidacaoResponse(String erro) {
        this.erros = new ArrayList<>();
        this.erros.add(erro);
    }

    public List<String> getErros() {
        return erros;
    }
}
