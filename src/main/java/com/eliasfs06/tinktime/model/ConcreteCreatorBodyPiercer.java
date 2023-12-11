package com.eliasfs06.tinktime.model;

public class ConcreteCreatorBodyPiercer implements FuncionarioCreator {
    @Override
    public Funcionario createFuncionario() {
        return new BodyPiercer();
    }
}
