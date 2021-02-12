package br.com.zup.proposta.biometria;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Embeddable
public class Biometria {
    @NotNull
    private byte[] digital;
    @CreationTimestamp
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Deprecated
    public Biometria() {}

    public Biometria(String digital) {
        this.digital = digital.getBytes();
    }
}
