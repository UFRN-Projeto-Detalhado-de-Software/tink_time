package com.eliasfs06.tinktime.restController;

import com.eliasfs06.tinktime.model.Empregado;
import com.eliasfs06.tinktime.repository.GenericRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/artist")
public class EmpregadoRestController extends GenericRestController<Empregado> {
    public EmpregadoRestController(GenericRepository<Empregado> repository) {
        super(repository);
    }
}
