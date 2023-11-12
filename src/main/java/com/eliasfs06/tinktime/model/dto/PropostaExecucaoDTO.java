package com.eliasfs06.tinktime.model.dto;

import com.eliasfs06.tinktime.model.PropostaExecucao;

public class PropostaExecucaoDTO {

    private Long id;

    private byte[] desenho;

    private PropostaOrcamentoDTO propostaOrcamento;

    private String statusAprovacao;

    public PropostaExecucaoDTO() {
    }

    public PropostaExecucaoDTO(PropostaExecucao propostaDesenho){
        this.id = propostaDesenho.getId();
        this.desenho = propostaDesenho.getDesenho();
        this.propostaOrcamento = new PropostaOrcamentoDTO(propostaDesenho.getPropostaOrcamento());
        this.statusAprovacao = propostaDesenho.getStatusAprovacao().getDenominacao();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getDesenho() {
        return desenho;
    }

    public void setDesenho(byte[] desenho) {
        this.desenho = desenho;
    }

    public PropostaOrcamentoDTO getPropostaOrcamento() {
        return propostaOrcamento;
    }

    public void setPropostaOrcamento(PropostaOrcamentoDTO propostaOrcamento) {
        this.propostaOrcamento = propostaOrcamento;
    }

    public String getStatusAprovacao() {
        return statusAprovacao;
    }

    public void setStatusAprovacao(String statusAprovacao) {
        this.statusAprovacao = statusAprovacao;
    }
}
