package br.com.zup.proposta.biometria;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BiometriaTest {

    @Test
    void deveriaValidarUmaStringEmBase64Valida() {
        String base64 = "dGlhZ28=";
        Biometria biometria = new Biometria(base64);
        assertTrue(biometria.validar());
    }

    @Test
    void naoDeveriaValidarUmaStringQueNaoEstaEmBase64() {
        String base64 = "NaoEstáEmBase64";
        Biometria biometria = new Biometria(base64);
        assertFalse(biometria.validar());
    }
}