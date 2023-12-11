package com.eliasfs06.tinktime.service.suggestionObserver;

import com.eliasfs06.tinktime.model.Client;
import com.eliasfs06.tinktime.model.Funcionario;
import com.eliasfs06.tinktime.model.enums.UnidadeFederacao;
import com.eliasfs06.tinktime.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SuggestionObserver {

    @Autowired
    private ClienteRepository clienteRepository;

    private List<TargetClient> targetClients = new ArrayList<>();

    public void setTargetClients(UnidadeFederacao uf) {
        targetClients = clienteRepository.findByUF(uf);
    }

    public void notifyTargetClients(Funcionario funcionario){
        for(TargetClient client : targetClients){
            client.updateClientsSugestion(funcionario);
            clienteRepository.save((Client) client);
        }
    }
}
