package com.eliasfs06.tinktime.model.dto;

import com.eliasfs06.tinktime.model.PropostaIdeia;

public class PropostaIdeiaDTO {

    private Long id;

    private UserDTO cliente;

    private UserDTO tatuador;

    private String descricao;

    public PropostaIdeiaDTO() {
    }

    public PropostaIdeiaDTO(PropostaIdeia propostaIdeia) {
        this.id = propostaIdeia.getId();
        this.cliente = new UserDTO(propostaIdeia.getCliente());
        this.tatuador = new UserDTO(propostaIdeia.getTatuador());
        this.descricao = propostaIdeia.getDescricao();
    }

    public PropostaIdeiaDTO(UserDTO cliente, UserDTO tatuador, String descricao) {
        this.cliente = cliente;
        this.tatuador = tatuador;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getCliente() {
        return cliente;
    }

    public void setCliente(UserDTO cliente) {
        this.cliente = cliente;
    }

    public UserDTO getTatuador() {
        return tatuador;
    }

    public void setTatuador(UserDTO tatuador) {
        this.tatuador = tatuador;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
