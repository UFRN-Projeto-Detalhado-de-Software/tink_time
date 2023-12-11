package com.eliasfs06.tinktime.model;

import com.eliasfs06.tinktime.service.suggestionObserver.TargetClient;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Client extends BaseEntity implements TargetClient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToMany
    List<Funcionario> suggestedEmployees = new ArrayList<>();

    private static int MAX_SUGESTION_LIST_SIZE = 10;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Funcionario> getSuggestedEmployees() {
        return suggestedEmployees;
    }

    public void setSuggestedEmployees(List<Funcionario> suggestedEmployees) {
        this.suggestedEmployees = suggestedEmployees;
    }

    @Override
    public void updateClientsSugestion(Funcionario funcionario) {
        if(suggestedEmployees.size() > MAX_SUGESTION_LIST_SIZE){
            suggestedEmployees.remove(0);
        }
        suggestedEmployees.add(funcionario);
    }
}
