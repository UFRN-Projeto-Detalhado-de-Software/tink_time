package com.eliasfs06.tinktime.model;

import com.eliasfs06.tinktime.exceptionsHandler.BusinessException;
import com.eliasfs06.tinktime.model.dto.PropostaIdeiaDTO;
import com.eliasfs06.tinktime.repository.GenericRepository;
import com.eliasfs06.tinktime.repository.UserRepository;

public class ValidadorDeIdeiaTatuagem extends ValidadorDeIdeia{
    public ValidadorDeIdeiaTatuagem(GenericRepository<User> userRepository) {
        super(userRepository);
    }

    @Override
    public boolean validarCamposEspecificos(PropostaIdeiaDTO propostaIdeiaDTO) throws BusinessException {
        if (propostaIdeiaDTO.getReferencia() == null){
            throw new BusinessException("Criação de proposta inválida");
        }
        return true;
    }
}
