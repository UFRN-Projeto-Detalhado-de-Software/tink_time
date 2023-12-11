package com.eliasfs06.tinktime.model;

import jakarta.persistence.Entity;

@Entity
public class BodyPiercer extends Funcionario {

    String estudio;

    String areasAplicacao;

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public String getAreasAplicacao() {
        return areasAplicacao;
    }

    public void setAreasAplicacao(String areasAplicacao) {
        this.areasAplicacao = areasAplicacao;
    }
}
