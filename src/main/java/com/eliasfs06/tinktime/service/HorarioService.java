package com.eliasfs06.tinktime.service;

import com.eliasfs06.tinktime.model.DiaAgenda;
import com.eliasfs06.tinktime.model.Horario;
import com.eliasfs06.tinktime.repository.GenericRepository;
import com.eliasfs06.tinktime.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class HorarioService extends GenericService<Horario> {

    @Autowired
    private HorarioRepository horarioRepository;

    public HorarioService(GenericRepository<Horario> repository) {
        super(repository);
    }

    public List<Horario> filtrarHorarios(DiaAgenda diaAgenda, String inicio, String fim) {
        List<Horario> horarios = diaAgenda.getHorarios();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime horaInicio = LocalTime.parse(inicio, formatter);
        LocalTime horaFim = LocalTime.parse(fim, formatter);

        List<Horario> horariosFiltrados = new ArrayList<>();
        for(Horario horario : horarios){
            if (horario.getHoraInicio().compareTo(horaInicio) >= 0 && horario.getHoraFim().compareTo(horaFim) <= 0) {
                horariosFiltrados.add(horario);
            }
        }

        return horariosFiltrados;
    }
}
