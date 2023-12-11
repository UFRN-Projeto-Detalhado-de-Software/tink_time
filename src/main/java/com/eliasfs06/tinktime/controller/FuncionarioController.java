package com.eliasfs06.tinktime.controller;

import com.eliasfs06.tinktime.model.*;
import com.eliasfs06.tinktime.model.dto.FuncionarioDTO;
import com.eliasfs06.tinktime.model.dto.FormCadastroHorarios;
import com.eliasfs06.tinktime.repository.GenericRepository;
import com.eliasfs06.tinktime.service.AgendaService;
import com.eliasfs06.tinktime.service.FuncionarioService;
import com.eliasfs06.tinktime.service.DiaAgendaService;
import com.eliasfs06.tinktime.service.HorarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("funcionario")
public class FuncionarioController extends GenericController<Funcionario> {

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private DiaAgendaService diaAgendaService;

    @Autowired
    private HorarioService horarioService;

    public FuncionarioController(GenericRepository<Funcionario> repository) {
        super(repository);
    }

    @GetMapping("/profile")
    public String getProfile(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FuncionarioDTO funcionario = funcionarioService.findByUser(user);

        if(funcionario.getId() == null){
            funcionario.setUser(user);
        }

        model.addAttribute("funcionario", funcionario);
        return "funcionario/profile";
    }

    @PostMapping("/profile")
    public String saveProfile(@ModelAttribute("funcionario") @Valid FuncionarioDTO funcionario, BindingResult br, Model model){
        if(br.hasErrors()){
            return "funcionario/profile";
        }

        funcionarioService.save(funcionario.toFuncionario());
        model.addAttribute("funcionario", funcionario);
        model.addAttribute("allStyles", TattoStyle.getAllStyles());

        return "funcionario/profile";
    }

    @GetMapping("/list")
    public ModelAndView listFuncionarios(){
        ModelAndView modelAndView = new ModelAndView();
        List<Funcionario> funcionarioList = funcionarioService.listActiveFuncionarios();
        modelAndView.addObject("funcionarioList", funcionarioList);
        modelAndView.setViewName("funcionario/list");
        return modelAndView;
    }

    @GetMapping("/agenda")
    public String agenda(){
        return "/funcionario/agenda";
    }

    @GetMapping("/agenda-dia/{data}")
    public String agendaDia(@PathVariable String data, Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FuncionarioDTO funcionarioDTO = funcionarioService.findByUser(user);
        Funcionario funcionario = funcionarioDTO.toFuncionario();
        Agenda agenda = agendaService.findByFuncionario(funcionario);
        DiaAgenda diaAgenda = diaAgendaService.findByDia(agenda, data);
        if(diaAgenda == null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dia = LocalDate.parse(data, formatter);
            diaAgenda =  agendaService.createDiaAgenda(agenda, dia);
        }
        model.addAttribute(diaAgenda);
        model.addAttribute(agenda);
        return "/fragments/agenda-dia :: agenda-dia";
    }

    @PostMapping("/salvar-horarios")
    public String salvarHorarios(@ModelAttribute FormCadastroHorarios formCadastroHorarios, Model model) {
        horarioService.saveFromForm(formCadastroHorarios);
        model.addAttribute("msgHorariosSalvos", "Horários salvos com sucesso");
        return "/funcionario/agenda";
    }
}
