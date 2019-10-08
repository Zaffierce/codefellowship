package com.Zaffierce.codefellowship.controllers;

import com.Zaffierce.codefellowship.models.ApplicationUser;
import com.Zaffierce.codefellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class ApplicationUserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;


    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView createNewUser(String username, String password, String firstName, String lastName, String dateOfBirth, String bio) {
        ApplicationUser user = new ApplicationUser(
                username, passwordEncoder.encode(password),
                firstName, lastName,
                dateOfBirth, bio
                );
        applicationUserRepository.save(user);
        return new RedirectView("/");
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/users")
    public String getUsers(Model m) {
        List<ApplicationUser> users = applicationUserRepository.findAll();
        m.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/{userID}")
    public String viewSingleUser(Model m, @PathVariable Long userID){
        m.addAttribute("user", applicationUserRepository.getOne(userID));
        return "single-user";
    }
}
