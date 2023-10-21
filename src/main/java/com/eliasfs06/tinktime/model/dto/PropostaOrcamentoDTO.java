package com.eliasfs06.tinktime.model.dto;

import com.eliasfs06.tinktime.model.PropostaOrcamento;
import com.eliasfs06.tinktime.model.PropostaTatuagem;

public class PropostaOrcamentoDTO {
    private Long id;

    private PropostaTatuagemDTO propostaTatuagem;

    private Float orcamento;

    public PropostaOrcamentoDTO(){}

    public PropostaOrcamentoDTO(PropostaOrcamento propostaOrcamento){
        this.id = propostaOrcamento.getId();
        this.propostaTatuagem = new PropostaTatuagemDTO(propostaOrcamento.getPropostaTatuagem());
        this.orcamento = propostaOrcamento.getOrcamento();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PropostaTatuagemDTO getPropostaTatuagem() {
        return propostaTatuagem;
    }

    public void setPropostaTatuagem(PropostaTatuagemDTO propostaTatuagem) {
        this.propostaTatuagem = propostaTatuagem;
    }

    public Float getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Float orcamento) {
        this.orcamento = orcamento;
    }
}
