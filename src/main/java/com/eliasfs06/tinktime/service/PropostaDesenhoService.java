package com.eliasfs06.tinktime.service;

import com.eliasfs06.tinktime.exceptionsHandler.BusinessException;
import com.eliasfs06.tinktime.model.PropostaExecucao;
import com.eliasfs06.tinktime.model.PropostaOrcamento;
import com.eliasfs06.tinktime.model.dto.PropostaExecucaoDTO;
import com.eliasfs06.tinktime.model.enums.StatusAprovacao;
import com.eliasfs06.tinktime.repository.GenericRepository;
import com.eliasfs06.tinktime.repository.PropostaExecucaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropostaDesenhoService extends GenericService<PropostaExecucao> {

    private final PropostaOrcamentoService propostaOrcamentoService;

    private final PropostaExecucaoRepository propostaDesenhoRepository;

    public PropostaDesenhoService(GenericRepository<PropostaExecucao> repository,
                                  PropostaOrcamentoService propostaOrcamentoService,
                                  PropostaExecucaoRepository propostaDesenhoRepository) {
        super(repository);
        this.propostaOrcamentoService = propostaOrcamentoService;
        this.propostaDesenhoRepository = propostaDesenhoRepository;
    }
    @Transactional
    public PropostaExecucao create (PropostaExecucaoDTO propostaDesenhoDTO) throws BusinessException {
        PropostaExecucao propostaDesenho = new PropostaExecucao();
        if (propostaDesenhoDTO.getDesenho() == null) {
            throw new BusinessException("O desenho não pode ser vazio");
        }
        propostaDesenho.setDesenho(propostaDesenhoDTO.getDesenho());

        if (propostaDesenhoDTO.getPropostaOrcamento() == null) {
            throw new BusinessException("A proposta de orçamento associada não pode ser vazia");
        }
        PropostaOrcamento propostaOrcamento = propostaOrcamentoService.get(propostaDesenhoDTO.getPropostaOrcamento().getId());
        propostaOrcamento.setId(propostaDesenhoDTO.getPropostaOrcamento().getId());
        propostaDesenho.setPropostaOrcamento(propostaOrcamento);

        propostaDesenho.setStatusAprovacao(StatusAprovacao.PENDENTE);

        save(propostaDesenho);
        return propostaDesenho;
    }

    @Transactional
    public List<PropostaExecucao> listPropostasByClienteID(Long clienteId) {
        Optional<List<PropostaExecucao>> propostaDesenhos = propostaDesenhoRepository.findAllByClienteId(clienteId);
        return propostaDesenhos.orElseGet(ArrayList::new);
    }

    public PropostaExecucao aprovar(Long id) throws BusinessException {
        PropostaExecucao propostaDesenho = propostaDesenhoRepository.findById(id).orElse(null);
        if (propostaDesenho == null) {
            throw new BusinessException("Proposta de orçamento inválida");
        }

        propostaDesenho.setStatusAprovacao(StatusAprovacao.APROVADO);

        propostaDesenhoRepository.save(propostaDesenho);

        return propostaDesenho;
    }

    public PropostaExecucao recusar(Long id) throws BusinessException {
        PropostaExecucao propostaDesenho = propostaDesenhoRepository.findById(id).orElse(null);
        if (propostaDesenho == null) {
            throw new BusinessException("Proposta de orçamento inválida");
        }

        propostaDesenho.setStatusAprovacao(StatusAprovacao.REPROVADO);

        propostaDesenhoRepository.save(propostaDesenho);

        return propostaDesenho;
    }

}
