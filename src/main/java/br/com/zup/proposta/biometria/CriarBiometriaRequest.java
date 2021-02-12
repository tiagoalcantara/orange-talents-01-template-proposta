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

    public boolean validarBase64() {
        try {
            Base64.getDecoder().decode(this.digital.getBytes());
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}
