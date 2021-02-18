package br.com.zup.proposta.cartao.clients.dtos;

public class AvisarViagemClientRequest {
    private String destino;
    private String validoAte;

    public AvisarViagemClientRequest(String destino, String validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public String getValidoAte() {
        return validoAte;
    }
}
