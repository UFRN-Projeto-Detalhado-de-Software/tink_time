package com.eliasfs06.tinktime.service;

import com.eliasfs06.tinktime.exceptionsHandler.BusinessException;
import com.eliasfs06.tinktime.model.PropostaDesenho;
import com.eliasfs06.tinktime.model.PropostaOrcamento;
import com.eliasfs06.tinktime.model.dto.PropostaDesenhoDTO;
import com.eliasfs06.tinktime.model.enums.StatusAprovacao;
import com.eliasfs06.tinktime.repository.GenericRepository;
import com.eliasfs06.tinktime.repository.PropostaDesenhoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropostaDesenhoService extends GenericService<PropostaDesenho> {

    private final PropostaOrcamentoService propostaOrcamentoService;

    private final PropostaDesenhoRepository propostaDesenhoRepository;

    public PropostaDesenhoService(GenericRepository<PropostaDesenho> repository,
                                  PropostaOrcamentoService propostaOrcamentoService,
                                  PropostaDesenhoRepository propostaDesenhoRepository) {
        super(repository);
        this.propostaOrcamentoService = propostaOrcamentoService;
        this.propostaDesenhoRepository = propostaDesenhoRepository;
    }
    @Transactional
    public PropostaDesenho create (PropostaDesenhoDTO propostaDesenhoDTO) throws BusinessException {
        PropostaDesenho propostaDesenho = new PropostaDesenho();
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
    public List<PropostaDesenho> listPropostasByClienteID(Long clienteId) {
        Optional<List<PropostaDesenho>> propostaDesenhos = propostaDesenhoRepository.findAllByClienteId(clienteId);
        return propostaDesenhos.orElseGet(ArrayList::new);
    }

    public PropostaDesenho aprovar(Long id) throws BusinessException {
        PropostaDesenho propostaDesenho = propostaDesenhoRepository.findById(id).orElse(null);
        if (propostaDesenho == null) {
            throw new BusinessException("Proposta de orçamento inválida");
        }

        propostaDesenho.setStatusAprovacao(StatusAprovacao.APROVADO);

        propostaDesenhoRepository.save(propostaDesenho);

        return propostaDesenho;
    }

    public PropostaDesenho recusar(Long id) throws BusinessException {
        PropostaDesenho propostaDesenho = propostaDesenhoRepository.findById(id).orElse(null);
        if (propostaDesenho == null) {
            throw new BusinessException("Proposta de orçamento inválida");
        }

        propostaDesenho.setStatusAprovacao(StatusAprovacao.REPROVADO);

        propostaDesenhoRepository.save(propostaDesenho);

        return propostaDesenho;
    }

    public List<String> listImagensBase64(List<PropostaDesenho> propostaDesenhos) {
        List<String> imagensBase64 = new ArrayList<>();
        for (PropostaDesenho propostaDesenho : propostaDesenhos) {
            byte[] imagemByte = propostaDesenho.getDesenho();
            String imagemBase64 = java.util.Base64.getEncoder().encodeToString(imagemByte);
            imagensBase64.add(imagemBase64);
        }
        return imagensBase64;
    }

}
