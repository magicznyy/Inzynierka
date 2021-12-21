package com.example.RejestracjaLogowanie;

import com.example.RejestracjaLogowanie.User;
import com.example.RejestracjaLogowanie.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class UserInformation {


    public void userdata(Model model, UserRepository userRepository)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        User user=userRepository.findUserByLogin(login);
        model.addAttribute("user", user);
        model.addAttribute("login", user.getLogin());
        model.addAttribute("id", user.getId());
        model.addAttribute("profileDescription", user.getProfileDescription());
        model.addAttribute("lat", user.getMapsCenterLatitude());
        model.addAttribute("lng", user.getMapsCenterLongitude());

        if(Objects.nonNull(user.getPrywatnosckonta()))
            model.addAttribute("userpriviet", 1);
        else model.addAttribute("userpriviet", 0);

        if(user.getProfilePicPath()==null)
            model.addAttribute("profilepic", "/images/profpic/nopicture.jpg");
        else
            model.addAttribute("profilepic", user.getProfilePicPath());
        File directory=new File("src/main/resources/static/images/user"+user.getId());

        if(directory.list()!=null) {
            String[] imagename = Objects.requireNonNull(directory.list());
            model.addAttribute("photos", imagename);
           /* System.out.println(Arrays.toString(imagename));
            System.out.println(directory);*/

        }
    }

    public User userCurr(Model model, UserRepository userRepository)
    {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return (User) userRepository.findUserByLogin(userDetails.getUsername());
    }
}
