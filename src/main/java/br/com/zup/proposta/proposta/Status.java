package br.com.zup.proposta.proposta;

public enum Status {
    ELEGIVEL, NAO_ELEGIVEL;

    public static Status mapearStatusPeloValor(String valor) {
        if(valor.equalsIgnoreCase("SEM_RESTRICAO")) return ELEGIVEL;
        return NAO_ELEGIVEL;
    }
}