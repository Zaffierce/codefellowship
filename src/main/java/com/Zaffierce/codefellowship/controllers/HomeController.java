package com.Zaffierce.codefellowship.controllers;

import com.Zaffierce.codefellowship.models.ApplicationUser;
import com.Zaffierce.codefellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @GetMapping("/")
    public String getHome(Principal p, Model m) {
        if (p != null) {
            ApplicationUser theUser = applicationUserRepository.findByUsername(p.getName());
            m.addAttribute("userID", theUser.getId());
            m.addAttribute("username", p.getName());
        }
        return "home";
    }

}
