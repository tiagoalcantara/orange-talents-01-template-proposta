package br.com.zup.proposta.proposta.clients;

public class AnalisaStatusResponse {
    private String documento;
    private String nome;
    private Long idProposta;
    private String resultadoSolicitacao;

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public String getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }
}
