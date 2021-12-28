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
import org.springframework.web.bind.annotation.*;

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
        String[][] tabela = new String[pins.size()][8];
        int i=0;
        for(Pin pin: pins)
        {
            tabela[i][0]= String.valueOf(pin.getPinId());
            tabela[i][1]= String.valueOf(pin.getLongitude());
            tabela[i][2]= String.valueOf(pin.getLatitude());
            tabela[i][3]= String.valueOf(pin.getPinDescription());
            tabela[i][4]= String.valueOf(pin.getPinColor());
            if(Objects.isNull(pin.getPhoto()))
            {
                tabela[i][5]= String.valueOf(-1);
                tabela[i][6]= String.valueOf(-1);
            }
            else
            {
                tabela[i][5]= String.valueOf(pin.getPhoto().getPost().getIdPost());
                tabela[i][6]= String.valueOf(pin.getPhoto().getPath()).replace("\\", "/");
            }
            tabela[i][7]= String.valueOf(pin.getUser().getId());
            i++;
        }
        /*System.out.println(Arrays.deepToString(tabela));*/
        model.addAttribute("pins", Arrays.deepToString(tabela));

        List<Pin> allpins = pinRepository.findAll();

        String[][] tabAll = new String[allpins.size()][8];
        int j=0;
        for(Pin pina: allpins)
        {
            if(Objects.isNull(pina.getUser().getPrywatnosckonta()) || pina.getUser() == user)
            {
                tabAll[j][0]= String.valueOf(pina.getPinId());
                tabAll[j][1]= String.valueOf(pina.getLongitude());
                tabAll[j][2]= String.valueOf(pina.getLatitude());
                tabAll[j][3]= String.valueOf(pina.getPinDescription());
                tabAll[j][4]= String.valueOf(pina.getPinColor());
                if(Objects.isNull(pina.getPhoto()))
                {
                    tabAll[j][5]= String.valueOf(-1);
                    tabAll[j][6]= String.valueOf(-1);
                }

                else
                {
                    tabAll[j][5]= String.valueOf(pina.getPhoto().getPost().getIdPost());
                    tabAll[j][6]= String.valueOf(pina.getPhoto().getPath()).replace("\\", "/");
                }

                if(pina.getUser().getId().equals(user.getId())) tabAll[j][7]= String.valueOf(pina.getUser().getId());
                else tabAll[j][7]= String.valueOf(-1);
            }
            else
            {
                tabAll[j][0]= String.valueOf(-1);
                tabAll[j][1]= String.valueOf(-1);
                tabAll[j][2]= String.valueOf(-1);
                tabAll[j][3]= String.valueOf(-1);
                tabAll[j][4]= String.valueOf(-1);
                tabAll[j][5]= String.valueOf(-1);
                tabAll[j][6]= String.valueOf(-1);
                tabAll[j][7]= String.valueOf(-1);
            }
            j++;
        }
        /*System.out.println(Arrays.deepToString(tabAll));*/
        model.addAttribute("pinsall", Arrays.deepToString(tabAll));


        return "Maps";

    }

    @RequestMapping("/savepin")
    String PinSave(@RequestParam("lat") Double lat, @RequestParam("lng") Double lng, @RequestParam("pindescription") String pindescription, @RequestParam(name="color") String color) {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user =  userRepository.findUserByLogin(userDetails.getUsername());
            Pin pineza= new Pin(lat, lng, pindescription, color, user, null);
         /*   user.addPin(pineza);*/
            pinRepository.save(pineza);

        return "redirect:/maps";
    }

    @RequestMapping("/deletepin")
    String PinDelete(@RequestParam("idPinezki") Long id)
    {
        Pin pin=pinRepository.getById(id);
        String yo=String.valueOf(pin.getPinDescription());
        System.out.println(yo);
        pinRepository.delete(pin);
        return "redirect:/maps";
    }

}