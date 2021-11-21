package com.example.RejestracjaLogowanie.Controller;

import com.example.RejestracjaLogowanie.*;
import org.apache.tomcat.websocket.PojoHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainPageController {


    @Autowired
    PostRepository postRepository;

    @GetMapping("/mainPage")
    public String mainPage(Model model){

        List<Post> posts =  postRepository.findAll();
        model.addAttribute("posts", posts);

        return "mainPage";
    }





}
