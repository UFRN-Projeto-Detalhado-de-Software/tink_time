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
@RequestMapping("/proposta-tatuagem")
public class PropostaIdeiaController {

    @Autowired
    private PropostaIdeiaService propostaTatuagemService;

    @Autowired
    private EmpregadoService artistService;

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
        List<PropostaIdeia> propostasList = propostaTatuagemService.listPropostasByTatuadorID(user.getId());
        modelAndView.addObject("propostasList", propostasList);
        modelAndView.setViewName("propostaTatuagem/list");
        return modelAndView;
    }


    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView();
        List<Empregado> artistas = artistService.listActiveArtists();
        modelAndView.addObject("newTatuagem", new PropostaIdeiaDTO());
        modelAndView.addObject("artistas", artistas);
        modelAndView.setViewName("propostaTatuagem/form");
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@RequestParam(value="descricao", required = true) String Descricao, @RequestParam(value="tatuador", required = true) String Tatuador,
                         Model model) throws BusinessException {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User artist = userService.findByID(Long.parseLong(Tatuador));
            UserDTO userdto = new UserDTO(user);
            UserDTO artistdto = new UserDTO(artist);
            PropostaIdeiaDTO propostaTatuagemDTO = new PropostaIdeiaDTO();
            propostaTatuagemDTO.setCliente(userdto);
            propostaTatuagemDTO.setTatuador(artistdto);
            propostaTatuagemDTO.setDescricao(Descricao);
            propostaTatuagemService.create(propostaTatuagemDTO);
        } catch (BusinessException e) {
            return "redirect:/index";
        }
        return "redirect:/index";
    }

    @GetMapping("/getPropostasByCliente")
    @ResponseBody
    public List<PropostaIdeia> getPropostasByCliente(@RequestParam Long clienteId) {
        return propostaTatuagemService.listPropostasByClienteID(clienteId);
    }

    @GetMapping("/buscar-horarios/{id}")
    public String agendarTatuagem(@PathVariable Long id, Model model){
        PropostaIdeia propostaTatuagem = propostaTatuagemService.get(id);
        EmpregadoDTO artistdto = artistService.findByUser(propostaTatuagem.getTatuador());
        Empregado artist = artistdto.toArtist();
        Agenda agenda = agendaService.findByArtist(artist);

        List<HorariosTatuagem> horariosDisponveis = agendaService.sugerirHorarios(artist, propostaTatuagem.getNumeroSessoes());
        List<HorariosTatuagem> horariosDisponiveisFormatados = agendaService.formatarHorariosDisponiveis(horariosDisponveis, propostaTatuagem.getNumeroSessoes());

        model.addAttribute("horarios", horariosDisponiveisFormatados);
        model.addAttribute("agendaId", agenda.getId());
        model.addAttribute("propostaId", id);
        model.addAttribute("agendamento", new AgendamentoDto());
        return "/propostaTatuagem/agendar-tatuagem";
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

        PropostaIdeia propostaTatuagem = propostaTatuagemService.get(idProposta);
        propostaTatuagem.setAgendamento(agendamento);
        propostaTatuagemService.save(propostaTatuagem);

        return "redirect:/index";
    }
}
