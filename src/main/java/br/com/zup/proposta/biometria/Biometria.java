package br.com.zup.proposta.biometria;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Base64;

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

    public boolean validar(){
        try {
            Base64.getDecoder().decode(digital);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
