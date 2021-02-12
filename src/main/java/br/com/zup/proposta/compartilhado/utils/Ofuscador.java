package br.com.zup.proposta.compartilhado.utils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class Ofuscador {
    public static String ofuscar(@NotBlank String textoParaOfuscar, @Positive int qtdCaracteresParaManter) {
        int qtdCaracteresParaOfuscar = textoParaOfuscar.length() - qtdCaracteresParaManter;
        return "*".repeat(qtdCaracteresParaOfuscar) + textoParaOfuscar.substring(qtdCaracteresParaOfuscar);
    }
}
