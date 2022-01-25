package com.example.RejestracjaLogowanie.Controller;


import com.example.RejestracjaLogowanie.User;
import com.example.RejestracjaLogowanie.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
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



    @RequestMapping("/test/{login}")
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
        model.addAttribute("id", user.getId());



        File directory=new File("src/main/resources/static/images/user"+user.getId());

        if(directory.list()!=null) {
            String[] imagename = Objects.requireNonNull(directory.list());
            model.addAttribute("photos", imagename);
        }

        if(user.getProfilePicPath()==null)
            model.addAttribute("profilepic", "/images/profpic/nopicture.jpg");
        else
            model.addAttribute("profilepic", user.getProfilePicPath());


        if(currUser.getProfilePicPath()==null)
            model.addAttribute("myprofilepic", "/images/profpic/nopicture.jpg");
        else
            model.addAttribute("myprofilepic", currUser.getProfilePicPath());


        return "profiles";
    }






}
