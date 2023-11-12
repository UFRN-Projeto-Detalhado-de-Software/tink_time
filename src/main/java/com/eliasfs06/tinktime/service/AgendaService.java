package com.eliasfs06.tinktime.service;

import com.eliasfs06.tinktime.model.*;
import com.eliasfs06.tinktime.model.dto.HorariosTatuagem;
import com.eliasfs06.tinktime.repository.AgendaRepository;
import com.eliasfs06.tinktime.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class AgendaService extends GenericService<Agenda> {

    @Autowired
    private DiaAgendaService diaAgendaService;

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private AgendaRepository agendaRepository;

    public AgendaService(GenericRepository<Agenda> repository) {
        super(repository);
    }

    public void createAgenda(Empregado artist) {
        Agenda agenda = new Agenda();
        artist.setAgenda(agenda);
        save(agenda);
    }

    public DiaAgenda createDiaAgenda(Agenda agenda, LocalDate dia){
        DiaAgenda diaAgenda = new DiaAgenda();
        diaAgenda.setDia(dia);
        agenda.getDiasAgenda().add(diaAgenda);
        diaAgendaService.save(diaAgenda);

        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(22, 0);

        while (startTime.getHour() < endTime.getHour()){
            Horario horario = new Horario();
            horario.setHoraInicio(startTime);
            horario.setHoraFim(startTime.plusMinutes(30));
            startTime = startTime.plusMinutes(30);
            horario.setStatusHorario(StatusHorario.FECHADO);
            diaAgenda.getHorarios().add(horario);
            horarioService.save(horario);
        }

        diaAgendaService.save(diaAgenda);
        update(agenda);
        return diaAgenda;
    }

    public Agenda findByArtist(Empregado artist) {
        return agendaRepository.findByArtist(artist);
    }

    public List<HorariosTatuagem> sugerirHorarios(Empregado artist, Integer numeroSessoes) {
        List<DiaAgenda> diaAgendaDisponiveis = diaAgendaService.findDiaAgendaComHorariosAbertoByArtist(artist);
        List<DiaAgenda> diaAgendaPossiveis = new ArrayList<>();

        for(DiaAgenda diaAgenda : diaAgendaDisponiveis){
            List<Horario> horarios = diaAgenda.getHorarios();
            if(horarios.size() > numeroSessoes){
                diaAgendaPossiveis.add(diaAgenda);
            }
        }

        List<Horario> sequenciaHorarios = new ArrayList<>();
        List<HorariosTatuagem> horariosTatuagems = new ArrayList<>();

        for(DiaAgenda diaAgenda : diaAgendaPossiveis){
            if(numeroSessoes > 1){
                List<Horario> horarios = diaAgenda.getHorarios();
                for(int i = 0; i < horarios.size(); i++){
                    if(i+1 < horarios.size() &&
                            horarios.get(i).getStatusHorario() == StatusHorario.ABERTO &&
                            horarios.get(i+1).getStatusHorario() == StatusHorario.ABERTO &&
                            horarios.get(i).getHoraFim().compareTo(horarios.get(i+1).getHoraInicio()) == 0){
                        sequenciaHorarios.add(horarios.get(i));
                        sequenciaHorarios.add(horarios.get(i+1));
                    } else {
                        if(sequenciaHorarios.size() >= numeroSessoes){
                            List<Horario> hoariosSemDuplicatas = new ArrayList<>();
                            for (Horario horario : sequenciaHorarios) {
                                if (!hoariosSemDuplicatas.contains(horario)) {
                                    hoariosSemDuplicatas.add(horario);
                                }
                            }

                            HorariosTatuagem horarioTatuagem = new HorariosTatuagem();
                            horarioTatuagem.setHorarios(hoariosSemDuplicatas);
                            horarioTatuagem.setDiaAgenda(diaAgenda);
                            horariosTatuagems.add(horarioTatuagem);
                        }
                        sequenciaHorarios = new ArrayList<>();
                    }
                }
            } else {
                List<Horario> horarios = diaAgenda.getHorarios();
                for(Horario horario : horarios){
                    HorariosTatuagem horarioTatuagem = new HorariosTatuagem();
                    List<Horario> horarioList = new ArrayList<>();
                    horarioList.add(horario);
                    horarioTatuagem.setHorarios(horarioList);
                    horarioTatuagem.setDiaAgenda(diaAgenda);
                    horariosTatuagems.add(horarioTatuagem);
                }
            }
        }


        return horariosTatuagems;
    }

    public List<HorariosTatuagem> formatarHorariosDisponiveis(List<HorariosTatuagem> horariosDisponveis, Integer numeroSessoes) {
        List<HorariosTatuagem> horariosFormatados = new ArrayList<>();
        for(HorariosTatuagem horariosTatuagemDisponivel : horariosDisponveis){
            List<Horario> horariosTatuagem = horariosTatuagemDisponivel.getHorarios().stream().toList();
            for(int i = 0; i < horariosTatuagem.size() - (numeroSessoes - 1); i++){
                List<Horario> horarios = new ArrayList<>();
                for(int j = 0; j < numeroSessoes; j++){
                    horarios.add(horariosTatuagem.get(i+j));
                }
                HorariosTatuagem novoHorarioFormatado = new HorariosTatuagem();
                novoHorarioFormatado.setHorarios(horarios);
                novoHorarioFormatado.setDiaAgenda(horariosTatuagemDisponivel.getDiaAgenda());
                horariosFormatados.add(novoHorarioFormatado);
            }
        }

        return horariosFormatados;
    }

}
