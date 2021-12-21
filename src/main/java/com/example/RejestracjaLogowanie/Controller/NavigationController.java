package com.example.RejestracjaLogowanie.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController {

    @GetMapping("/mapsony")
    public String photoit()
    {
        return "Maps";
    }

    @GetMapping("/add")
    public String add()
    {
        return "uploadPhoto";
    }

    @GetMapping("/map")
    public String map()
    {
        return "process_success";
    }

    @GetMapping("/weather")
    public String weather()
    {
        return "weather";
    }

    @GetMapping("/purchasedphotos")
    public String purchasedphotos()
    {
        return "process_success";
    }



}
