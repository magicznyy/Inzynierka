package com.example.RejestracjaLogowanie.Controller;

import com.example.RejestracjaLogowanie.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

@Controller
public class MainPageController {


    @Autowired
    PostRepository postRepository;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/mainPage")
    public String mainPage(Model model){

        model.addAttribute("posts", getFollowedUsersPosts());

        return "mainPage";
    }

    //to jest straszne, ale dziala na razie
    public List<Post> getFollowedUsersPosts(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());

        List<Post> posts = postRepository.findAll();
        List<Post> followedUsersPosts = new ArrayList<>();
        for (Post post : posts) {
            if(user.isAlreadyFollowed(post.getUser().getLogin()) == true){
                followedUsersPosts.add(post);
            }
        }
        return followedUsersPosts;
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
    @GetMapping(value = "/mainPage/data/getPosts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> mainPageFetchPosts() throws JsonProcessingException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());

        /*List<FollowedUser> users = user.getFollowedUsers();
        List<Post> followedUsersPosts = null;
        for (FollowedUser usr : users ){
            try {
                followedUsersPosts.addAll(usr.getUser().getPosts());
            }catch (NullPointerException e){
                System.out.println(e.toString());
            }
        }*/

        List<Post> allposts = new ArrayList<>();
        List<Post> followedUsersPosts = new ArrayList<>();

        allposts.addAll(postRepository.findAll());


        for (Post pr : allposts){
            System.out.println(pr);
            if(pr.getUser().getLogin().equals("Kolega"))
                followedUsersPosts.add(pr);
        }

        //List<User> users = new ArrayList<>();;
        //users.add(userRepository.findUserByLogin("Kolega"));

        //System.out.println(userRepository.findUserByLogin("Kolega"));


        /*
        for (User usr : users ){
            try {
                followedUsersPosts.addAll(usr.getPosts());
                System.out.println(followedUsersPosts);
            }catch (NullPointerException e){
                System.out.println(e.toString());
            }
        }*/

        //System.out.println(followedUsersPosts);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(followedUsersPosts);

        return Optional.ofNullable(followedUsersPosts)
                .orElse(Collections.<Post> emptyList());
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
