package com.eliasfs06.tinktime.model.dto;

import com.eliasfs06.tinktime.model.TattooStyle;
import com.eliasfs06.tinktime.model.Tatuador;

import java.util.List;

public class TatuadorDTO extends FuncionarioDTO {

    private String estudio;

    private List<TattooStyle> styles;


    TatuadorDTO () {
        super();
    }

    public TatuadorDTO (Tatuador tatuador) {
        super(tatuador);
        this.estudio = ((Tatuador) tatuador).getEstudio();
        this.styles = ((Tatuador) tatuador).getStyles();
    }

    public Tatuador toTatuador() {
        Tatuador tatuador = (Tatuador) super.toFuncionario();
        tatuador.setEstudio(this.estudio);
        tatuador.setStyles(this.styles);
        return tatuador;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public List<TattooStyle> getStyles() {
        return styles;
    }

    public void setStyles(List<TattooStyle> styles) {
        this.styles = styles;
    }
}
