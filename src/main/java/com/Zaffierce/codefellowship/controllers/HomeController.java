package com.Zaffierce.codefellowship.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome(Principal p, Model m) {
        if (p != null) {
            String nav = "nav-loggedin-user";
            m.addAttribute("username", p.getName());
            m.addAttribute("navbar", nav);
        } else {
            String nav = "nav-loggedout";
            m.addAttribute("navbar", nav);
        }
        return "home";
    }

}
