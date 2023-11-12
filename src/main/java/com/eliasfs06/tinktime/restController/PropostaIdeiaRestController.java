package com.eliasfs06.tinktime.restController;

import com.eliasfs06.tinktime.exceptionsHandler.BusinessException;
import com.eliasfs06.tinktime.model.PropostaIdeia;
import com.eliasfs06.tinktime.model.dto.PropostaIdeiaDTO;
import com.eliasfs06.tinktime.repository.GenericRepository;
import com.eliasfs06.tinktime.service.PropostaIdeiaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/proposta-tatuagem")
public class PropostaIdeiaRestController extends GenericRestController<PropostaIdeia> {

    private final PropostaIdeiaService propostaTatuagemService;

    public PropostaIdeiaRestController(GenericRepository<PropostaIdeia> propostaTatuagemGenericRepository,
                                       PropostaIdeiaService propostaTatuagemService) {
        super(propostaTatuagemGenericRepository);
        this.propostaTatuagemService = propostaTatuagemService;
    }

    @Override
    @PostMapping("")
    public ResponseEntity<PropostaIdeia> create(@RequestBody PropostaIdeia created) {
        try {
            return ResponseEntity.ok(propostaTatuagemService.create(new PropostaIdeiaDTO(created)));
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
