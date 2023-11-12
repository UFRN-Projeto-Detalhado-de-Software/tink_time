package com.eliasfs06.tinktime.model;

import com.eliasfs06.tinktime.model.enums.StatusAprovacao;
import jakarta.persistence.*;

@Entity
public class PropostaExecucao extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] desenho;

    @ManyToOne
    @JoinColumn(name = "proposta_orcamento_id")
    private PropostaOrcamento propostaOrcamento;

    @Enumerated(EnumType.STRING)
    private StatusAprovacao statusAprovacao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getDesenho() {
        return desenho;
    }

    public void setDesenho(byte[] desenho) {
        this.desenho = desenho;
    }

    public PropostaOrcamento getPropostaOrcamento() {
        return propostaOrcamento;
    }

    public void setPropostaOrcamento(PropostaOrcamento propostaOrcamento) {
        this.propostaOrcamento = propostaOrcamento;
    }

    public StatusAprovacao getStatusAprovacao() {
        return statusAprovacao;
    }

    public void setStatusAprovacao(StatusAprovacao statusAprovacao) {
        this.statusAprovacao = statusAprovacao;
    }

}
