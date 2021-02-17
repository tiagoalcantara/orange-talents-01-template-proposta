package br.com.zup.proposta.cartao;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @OneToOne
    private Cartao cartao;
    @NotBlank
    private String ip;
    @NotBlank
    private String userAgent;
    @CreationTimestamp
    private LocalDateTime data;

    @Deprecated
    public Bloqueio() { }

    public Bloqueio(@NotNull Cartao cartao,
                    @NotBlank String ip,
                    @NotBlank String userAgent) {
        this.cartao = cartao;
        this.ip = ip;
        this.userAgent = userAgent;
    }
}
