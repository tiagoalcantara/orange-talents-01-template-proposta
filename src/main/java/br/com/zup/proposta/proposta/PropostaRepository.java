package br.com.zup.proposta.proposta;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    boolean existsByDocumento(String document);

    @Query("SELECT p FROM Proposta p LEFT JOIN p.cartao c WHERE p.status = :status AND c.id is null")
    List<Proposta> buscarSemCartaoAssociado(@Param("status") Status status);
}
