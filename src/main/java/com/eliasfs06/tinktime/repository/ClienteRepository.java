package com.eliasfs06.tinktime.repository;

import com.eliasfs06.tinktime.model.Client;
import com.eliasfs06.tinktime.model.User;
import com.eliasfs06.tinktime.model.enums.UnidadeFederacao;
import com.eliasfs06.tinktime.service.suggestionObserver.TargetClient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends GenericRepository<Client> {
    @Query("SELECT c FROM Client c " +
            "JOIN User u ON c.user = u " +
            "JOIN Person p ON u.person = p " +
            "WHERE p.active = TRUE AND p.ufResidencia = ?1")
    List<TargetClient> findByUF(UnidadeFederacao uf);

    @Query("SELECT c FROM Client c WHERE c.user = ?1")
    Client findByUser(User user);
}
