package br.com.zup.proposta.cartao.carteira;

import br.com.zup.proposta.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AssociarCarteiraRequest {
    @Email
    @NotBlank
    private String email;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AssociarCarteiraRequest(@Email @NotBlank String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Carteira toCarteira(Cartao cartao, TipoCarteira tipoCarteira){
        return new Carteira(email, tipoCarteira, cartao);
    }
}
