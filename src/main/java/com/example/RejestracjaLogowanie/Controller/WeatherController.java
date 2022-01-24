package com.example.RejestracjaLogowanie.Controller;

import com.example.RejestracjaLogowanie.User;
import com.example.RejestracjaLogowanie.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
public class WeatherController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/weather")
    public String weather(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        User user=userRepository.findUserByLogin(login);
        if(Objects.nonNull(user.getPrywatnosckonta()))
            model.addAttribute("userpriviet", 1);
        else model.addAttribute("userpriviet", 0);

        if(user.getProfilePicPath()==null)
            model.addAttribute("profilepic", "/images/profpic/nopicture.jpg");
        else
            model.addAttribute("profilepic", user.getProfilePicPath());



        return "weather";
    }

    @RequestMapping("/weatherPreferences")
    public String weatherPreferencesUpdate(@RequestParam(name="tempRangeInput") int preftemperature,
                                         @RequestParam(name="windRangeInput") int prefwind,
                                         @RequestParam(name="cloudsRangeInput") int prefclouds,
                                         @RequestParam(name="precipProbRangeInput") int prefprecip){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        User user=userRepository.findUserByLogin(login);

        user.setPreftemperatura(preftemperature);
        user.setPrefpredkoscWiatru(prefwind);
        user.setPrefstopienZachmurzenia(prefclouds);
        user.setPrefprawdopodobOpadow(prefprecip);

        userRepository.save(user);
        return "redirect:/weather";
    }

    @ResponseBody
    @GetMapping( "/sendWeatherPreferences")
    public String weatherPreferencesSend() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        User user=userRepository.findUserByLogin(login);

        String returnJSON = "{" +
                "\"preferences\" :" +
                                "{\"preftemp\":"+ user.getPreftemperatura() +
                                ",\"prefwind\":" + user.getPrefpredkoscWiatru() +
                                ",\"prefclouds\":"+ user.getPrefstopienZachmurzenia() +
                                 ",\"prefprecip\":"+ user.getPrefprawdopodobOpadow() +
                                "}," +
                "\"userDetails\" :" +
                "{ \"key\" : \"07e09c6a0dc3a225cd5db4e457e4cf2b\"" +
                ",\"longitude\" : "+ user.getMapsCenterLongitude() +
                ",\"latitude\" :" + user.getMapsCenterLatitude() +
                "}" +"}";

        return returnJSON;
    }

}
