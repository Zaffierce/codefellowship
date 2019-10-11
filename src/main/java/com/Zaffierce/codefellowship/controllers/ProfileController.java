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
import java.util.List;
import java.util.Set;

@Controller
public class ProfileController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/myprofile/{id}")
    public String getMyProfile(Principal p, Model m, @PathVariable long id) {
        if (p != null) {
            ApplicationUser theUser = applicationUserRepository.findByUsername(p.getName());
            m.addAttribute("userID", theUser.getId());
            m.addAttribute("username", p.getName());
            ApplicationUser user = applicationUserRepository.findById(id).get();
            m.addAttribute("user", user);
            //If you're not following them, show this.
            m.addAttribute("shouldShowFollow", !user.getUsersThatFollowMe().contains(theUser));
        }
        return "myprofile";
    }

    @PostMapping("/addPost")
    public RedirectView addPost(Principal p, String body) {
        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
        Post post = new Post(body, user);
        postRepository.save(post);
        return new RedirectView("/myprofile/" + user.getId());
    }

    @GetMapping("/feed")
    public String getFeed(Principal p, Model m) {
        if (p != null) {
            ApplicationUser theUser = applicationUserRepository.findByUsername(p.getName());
            m.addAttribute("userID", theUser.getId());
            m.addAttribute("username", p.getName());
            List<Post> posts = postRepository.findAll();
            m.addAttribute("posts", posts);
            Set<ApplicationUser> followingUsers = theUser.getUsersIFollow();
            m.addAttribute("followingUsers", followingUsers);
//            theUser.getUsersIFollow()
        }
        return "feed";
    }

    @PostMapping("/follow")
    public RedirectView followUser(Principal p, Model m, Long id) {
        ApplicationUser theUser = applicationUserRepository.findByUsername(p.getName());
        ApplicationUser myID = applicationUserRepository.getOne(theUser.getId());
        ApplicationUser theirID = applicationUserRepository.getOne(id);

        myID.followThisUser(theirID);
        applicationUserRepository.save(myID);
    return new RedirectView("/");
    }
}
