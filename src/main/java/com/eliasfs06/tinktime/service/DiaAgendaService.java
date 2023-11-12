package com.eliasfs06.tinktime.service;

import com.eliasfs06.tinktime.model.Agenda;
import com.eliasfs06.tinktime.model.Empregado;
import com.eliasfs06.tinktime.model.DiaAgenda;
import com.eliasfs06.tinktime.repository.DiaAgendaRepossitory;
import com.eliasfs06.tinktime.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class DiaAgendaService extends GenericService<DiaAgenda> {

    @Autowired
    private DiaAgendaRepossitory diaAgendaRepossitory;

    public DiaAgendaService(GenericRepository<DiaAgenda> repository) {
        super(repository);
    }

    public DiaAgenda findByDia(Agenda agenda, String data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dia;
        try {
            dia = dateFormat.parse(data);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return diaAgendaRepossitory.findByDia(agenda.getId(), dia);
    }

    public List<DiaAgenda> findDiaAgendaComHorariosAbertoByArtist(Empregado artist) {
        return diaAgendaRepossitory.findDiaAgendaComHorariosAbertoByArtist(artist.getId());
    }

    public DiaAgenda findByDiaEAgenda(Long id, LocalDate diaAgenda) {
        return diaAgendaRepossitory.findByDiaEAgenda(id, diaAgenda);
    }
}
