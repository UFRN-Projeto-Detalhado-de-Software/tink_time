package com.eliasfs06.tinktime.model;

import com.eliasfs06.tinktime.exceptionsHandler.BusinessException;
import com.eliasfs06.tinktime.model.dto.PropostaIdeiaDTO;
import com.eliasfs06.tinktime.repository.GenericRepository;

public abstract class ValidadorDeIdeia {

    private final GenericRepository<User> userRepository;

    public ValidadorDeIdeia(GenericRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    public boolean validar(PropostaIdeiaDTO propostaIdeiaDTO) throws BusinessException {
        return validarCamposComuns(propostaIdeiaDTO) && validarCamposEspecificos(propostaIdeiaDTO);
    }

    public boolean validarCamposComuns(PropostaIdeiaDTO propostaIdeiaDTO) throws BusinessException {
        User cliente = userRepository.findById(propostaIdeiaDTO.getCliente().getId()).orElse(null);
        User tatuador = userRepository.findById(propostaIdeiaDTO.getTatuador().getId()).orElse(null);
        if (cliente == null || tatuador == null || propostaIdeiaDTO.getDescricao() == null) {
            throw new BusinessException("Criação de proposta inválida");
        }
        return true;
    }

    public abstract boolean validarCamposEspecificos(PropostaIdeiaDTO propostaIdeiaDTO) throws BusinessException;
}
