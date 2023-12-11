package com.eliasfs06.tinktime.repository;

import com.eliasfs06.tinktime.model.PropostaIdeia;
import com.eliasfs06.tinktime.model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PropostaIdeiaRepository extends GenericRepository<PropostaIdeia> {

    PropostaIdeia findPropostaIdeiaByClienteAndTatuadorAndDescricao(User cliente, User tatuador, String descricao);

    @Query("SELECT p FROM PropostaIdeia p WHERE p.tatuador.id = ?1")
    Optional<List<PropostaIdeia>> findAllByTatuadorId(Long id);

    //find all by cliente id
    @Query("SELECT p FROM PropostaIdeia p WHERE p.cliente.id = ?1")
    Optional<List<PropostaIdeia>> findAllByClienteId(Long id);
}
