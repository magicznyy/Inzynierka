package com.example.RejestracjaLogowanie.Controller;

import com.example.RejestracjaLogowanie.User;
import com.example.RejestracjaLogowanie.UserDetailsService;
import com.example.RejestracjaLogowanie.UserInformation;
import com.example.RejestracjaLogowanie.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@Controller
public class ProfileController extends UserInformation {



    UserRepository userRepository;
    @Autowired
    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @GetMapping("/profile")
    public  String display(Model model)
    {
        userdata(model, userRepository);
        return "profile";
    }



}
