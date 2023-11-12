package com.eliasfs06.tinktime.repository;

import com.eliasfs06.tinktime.model.Empregado;
import com.eliasfs06.tinktime.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpregadoRepository extends GenericRepository<Empregado> {

    @Query(value = "SELECT a FROM Empregado a WHERE a.user = ?1 AND a.active = true", nativeQuery = false)
    Empregado findByUser(User user);

    @Query(value = "SELECT a FROM Empregado a WHERE a.active = true", nativeQuery = false)
    java.util.List<Empregado> listActiveArtists();
}
