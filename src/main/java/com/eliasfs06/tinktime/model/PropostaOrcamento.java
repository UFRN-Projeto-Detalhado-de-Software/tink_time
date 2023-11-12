package com.eliasfs06.tinktime.model;

import com.eliasfs06.tinktime.model.enums.StatusAprovacao;
import jakarta.persistence.*;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "proposta_orcamento")
public class PropostaOrcamento extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "propostatatuagem_id")
    private PropostaIdeia propostaTatuagem;

    private Float orcamento;

    @Nullable
    @Enumerated(EnumType.STRING)
    private StatusAprovacao statusAprovacao;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public PropostaIdeia getPropostaTatuagem() {
        return propostaTatuagem;
    }

    public void setPropostaTatuagem(PropostaIdeia propostaTatuagem) {
        this.propostaTatuagem = propostaTatuagem;
    }

    public Float getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Float orcamento) {
        this.orcamento = orcamento;
    }

    @Nullable
    public StatusAprovacao getStatusAprovacao() {
        return statusAprovacao;
    }

    public void setStatusAprovacao(@Nullable StatusAprovacao statusAprovacao) {
        this.statusAprovacao = statusAprovacao;
    }
}
