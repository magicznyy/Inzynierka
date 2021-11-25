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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

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
        User user =  userRepository.findUserByLogin(userDetails.getUsername());

        List<Pin> pins= pinRepository.findPinsByUser(user);
        String[][] tabela = new String[pins.size()][6];
        int i=0;
        for(Pin pin: pins)
        {
            tabela[i][0]= String.valueOf(pin.getPinId());
            tabela[i][1]= String.valueOf(pin.getLatitude());
            tabela[i][2]= String.valueOf(pin.getLongitude());
            tabela[i][3]= String.valueOf(pin.getPinDescription());
            tabela[i][4]= String.valueOf(pin.getPinColor());
            tabela[i][5]= String.valueOf(pin.getUser().getId());
            i++;
        }
        System.out.println(Arrays.deepToString(tabela));

        Pin pin1= new Pin(21.031820374563694, 52.263799574655906, "test1", "e74c3c", user);
         pinRepository.save(pin1);
        Pin pin2= new Pin(20.463031153467398, 52.44672743296093, "test2", "e74c3c", user);
        pinRepository.save(pin2);
        Pin pin3= new Pin( 21.446734250822544, 52.04294773890368,"test3", "e74c3c", user);
        pinRepository.save(pin3);
        Pin pin4= new Pin( 20.048117277208547, 52.49694173354993,"test4", "e74c3c", user);
        pinRepository.save(pin4);
        Pin pin5= new Pin( 21.463220894912695,51.96682963967109, "test5", "e74c3c", user);
        pinRepository.save(pin5);
        model.addAttribute("pins", Arrays.deepToString(tabela));
        return "Maps";

    }

/*    @PostMapping("/savepin")
    String PinSave(@Valid Pin pin, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("Sa bledy lmao");
            return "process_success";
        }
        else
            pinRepository.save(pin);

        return "Maps";
    }*/

}