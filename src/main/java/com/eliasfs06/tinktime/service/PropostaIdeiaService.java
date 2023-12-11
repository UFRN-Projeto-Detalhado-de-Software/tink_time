package com.eliasfs06.tinktime.service;

import com.eliasfs06.tinktime.exceptionsHandler.BusinessException;
import com.eliasfs06.tinktime.model.PropostaIdeia;
import com.eliasfs06.tinktime.model.User;
import com.eliasfs06.tinktime.model.UserRole;
import com.eliasfs06.tinktime.model.ValidadorDeIdeiaTatuagem;
import com.eliasfs06.tinktime.model.dto.PropostaIdeiaDTO;
import com.eliasfs06.tinktime.repository.GenericRepository;
import com.eliasfs06.tinktime.repository.PropostaIdeiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PropostaIdeiaService extends GenericService<PropostaIdeia> {

    @Autowired
    private PropostaIdeiaRepository propostaIdeiaRepository;

    @Autowired
    private GenericRepository<User> userRepository;

    public PropostaIdeiaService(GenericRepository<PropostaIdeia> repository) {
        super(repository);
    }

    public PropostaIdeiaDTO findById(Long id){
        return new PropostaIdeiaDTO(Objects.requireNonNull(propostaIdeiaRepository.findById(id).orElse(null)));
    }

    @Transactional
    public PropostaIdeia create(PropostaIdeiaDTO propostaIdeiaDTO) throws BusinessException, ParseException {
        PropostaIdeia propostaIdeia = new PropostaIdeia();
        ValidadorDeIdeiaTatuagem validadorDeIdeiaTatuagem = new ValidadorDeIdeiaTatuagem(userRepository);

        if (!validadorDeIdeiaTatuagem.validar(propostaIdeiaDTO)) {
            throw new BusinessException("Criação de proposta inválida");
        }

        propostaIdeia = propostaIdeiaDTO.toPropostaIdeia();
        save(propostaIdeia);

        return propostaIdeia;
    }

    @Transactional
    public List<PropostaIdeia> listPropostasByTatuadorID(Long id){
        Optional<List<PropostaIdeia>> propostas =  propostaIdeiaRepository.findAllByTatuadorId(id);
        if (propostas.isPresent())
            return propostas.get();
        return new ArrayList<>();
    }

    @Transactional
    public List<PropostaIdeia> listPropostasByClienteID(Long id){
        Optional<List<PropostaIdeia>> propostas =  propostaIdeiaRepository.findAllByClienteId(id);
        if (propostas.isPresent())
            return propostas.get();
        return new ArrayList<>();
    }

    @Transactional
    public List<PropostaIdeia> getPropostasByRole(User user){
        List<PropostaIdeia> propostasList = new ArrayList<>();
        if(user.getUserRole() == UserRole.EMPLOYEE) {
            propostasList = listPropostasByTatuadorID(user.getId());
        } else {
            propostasList = listPropostasByClienteID(user.getId());
        }
        return propostasList;
    }

}
