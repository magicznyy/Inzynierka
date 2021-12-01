package com.example.RejestracjaLogowanie.Controller;

import com.example.RejestracjaLogowanie.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.websocket.PojoHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class MainPageController {


    @Autowired
    PostRepository postRepository;
    @Autowired
    PhotoRepository photoRepository;

    @GetMapping("/mainPage")
    public String mainPage(Model model){

        List<Post> posts =  postRepository.findAll();
        model.addAttribute("posts", posts);

        return "mainPage";
    }


    @ResponseBody
    @GetMapping(value = "/mainPage/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public Post mainPageData() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Post post = postRepository.findAll().get(0);
        mapper.writeValueAsString(post);

        return Optional.ofNullable(post)
                .orElse(new Post());
    }


    @ResponseBody
    @GetMapping(value = "/mainPage/data/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> mainPagePhoto(@PathVariable("id") Integer id) throws IOException {
        String path = "\\static";
        path +=  photoRepository.findAll().get(id).getPath();
        ClassPathResource imageFile = new ClassPathResource(path);
        System.out.println("ścieżka wysyłana =" +path);


        byte[] imageBytes = StreamUtils.copyToByteArray(imageFile.getInputStream());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }




}
