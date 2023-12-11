package com.eliasfs06.tinktime.service;

import com.eliasfs06.tinktime.exceptionsHandler.BusinessException;
import com.eliasfs06.tinktime.model.PropostaIdeia;
import com.eliasfs06.tinktime.model.User;
import com.eliasfs06.tinktime.model.UserRole;
import com.eliasfs06.tinktime.model.dto.PropostaIdeiaDTO;
import com.eliasfs06.tinktime.repository.GenericRepository;
import com.eliasfs06.tinktime.repository.PropostaIdeiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PropostaIdeia create(PropostaIdeiaDTO propostaIdeiaDTO) throws BusinessException {
        PropostaIdeia propostaIdeia = new PropostaIdeia();

        if (propostaIdeiaDTO.getCliente() == null || propostaIdeiaDTO.getTatuador() == null) {
            throw new BusinessException("Cliente ou tatuador não pode ser nulo");
        }
        User cliente = userRepository.findById(propostaIdeiaDTO.getCliente().getId()).orElse(null);
        User tatuador = userRepository.findById(propostaIdeiaDTO.getTatuador().getId()).orElse(null);

        if (cliente == null || tatuador == null) {
            throw new BusinessException("Cliente ou tatuador não encontrado");
        }

        propostaIdeia.setCliente(cliente);
        propostaIdeia.setTatuador(tatuador);
        propostaIdeia.setDescricao(propostaIdeiaDTO.getDescricao());
        propostaIdeiaRepository.save(propostaIdeia);

        return propostaIdeia;
    }

    public List<PropostaIdeia> listPropostasByTatuadorID(Long id){
        Optional<List<PropostaIdeia>> propostas =  propostaIdeiaRepository.findAllByTatuadorId(id);
        if (propostas.isPresent())
            return propostas.get();
        return new ArrayList<>();
    }

    public List<PropostaIdeia> listPropostasByClienteID(Long id){
        Optional<List<PropostaIdeia>> propostas =  propostaIdeiaRepository.findAllByClienteId(id);
        if (propostas.isPresent())
            return propostas.get();
        return new ArrayList<>();
    }

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
