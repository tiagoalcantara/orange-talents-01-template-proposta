package br.com.zup.proposta.compartilhado.auditoria;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

@Embeddable
public class OrigemDaRequisicao {
    @NotBlank
    @Column(nullable = false)
    private String ip;
    @NotBlank
    @Column(nullable = false)
    private String userAgent;

    @Deprecated
    public OrigemDaRequisicao(){}

    public OrigemDaRequisicao(@NotBlank String ip, @NotBlank String userAgent) {
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public static OrigemDaRequisicao pegarDadosDeOrigemDaRequest(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");

        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return new OrigemDaRequisicao(ip, userAgent);
    }
}
