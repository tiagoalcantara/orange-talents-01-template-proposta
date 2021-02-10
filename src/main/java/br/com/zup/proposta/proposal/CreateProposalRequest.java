package br.com.zup.proposta.proposal;

import br.com.zup.proposta.shared.validators.CpfCnpj;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class CreateProposalRequest {
    @NotBlank
    @CpfCnpj
    private String document;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotNull
    @Positive
    private BigDecimal salary;

    public CreateProposalRequest(@NotBlank String document,
                                 @NotBlank @Email String email,
                                 @NotBlank String name,
                                 @NotBlank String address,
                                 @NotNull @PositiveOrZero BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public Proposal toProposal() {
        return new Proposal(document, email, name, address, salary);
    }
}
