package com.eliasfs06.tinktime.service;

import com.eliasfs06.tinktime.model.Client;
import com.eliasfs06.tinktime.model.User;
import com.eliasfs06.tinktime.repository.ClienteRepository;
import com.eliasfs06.tinktime.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService extends GenericService<Client> {

    @Autowired
    private ClienteRepository repository;

    public ClientService(GenericRepository<Client> repository) {
        super(repository);
    }


    public void createClient(User user) {
        Client client = new Client();
        client.setUser(user);
        save(client);
    }

    public List<Client> listClients() {
        return repository.findAll();
    }

    // findById
    public Client findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
