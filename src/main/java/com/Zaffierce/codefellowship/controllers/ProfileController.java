package com.Zaffierce.codefellowship.controllers;

import com.Zaffierce.codefellowship.models.ApplicationUser;
import com.Zaffierce.codefellowship.models.ApplicationUserRepository;
import com.Zaffierce.codefellowship.models.Post;
import com.Zaffierce.codefellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/myprofile/{id}")
    public String getMyProfile(Principal p, Model m, @PathVariable long id) {
        if (p != null) { m.addAttribute("username", p.getName()); }
//        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
        ApplicationUser user = applicationUserRepository.findById(id).get();
//        m.addAttribute("userID", userID);
        m.addAttribute("user", user);
        return "myprofile";
    }

    @PostMapping("/addPost")
    public RedirectView addPost(Principal p, String body) {
        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
        Post post = new Post(body, user);
        postRepository.save(post);
        return new RedirectView("/myprofile/" + user.getId());
    }
}
