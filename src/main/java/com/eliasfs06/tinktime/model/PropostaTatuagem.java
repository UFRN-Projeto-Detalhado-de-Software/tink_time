package com.eliasfs06.tinktime.model;

import jakarta.persistence.*;

@Entity
public class PropostaTatuagem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private User cliente;

    @ManyToOne
    @JoinColumn(name = "tatuador_id")
    private User tatuador;

    private String descricao;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCliente() {
        return cliente;
    }

    public void setCliente(User cliente) {
        this.cliente = cliente;
    }

    public User getTatuador() {
        return tatuador;
    }

    public void setTatuador(User tatuador) {
        this.tatuador = tatuador;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}