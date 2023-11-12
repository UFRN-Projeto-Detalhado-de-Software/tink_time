package com.eliasfs06.tinktime.controller;

import ch.qos.logback.core.model.Model;
import com.eliasfs06.tinktime.exceptionsHandler.BusinessException;
import com.eliasfs06.tinktime.model.Client;
import com.eliasfs06.tinktime.model.PropostaExecucao;
import com.eliasfs06.tinktime.model.User;
import com.eliasfs06.tinktime.model.dto.PropostaExecucaoDTO;
import com.eliasfs06.tinktime.model.dto.PropostaOrcamentoDTO;
import com.eliasfs06.tinktime.model.dto.PropostaIdeiaDTO;
import com.eliasfs06.tinktime.model.enums.StatusAprovacao;
import com.eliasfs06.tinktime.service.ClientService;
import com.eliasfs06.tinktime.service.PropostaDesenhoService;
import com.eliasfs06.tinktime.service.PropostaOrcamentoService;
import com.eliasfs06.tinktime.service.PropostaIdeiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/proposta-desenho")
public class PropostaExecucaoController {

    @Autowired
    private PropostaDesenhoService propostaDesenhoService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private PropostaIdeiaService propostaTatuagemService;

    @Autowired
    private PropostaOrcamentoService propostaOrcamentoService;

    @GetMapping("/list")
    public ModelAndView listDesenhos() {
        ModelAndView modelAndView = new ModelAndView();
        User cliente = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PropostaExecucao> propostaDesenhos = propostaDesenhoService.listPropostasByClienteID(cliente.getId());

        List<String> imagensBase64 = new ArrayList<>();
        for (PropostaExecucao propostaDesenho : propostaDesenhos) {
            byte[] imagemByte = propostaDesenho.getDesenho();
            String imagemBase64 = java.util.Base64.getEncoder().encodeToString(imagemByte);
            imagensBase64.add(imagemBase64);
        }

        modelAndView.addObject("propostaDesenhos", propostaDesenhos);
        modelAndView.addObject("imagensBase64", imagensBase64);
        modelAndView.setViewName("propostaDesenho/list");
        return modelAndView;
    }

    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView();
        List<Client> clientes = clientService.listClients();
        modelAndView.addObject("clientes", clientes);
        modelAndView.addObject("newDesenho", new PropostaExecucao());
        modelAndView.setViewName("propostaDesenho/form");
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@RequestParam(value="propostaOrcamento.propostaTatuagem", required = true) String tatuagem,
                         @RequestParam(value="propostaOrcamento", required = true) String orcamento,
                         PropostaExecucao propostaDesenho,
                         Model model) throws BusinessException {
        try {
            PropostaExecucaoDTO propostaDesenhoDTO = new PropostaExecucaoDTO();

            PropostaIdeiaDTO propostaTatuagem = propostaTatuagemService.findById(Long.parseLong(tatuagem));
            PropostaOrcamentoDTO propostaOrcamento = propostaOrcamentoService.findById(Long.parseLong(orcamento));

            propostaOrcamento.setPropostaTatuagem(propostaTatuagem);
            propostaDesenhoDTO.setPropostaOrcamento(propostaOrcamento);
            propostaDesenhoDTO.setDesenho(propostaDesenho.getDesenho());
            propostaDesenhoDTO.setStatusAprovacao(StatusAprovacao.PENDENTE.name());

            propostaDesenhoService.create(propostaDesenhoDTO);

        } catch (BusinessException e) {
            return "redirect:/index";
        }
        return "redirect:/index";
    }

    @GetMapping("/recusar/{id}")
    public String recusar(@PathVariable("id") Long id){
        try {
            propostaDesenhoService.recusar(id);
            return "redirect:/proposta-desenho/list";
        } catch (BusinessException e) {
            return "redirect:/index";
        }
    }

    @GetMapping("/aprovar/{id}")
    public String aprovar(@PathVariable("id") Long id){
        try {
            propostaDesenhoService.aprovar(id);
            return "redirect:/proposta-desenho/list";
        } catch (BusinessException e) {
            return "redirect:/index";
        }
    }

}
