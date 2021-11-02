package com.example.RejestracjaLogowanie.Controller;

import com.example.RejestracjaLogowanie.User;
import com.example.RejestracjaLogowanie.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController{

    UserRepository userRepository;

    @Autowired
    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);


    @GetMapping("/registration")
    public String register(Model model)
    {
        model.addAttribute("user", new User());
        return "Registration";

    }

    @PostMapping("/process_register")
    public String send(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("Sa bledy lmao");
            return "process_success";
        }
        else {
            log.info(">> User : {}", user.toString());
            userRepository.save(user);
            return "process_success";
        }
    }

}