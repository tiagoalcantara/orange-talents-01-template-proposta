package br.com.zup.proposta.compartilhado.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class OfuscadorTest {

    @ParameterizedTest
    @MethodSource("deveOfuscarUmTextoGerador")
    void deveriaOfuscarUmTexto(String texto, int qtdCaracteresParaManter, String resultadoEsperado) throws Exception {
        String textoOfuscado = Ofuscador.ofuscar(texto, qtdCaracteresParaManter);
        Assertions.assertEquals(resultadoEsperado, textoOfuscado);
    }

    static Stream<Arguments> deveOfuscarUmTextoGerador() {
        return Stream.of(
          Arguments.of("1234-1234-1234-1234", 4, "***************1234"),
          Arguments.of("12345678910", 3, "********910")
        );
    }
}