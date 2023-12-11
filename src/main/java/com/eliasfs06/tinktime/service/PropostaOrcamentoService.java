package com.eliasfs06.tinktime.service;

import com.eliasfs06.tinktime.exceptionsHandler.BusinessException;
import com.eliasfs06.tinktime.model.PropostaIdeia;
import com.eliasfs06.tinktime.model.PropostaOrcamento;
import com.eliasfs06.tinktime.model.User;
import com.eliasfs06.tinktime.model.dto.PropostaOrcamentoDTO;
import com.eliasfs06.tinktime.model.enums.StatusAprovacao;
import com.eliasfs06.tinktime.repository.GenericRepository;
import com.eliasfs06.tinktime.repository.PropostaOrcamentoRepository;
import com.eliasfs06.tinktime.repository.PropostaIdeiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PropostaOrcamentoService extends GenericService<PropostaOrcamento> {

    @Autowired
    private PropostaOrcamentoRepository propostaOrcamentoRepository;

    @Autowired
    private GenericRepository<User> userRepository;

    @Autowired
    private PropostaIdeiaRepository propostaIdeiaRepository;

    public PropostaOrcamentoService(GenericRepository<PropostaOrcamento> repository) {
        super(repository);
    }

    @Transactional
    public PropostaOrcamento create(PropostaOrcamentoDTO propostaOrcamentoDTO) throws BusinessException {
        PropostaOrcamento propostaOrcamento = new PropostaOrcamento();

        if (propostaOrcamentoDTO.getOrcamento() == null)
            throw new BusinessException("Orçamento não pode ser nulo");

        if (propostaOrcamentoDTO.getPropostaIdeia().getCliente() == null || propostaOrcamentoDTO.getPropostaIdeia().getTatuador() == null || propostaOrcamentoDTO.getPropostaIdeia().getDescricao() == null)
            throw new BusinessException("Proposta de tatuagem do orçamento não está bem estruturada");

        User cliente = userRepository.findById(propostaOrcamentoDTO.getPropostaIdeia().getCliente().getId()).orElse(null);
        User tatuador = userRepository.findById(propostaOrcamentoDTO.getPropostaIdeia().getTatuador().getId()).orElse(null);
        String descricao = propostaOrcamentoDTO.getPropostaIdeia().getDescricao();

        if (cliente == null || tatuador == null) {
            throw new BusinessException("Cliente ou tatuador inválidos");
        }

        PropostaIdeia propostaIdeia = propostaIdeiaRepository.findPropostaIdeiaByClienteAndTatuadorAndDescricao(cliente, tatuador, descricao);

        if (propostaIdeia == null) {
            throw new BusinessException("Proposta de tatuagem inválida");
        }

        propostaOrcamento.setPropostaIdeia(propostaIdeia);
        propostaOrcamento.setOrcamento(propostaOrcamentoDTO.getOrcamento());
        propostaOrcamento.setStatusAprovacao(StatusAprovacao.PENDENTE);

        propostaOrcamentoRepository.save(propostaOrcamento);

        return propostaOrcamento;
    }

    public PropostaOrcamento aprovar(Long id) throws BusinessException {
        PropostaOrcamento propostaOrcamento = propostaOrcamentoRepository.findById(id).orElse(null);
        if (propostaOrcamento == null) {
            throw new BusinessException("Proposta de orçamento inválida");
        }

        propostaOrcamento.setStatusAprovacao(StatusAprovacao.APROVADO);

        propostaOrcamentoRepository.save(propostaOrcamento);

        return propostaOrcamento;
    }

    public PropostaOrcamento recusar(Long id) throws BusinessException {
        PropostaOrcamento propostaOrcamento = propostaOrcamentoRepository.findById(id).orElse(null);
        if (propostaOrcamento == null) {
            throw new BusinessException("Proposta de orçamento inválida");
        }

        propostaOrcamento.setStatusAprovacao(StatusAprovacao.REPROVADO);

        propostaOrcamentoRepository.save(propostaOrcamento);

        return propostaOrcamento;
    }

    public PropostaOrcamentoDTO findById(Long id){
        return new PropostaOrcamentoDTO(Objects.requireNonNull(propostaOrcamentoRepository.findById(id).orElse(null)));
    }

    public List<PropostaOrcamento> listPropostasByClienteID(Long id) {
        Optional<List<PropostaOrcamento>> propostaOrcamentoOptional = propostaOrcamentoRepository.findAllByClienteId(id);
        if (propostaOrcamentoOptional.isPresent()) {
            return propostaOrcamentoOptional.get();
        }
        return new ArrayList<>();
    }

    public List<PropostaOrcamento> findAllByStatusAprovacaoAndPropostaIdeiaId(Long ideiaId) {
        Optional<List<PropostaOrcamento>> propostaOrcamentosAprovados = propostaOrcamentoRepository.findAllByStatusAprovacaoAndPropostaIdeiaId(StatusAprovacao.APROVADO.name(), ideiaId);
        return propostaOrcamentosAprovados.orElseGet(ArrayList::new);
    }
}

