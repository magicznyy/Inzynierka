package com.example.RejestracjaLogowanie.Controller;

import com.example.RejestracjaLogowanie.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
public class MapsController extends UserInformation {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PinRepository pinRepository;

    @GetMapping("/maps")
    String Maps(Model model)
    {

        userdata(model, userRepository);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user =  userRepository.findUserByLogin(userDetails.getUsername());

        List<Pin> pins= pinRepository.findPinsByUser(user);
        String[][] tabela = new String[pins.size()][7];
        int i=0;
        for(Pin pin: pins)
        {
            tabela[i][0]= String.valueOf(pin.getPinId());
            tabela[i][1]= String.valueOf(pin.getLatitude());
            tabela[i][2]= String.valueOf(pin.getLongitude());
            tabela[i][3]= String.valueOf(pin.getPinDescription());
            tabela[i][4]= String.valueOf(pin.getPinColor());
            tabela[i][5]= String.valueOf(pin.getUser().getId());
            if(Objects.isNull(pin.getPhoto()))
                    tabela[i][6]= String.valueOf(-1);
            else  tabela[i][6]= String.valueOf(pin.getPhoto().getPath()).replace("\\", "/");
            i++;
        }
        System.out.println(Arrays.deepToString(tabela));
        model.addAttribute("pins", Arrays.deepToString(tabela));

        List<Pin> allpins = pinRepository.findAll();

        String[][] tabAll = new String[allpins.size()][7];
        int j=0;
        for(Pin pina: allpins)
        {
            tabAll[j][0]= String.valueOf(pina.getPinId());
            tabAll[j][1]= String.valueOf(pina.getLatitude());
            tabAll[j][2]= String.valueOf(pina.getLongitude());
            tabAll[j][3]= String.valueOf(pina.getPinDescription());
            tabAll[j][4]= String.valueOf(pina.getPinColor());
            tabAll[j][5]= String.valueOf(pina.getUser().getId());
            if(Objects.isNull(pina.getPhoto()))
                tabAll[j][6]= String.valueOf(-1);
            else  tabAll[j][6]= String.valueOf(pina.getPhoto().getPath()).replace("\\", "/");
            j++;
        }
        System.out.println(Arrays.deepToString(tabAll));
        model.addAttribute("pinsall", Arrays.deepToString(tabAll));


        return "Maps";

    }

    @RequestMapping("/savepin")
    String PinSave(@RequestParam("lat") Double lat, @RequestParam("lng") Double lng, @RequestParam("pindescription") String pindescription, @RequestParam("color") String color) {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user =  userRepository.findUserByLogin(userDetails.getUsername());
            Pin pineza= new Pin(lng, lat, pindescription, color, user, null);

            pinRepository.save(pineza);

        return "redirect:/maps";
    }

}