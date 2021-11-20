package com.example.RejestracjaLogowanie.Controller;

import com.example.RejestracjaLogowanie.*;
import org.apache.tomcat.websocket.PojoHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainPageController {


    @Autowired
    PostRepository userRepository;

    @GetMapping("/mainPage")
    public String mainPage(){

        List<Post> photos =  userRepository.findAll();
        for (Post user :photos) {

            System.out.println(user.getIdPost());
            System.out.println(user.getUser().getLogin());
        }


        return "mainPage";
    }





}
