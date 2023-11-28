package com.eliasfs06.tinktime.service;

import com.eliasfs06.tinktime.model.Funcionario;
import com.eliasfs06.tinktime.model.User;
import com.eliasfs06.tinktime.model.dto.FuncionarioDTO;
import com.eliasfs06.tinktime.model.dto.UserDTO;
import com.eliasfs06.tinktime.repository.FuncionaroRepository;
import com.eliasfs06.tinktime.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FuncionarioService extends GenericService<Funcionario> {

    @Autowired
    private FuncionaroRepository repository;

    @Autowired
    private AgendaService agendaService;

    public FuncionarioService(GenericRepository<Funcionario> repository) {
        super(repository);
    }

    public FuncionarioDTO findByUser(User user) {
        Funcionario funcionario = repository.findByUser(user);
        return new FuncionarioDTO(funcionario);
    }

    @Transactional
    public void createFuncionario(User user) {
        Funcionario funcionario = new Funcionario();
        funcionario.setUser(user);
        agendaService.createAgenda(funcionario);
        save(funcionario);
    }

    public List<UserDTO> getListUserDTOFuncionarios(List<Funcionario> funcionarios){
        List<UserDTO> funcionariosUsers = new ArrayList<>();
        for (Funcionario funcionario : funcionarios) {
            funcionariosUsers.add(new UserDTO(funcionario.getUser()));
        }

        return funcionariosUsers;
    }

    public java.util.List<Funcionario> listActiveFuncionarios() {
        return repository.listActiveFuncionarios();
    }
}