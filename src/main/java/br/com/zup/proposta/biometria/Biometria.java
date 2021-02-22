package br.com.zup.proposta.biometria;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Arrays;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Biometria biometria = (Biometria) o;
        return Arrays.equals(digital, biometria.digital);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(digital);
    }
}
