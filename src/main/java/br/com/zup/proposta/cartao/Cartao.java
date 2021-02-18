package br.com.zup.proposta.cartao;

import br.com.zup.proposta.biometria.Biometria;
import br.com.zup.proposta.cartao.bloqueio.Bloqueio;
import br.com.zup.proposta.cartao.carteira.Carteira;
import br.com.zup.proposta.cartao.carteira.TipoCarteira;
import br.com.zup.proposta.cartao.viagem.AvisoDeViagem;
import br.com.zup.proposta.proposta.Proposta;
import br.com.zup.proposta.proposta.Status;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Cartao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.MERGE)
    private Proposta proposta;
    @NotNull
    private String numero;
    @ElementCollection
    private Set<Biometria> biometrias = new HashSet<>();
    @OneToOne(mappedBy = "cartao", cascade = CascadeType.PERSIST)
    private Bloqueio bloqueio;
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private List<AvisoDeViagem> avisosDeViagens = new ArrayList<>();
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private Set<Carteira> carteiras = new HashSet<>();

    @Deprecated
    public Cartao() {}

    public Cartao(Proposta proposta,
                  @NotNull String numero) {
        this.proposta = proposta;
        this.numero = numero;
    }

    public boolean estaBloqueado(){
        return this.bloqueio != null;
    }

    public void bloquear(Bloqueio bloqueio) {
        Assert.state(!this.estaBloqueado(), "Não podemos bloquear um cartão já bloqueado");
        this.bloqueio = bloqueio;
        this.proposta.atualizarStatus(Status.BLOQUEADO);
    };

    public String getNumero() {
        return numero;
    }

    public void avisarViagem(AvisoDeViagem viagem){
        this.avisosDeViagens.add(viagem);
    }

    public void adicionarBiometria(Biometria biometria) {
        this.biometrias.add(biometria);
    }

    public void associarCarteira(Carteira carteira) {
        carteiras.add(carteira);
    }

    public boolean temCarteira(Carteira carteira) {
        return carteiras.contains(carteira);
    }
}
