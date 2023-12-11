package com.eliasfs06.tinktime.controller;

import com.eliasfs06.tinktime.model.UserRole;
import com.eliasfs06.tinktime.service.ClientService;
import com.eliasfs06.tinktime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    UserService userService;

    @Autowired
    ClientService clientService;

    @GetMapping
    private String index(Model model){
        if(userService.getRoleUsuarioLogago() == UserRole.CLIENT){
            model.addAttribute("clientSuggestion", clientService.getSuggetionByLoggedClient());
        }
        return "/index";
    }
}
