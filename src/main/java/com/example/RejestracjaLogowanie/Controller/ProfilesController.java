package com.example.RejestracjaLogowanie.Controller;


import com.example.RejestracjaLogowanie.User;
import com.example.RejestracjaLogowanie.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.security.Principal;
import java.util.Arrays;
import java.util.Objects;

@org.springframework.stereotype.Controller
public class ProfilesController {


    UserRepository userRepository;
    @Autowired
    public ProfilesController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @GetMapping("/test/{login}")
    String displayProfile(Model model, @PathVariable(name="login") String login)
    {

        User user = (User) userRepository.findUserByLogin(login);
        model.addAttribute("user", user);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User currUser = (User) userRepository.findUserByLogin(userDetails.getUsername());


        if(user.getLogin() == currUser.getLogin())
            return "redirect:/profile";

        model.addAttribute("login", user.getLogin());
        model.addAttribute("money", user.getSaldo());
        model.addAttribute("id", user.getId());
        File directory=new File("C:\\Users\\HardPc\\Desktop\\Inzynierka\\src\\main\\resources\\static\\images\\user"+user.getId());
        if(directory.list()!=null) {
            String[] imagename = Objects.requireNonNull(directory.list());
            model.addAttribute("photos", imagename);
        }

        if(user.getProfilePicPath()==null)
            model.addAttribute("profilepic", "/images/profpic/nopicture.jpg");
        else
            model.addAttribute("profilepic", user.getProfilePicPath());

        model.addAttribute("myprofilepic", currUser.getProfilePicPath());


        return "profiles";
    }



}
