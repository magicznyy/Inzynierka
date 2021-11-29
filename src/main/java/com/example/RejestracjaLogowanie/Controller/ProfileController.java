package com.example.RejestracjaLogowanie.Controller;

import com.example.RejestracjaLogowanie.User;
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
public class ProfileController {



    UserRepository userRepository;
    @Autowired
    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private void userdata(Model model, UserRepository userRepository)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        User user=userRepository.findUserByLogin(login);
        model.addAttribute("login", user.getLogin());
        model.addAttribute("money", user.getSaldo());
        model.addAttribute("id", user.getId());
        model.addAttribute("profileDescription", user.getProfileDescription());

        if(user.getProfilePicPath()==null)
            model.addAttribute("profilepic", "/images/profpic/nopicture.jpg");
        else
            model.addAttribute("profilepic", user.getProfilePicPath());
        File directory=new File("src/main/resources/static/images/user"+user.getId());

        if(directory.list()!=null) {
            String[] imagename = Objects.requireNonNull(directory.list());
            model.addAttribute("photos", imagename);
            System.out.println(Arrays.toString(imagename));
            System.out.println(directory);

        }
    }

    @GetMapping("/profile")
    public  String display(Model model)
    {
        userdata(model, userRepository);
        return "profile";
    }

}
