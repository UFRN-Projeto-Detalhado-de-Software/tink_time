package com.eliasfs06.tinktime.repository;

import com.eliasfs06.tinktime.model.Agenda;
import com.eliasfs06.tinktime.model.Empregado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends GenericRepository<Agenda>{
    @Query("SELECT a FROM Agenda a JOIN Empregado art ON art.agenda = a WHERE art = ?1")
    Agenda findByArtist(Empregado artist);


}
