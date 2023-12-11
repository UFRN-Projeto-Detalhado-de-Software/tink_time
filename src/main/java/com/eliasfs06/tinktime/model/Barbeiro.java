package com.eliasfs06.tinktime.model;

import jakarta.persistence.Entity;

@Entity
public class Barbeiro extends Funcionario {

    String barbearia;

    String tiposServico;

    public String getBarbearia() {
        return barbearia;
    }

    public void setBarbearia(String barbearia) {
        this.barbearia = barbearia;
    }

    public String getTiposServico() {
        return tiposServico;
    }

    public void setTiposServico(String tiposServico) {
        this.tiposServico = tiposServico;
    }
}
