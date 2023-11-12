package com.eliasfs06.tinktime.service;

import com.eliasfs06.tinktime.exceptionsHandler.BusinessException;
import com.eliasfs06.tinktime.model.PropostaIdeia;
import com.eliasfs06.tinktime.model.User;
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
    private PropostaIdeiaRepository propostaTatuagemRepository;

    @Autowired
    private GenericRepository<User> userRepository;

    public PropostaIdeiaService(GenericRepository<PropostaIdeia> repository) {
        super(repository);
    }

    public PropostaIdeiaDTO findById(Long id){
        return new PropostaIdeiaDTO(Objects.requireNonNull(propostaTatuagemRepository.findById(id).orElse(null)));
    }

    @Transactional
    public PropostaIdeia create(PropostaIdeiaDTO propostaTatuagemDTO) throws BusinessException {
        PropostaIdeia propostaTatuagem = new PropostaIdeia();

        if (propostaTatuagemDTO.getCliente() == null || propostaTatuagemDTO.getTatuador() == null) {
            throw new BusinessException("Cliente ou tatuador não pode ser nulo");
        }
        User cliente = userRepository.findById(propostaTatuagemDTO.getCliente().getId()).orElse(null);
        User tatuador = userRepository.findById(propostaTatuagemDTO.getTatuador().getId()).orElse(null);

        if (cliente == null || tatuador == null) {
            throw new BusinessException("Cliente ou tatuador não encontrado");
        }

        propostaTatuagem.setCliente(cliente);
        propostaTatuagem.setTatuador(tatuador);
        propostaTatuagem.setDescricao(propostaTatuagemDTO.getDescricao());
        propostaTatuagemRepository.save(propostaTatuagem);

        return propostaTatuagem;
    }

    public List<PropostaIdeia> listPropostasByTatuadorID(Long id){
        Optional<List<PropostaIdeia>> propostas =  propostaTatuagemRepository.findAllByTatuadorId(id);
        if (propostas.isPresent())
            return propostas.get();
        return new ArrayList<>();
    }

    public List<PropostaIdeia> listPropostasByClienteID(Long id){
        Optional<List<PropostaIdeia>> propostas =  propostaTatuagemRepository.findAllByClienteId(id);
        if (propostas.isPresent())
            return propostas.get();
        return new ArrayList<>();
    }

}
