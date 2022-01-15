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
    String Maps(Model model) {

        userdata(model, userRepository);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepository.findUserByLogin(userDetails.getUsername());
        model.addAttribute("iduser", user.getId());
        List<Pin> allpins = pinRepository.findAll();
        String[][] tabAll = new String[allpins.size()][9];
        int j = 0;
        for (Pin pina : allpins) {
            {
                tabAll[j][0] = String.valueOf(pina.getPinId());
                tabAll[j][1] = String.valueOf(pina.getLongitude());
                tabAll[j][2] = String.valueOf(pina.getLatitude());
                tabAll[j][3] = String.valueOf(pina.getPinDescription());
                tabAll[j][4] = String.valueOf(pina.getPinColor());
                if (Objects.isNull(pina.getPhoto())) {
                tabAll[j][5] = String.valueOf(-1);
                tabAll[j][6] = String.valueOf(-1); }
                else {
                tabAll[j][5] = String.valueOf(pina.getPhoto().getPost().getIdPost());
                tabAll[j][6] = String.valueOf(pina.getPhoto().getPath()).replace("\\", "/"); }
                tabAll[j][7] = String.valueOf(pina.getUser().getId());
                if(Objects.isNull(pina.getUser().getPrywatnosckonta()))
                tabAll[j][8] = String.valueOf(1); /*publiczny*/
                else
                tabAll[j][8] = String.valueOf(-1); /*prywatny*/}
                j++; }
        model.addAttribute("pinsall", Arrays.deepToString(tabAll));


        return "Maps";

    }


    @RequestMapping("/savepin")
    String PinSave(@RequestParam("lat") Double lat, @RequestParam("lng") Double lng,
    @RequestParam("pindescription") String pindescription, @RequestParam(name="color") String color) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user =  userRepository.findUserByLogin(userDetails.getUsername());
        Pin pinAdd= new Pin(lat, lng, pindescription, color, user, null);
        pinRepository.save(pinAdd);

        return "redirect:/maps";
    }

    @RequestMapping("/deletepin")
    String PinDelete(@RequestParam("idPinezki") Long id)
    {
        Pin pinDelete=pinRepository.getById(id);
        pinRepository.delete(pinDelete);
        return "redirect:/maps";
    }

}