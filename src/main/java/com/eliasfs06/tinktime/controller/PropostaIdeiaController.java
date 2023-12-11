package com.eliasfs06.tinktime.controller;

import com.eliasfs06.tinktime.exceptionsHandler.BusinessException;
import com.eliasfs06.tinktime.model.*;
import com.eliasfs06.tinktime.model.dto.*;
import com.eliasfs06.tinktime.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/proposta-ideia")
public class PropostaIdeiaController {

    @Autowired
    private PropostaIdeiaService propostaIdeiaService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private UserService userService;

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private DiaAgendaService diaAgendaService;

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private AgendamentoService agendamentoService;

    @GetMapping("/list")
    public ModelAndView listTatuagens(){
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PropostaIdeia> propostasList = propostaIdeiaService.getPropostasByRole(user);
        modelAndView.addObject("propostasList", propostasList);
        modelAndView.setViewName("propostaIdeia/list");
        return modelAndView;
    }


    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView();
        List<Funcionario> funcionarios = funcionarioService.listActiveFuncionarios();
        modelAndView.addObject("newIdeia", new PropostaIdeiaDTO());
        modelAndView.addObject("funcionarios", funcionarios);
        modelAndView.setViewName("propostaIdeia/form");
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@RequestParam(value="descricao", required = true) String Descricao, @RequestParam(value="tatuador", required = true) String Funcionario,
                         Model model) throws BusinessException {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User funcionario = userService.findByID(Long.parseLong(Funcionario));
            propostaIdeiaService.create(new PropostaIdeiaDTO(new UserDTO(user), new UserDTO(funcionario), Descricao));
        } catch (BusinessException e) {
            return "redirect:/index";
        }
        return "redirect:/index";
    }

    @GetMapping("/getPropostasByCliente")
    @ResponseBody
    public List<PropostaIdeia> getPropostasByCliente(@RequestParam Long clienteId) {
        return propostaIdeiaService.listPropostasByClienteID(clienteId);
    }

    @GetMapping("/buscar-horarios/{id}")
    public String agendarTatuagem(@PathVariable Long id, Model model){
        PropostaIdeia propostaIdeia = propostaIdeiaService.get(id);
        FuncionarioDTO funcionarioDTO = funcionarioService.findByUser(propostaIdeia.getTatuador());
        Funcionario funcionario = funcionarioDTO.toFuncionario();
        Agenda agenda = agendaService.findByFuncionario(funcionario);

        List<HorariosTatuagem> horariosDisponveis = agendaService.sugerirHorarios(funcionario, propostaIdeia.getNumeroSessoes());
        List<HorariosTatuagem> horariosDisponiveisFormatados = agendaService.formatarHorariosDisponiveis(horariosDisponveis, propostaIdeia.getNumeroSessoes());

        model.addAttribute("horarios", horariosDisponiveisFormatados);
        model.addAttribute("agendaId", agenda.getId());
        model.addAttribute("propostaId", id);
        model.addAttribute("agendamento", new AgendamentoDto());
        return "/propostaIdeia/agendar-ideia";
    }

    @PostMapping("/agendar-tatuagem/{idProposta}/{idAgenda}")
    public String agendarTatuagem(@PathVariable Long idProposta, @PathVariable Long idAgenda, @ModelAttribute AgendamentoDto horariosTatuagem){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dia = LocalDate.parse(horariosTatuagem.getDiaAgenda(), formatter);
        DiaAgenda diaAgenda = diaAgendaService.findByDiaEAgenda(idAgenda, dia);
        List<Horario> horarios = horarioService.filtrarHorarios(diaAgenda, horariosTatuagem.getHoraIncio(), horariosTatuagem.getHoraFim());
        for(Horario horario : horarios){
            horario.setStatusHorario(StatusHorario.RESERVADO);
            horarioService.save(horario);
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setHorarios(horarios);
        agendamento.setData(diaAgenda.getDia());
        agendamentoService.save(agendamento);

        PropostaIdeia propostaIdeia = propostaIdeiaService.get(idProposta);
        propostaIdeia.setAgendamento(agendamento);
        propostaIdeiaService.save(propostaIdeia);

        return "redirect:/index";
    }
}
