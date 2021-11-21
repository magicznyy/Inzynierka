package com.example.RejestracjaLogowanie.Controller;

import com.example.RejestracjaLogowanie.Pin;
import com.example.RejestracjaLogowanie.PinRepository;
import com.example.RejestracjaLogowanie.User;
import com.example.RejestracjaLogowanie.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapsController {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PinRepository pinRepository;

@GetMapping("/maps")
String Maps(Model model)
{
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) auth.getPrincipal();
    User user = (User) userRepository.findUserByLogin(userDetails.getUsername());

    model.addAttribute("long",54.333 );
    model.addAttribute("lati", 52.34);


    /*  Pin pin= new Pin(54.333, 52.34, "fajnietu", "zielony", user);
    pinRepository.save(pin);*/
    return "Maps";



}

}
