package com.eliasfs06.tinktime.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Tatuador extends Funcionario {

    private String estudio;

    @ElementCollection
    @CollectionTable(name = "styles", joinColumns = @JoinColumn(name = "artist_id"))
    @Enumerated(EnumType.STRING)
    private List<TattoStyle> styles;

    public Tatuador() {
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public List<TattoStyle> getStyles() {
        return styles;
    }

    public void setStyles(List<TattoStyle> styles) {
        this.styles = styles;
    }
}
