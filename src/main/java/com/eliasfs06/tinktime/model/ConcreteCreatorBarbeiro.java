package com.eliasfs06.tinktime.model;

public class ConcreteCreatorBarbeiro implements FuncionarioCreator {
    @Override
    public Funcionario createFuncionario() {
        return new Barbeiro();
    }
}
