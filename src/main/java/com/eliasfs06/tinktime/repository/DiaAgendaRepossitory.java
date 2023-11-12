package com.eliasfs06.tinktime.repository;

import com.eliasfs06.tinktime.model.DiaAgenda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface DiaAgendaRepossitory extends GenericRepository<com.eliasfs06.tinktime.model.DiaAgenda>{
    @Query(value = "SELECT dia.* FROM agenda a " +
            "JOIN agenda_dias_agenda da ON da.agenda_id = a.id " +
            "JOIN dia_agenda dia ON da.dias_agenda_id = dia.id " +
            "WHERE a.id = ?1 AND dia.dia = ?2" , nativeQuery = true)
    DiaAgenda findByDia(Long agenda, Date data);

    @Query(value = "select distinct d.* from agenda a "+
            "join artist atr on atr.agenda_id = a.id "+
            "join agenda_dias_agenda adas on adas.agenda_id = a.id "+
            "join dia_agenda d on adas.dias_agenda_id = d.id "+
            "join dia_agenda_horarios dah on dah.dia_agenda_id = d.id "+
            "join horario h on dah.horarios_id = h.id "+
            "where h.status_horario = 'ABERTO' and atr.id = ?1", nativeQuery = true)
    List<DiaAgenda> findDiaAgendaComHorariosAbertoByArtist(Long artist);

    @Query(value = "SELECT dia.* FROM agenda a " +
            "JOIN agenda_dias_agenda adas ON a.id = adas.agenda_id " +
            "JOIN dia_agenda dia ON dia.id = adas.dias_agenda_id " +
            "WHERE a.id = ?1 AND dia.dia = ?2 ", nativeQuery = true)
    DiaAgenda findByDiaEAgenda(Long id, LocalDate diaAgenda);
}
