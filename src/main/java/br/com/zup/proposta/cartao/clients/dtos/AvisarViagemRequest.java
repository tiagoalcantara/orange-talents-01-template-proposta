package br.com.zup.proposta.cartao.clients.dtos;

public class AvisarViagemRequest {
    private String destino;
    private String validoAte;

    public AvisarViagemRequest(String destino, String validoAte) {
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
