package br.com.zup.proposta.biometria;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import java.util.Base64;

public class CriarBiometriaRequest {
    @NotBlank
    private String digital;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CriarBiometriaRequest(@NotBlank String digital) {
        this.digital = digital;
    }

    public Biometria toBiometria() {
        return new Biometria(digital);
    }
}
