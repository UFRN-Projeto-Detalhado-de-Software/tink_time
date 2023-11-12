package com.eliasfs06.tinktime.service;

import com.eliasfs06.tinktime.model.Empregado;
import com.eliasfs06.tinktime.model.User;
import com.eliasfs06.tinktime.model.dto.EmpregadoDTO;
import com.eliasfs06.tinktime.model.dto.UserDTO;
import com.eliasfs06.tinktime.repository.EmpregadoRepository;
import com.eliasfs06.tinktime.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpregadoService extends GenericService<Empregado>{

    @Autowired
    private EmpregadoRepository repository;

    @Autowired
    private AgendaService agendaService;

    public EmpregadoService(GenericRepository<Empregado> repository) {
        super(repository);
    }

    public EmpregadoDTO findByUser(User user) {
        Empregado artist = repository.findByUser(user);
        return new EmpregadoDTO(artist);
    }

    public void createArtist(User user) {
        Empregado artist = new Empregado();
        artist.setUser(user);
        agendaService.createAgenda(artist);
        save(artist);
    }

    public List<UserDTO> getListUserDTOArtists(List<Empregado> artists){
        List<UserDTO> artistsUsers = new ArrayList<>();
        for (Empregado artist : artists) {
            artistsUsers.add(new UserDTO(artist.getUser()));
        }

        return artistsUsers;
    }

    public java.util.List<Empregado> listActiveArtists() {
        return repository.listActiveArtists();
    }
}
