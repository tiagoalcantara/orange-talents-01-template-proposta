package br.com.zup.proposta.proposal;

import br.com.zup.proposta.shared.validators.CpfCnpj;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @CpfCnpj
    @Column(nullable = false)
    private String document;
    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;
    @NotBlank
    @Column(nullable = false)
    private String name;
    @NotBlank
    @Column(nullable = false)
    private String address;
    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal salary;

    public Proposal(@NotBlank String document,
                    @NotBlank @Email String email,
                    @NotBlank String name,
                    @NotBlank String address,
                    @NotNull @Positive BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }
}
