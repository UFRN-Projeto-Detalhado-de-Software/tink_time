package com.eliasfs06.tinktime.restController;

import com.eliasfs06.tinktime.exceptionsHandler.BusinessException;
import com.eliasfs06.tinktime.model.PropostaExecucao;
import com.eliasfs06.tinktime.model.dto.PropostaExecucaoDTO;
import com.eliasfs06.tinktime.repository.GenericRepository;
import com.eliasfs06.tinktime.service.PropostaDesenhoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("proposta-desenho")
public class PropostaExecucaoRestController extends GenericRestController<PropostaExecucao> {

    private final PropostaDesenhoService propostaDesenhoService;

    public PropostaExecucaoRestController(GenericRepository<PropostaExecucao> propostaDesenhoGenericRepository,
                                          PropostaDesenhoService propostaDesenhoService) {
        super(propostaDesenhoGenericRepository);
        this.propostaDesenhoService = propostaDesenhoService;
    }

    @Override
    @PostMapping("")
    public ResponseEntity<PropostaExecucao> create(@RequestBody PropostaExecucao created) {
        try {
            return ResponseEntity.ok(propostaDesenhoService.create(new PropostaExecucaoDTO(created)));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
