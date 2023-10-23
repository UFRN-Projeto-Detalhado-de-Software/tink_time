package com.eliasfs06.tinktime.service;

import com.eliasfs06.tinktime.exceptionsHandler.BusinessException;
import com.eliasfs06.tinktime.model.PropostaDesenho;
import com.eliasfs06.tinktime.model.PropostaOrcamento;
import com.eliasfs06.tinktime.model.dto.PropostaDesenhoDTO;
import com.eliasfs06.tinktime.model.enums.StatusAprovacao;
import com.eliasfs06.tinktime.repository.GenericRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropostaDesenhoService extends GenericService<PropostaDesenho> {

    private final PropostaOrcamentoService propostaOrcamentoService;

    public PropostaDesenhoService(GenericRepository<PropostaDesenho> repository,
                                  PropostaOrcamentoService propostaOrcamentoService) {
        super(repository);
        this.propostaOrcamentoService = propostaOrcamentoService;
    }
    @Transactional
    public PropostaDesenho create (PropostaDesenhoDTO propostaDesenhoDTO) throws BusinessException {
        PropostaDesenho propostaDesenho = new PropostaDesenho();
        if (propostaDesenhoDTO.getDesenho() == null) {
            throw new BusinessException("O desenho não pode ser vazio");
        }
        propostaDesenho.setDesenho(propostaDesenho.getDesenho());

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
}