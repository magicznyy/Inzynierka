package com.example.RejestracjaLogowanie.Controller;

import com.example.RejestracjaLogowanie.User;
import com.example.RejestracjaLogowanie.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class LoginController {

    UserRepository userRepository;



    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "Login";
    }

    private boolean userexist(String name) {
        return userRepository.findUserByLogin(name) != null;
    }

    @PostMapping("/login")
    public String checkifregistered(User user)
    {
       if(userexist(user.getLogin()))
        System.out.println("jest git");
       else             System.out.println("nie git");

        return "process_success";
}
}
