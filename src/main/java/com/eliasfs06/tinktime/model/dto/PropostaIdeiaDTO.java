package com.eliasfs06.tinktime.model.dto;

import com.eliasfs06.tinktime.model.PropostaIdeia;

public class PropostaIdeiaDTO {

    private Long id;

    private UserDTO cliente;

    private UserDTO tatuador;

    private String descricao;

    public PropostaIdeiaDTO() {
    }

    public PropostaIdeiaDTO(PropostaIdeia propostaTatuagem) {
        this.id = propostaTatuagem.getId();
        this.cliente = new UserDTO(propostaTatuagem.getCliente());
        this.tatuador = new UserDTO(propostaTatuagem.getTatuador());
        this.descricao = propostaTatuagem.getDescricao();
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
