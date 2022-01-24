package com.example.RejestracjaLogowanie.Controller;


import com.example.RejestracjaLogowanie.*;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchResultsController extends PostService   {




     @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/searchResults")
    public String  displayResults(Model model, @RequestParam(name="searchValue") String searchValue)
    {

        User user =  userRepository.findUserByLogin(searchValue);
        List<Post> allPosts = new ArrayList();
        allPosts = postRepository.findAll();

        try{
            if(Character.toString(searchValue.charAt(0)).matches("#"))
                searchValue = searchValue.substring(1);
        }
        catch(Exception e){
             System.out.println("Pusto");
        }


        List<Post> testSprawdz = new ArrayList<Post>(postRepository.findByTagsContainingIgnoreCase(searchValue));

        List<String> miniaturesPath = new ArrayList<String>();
        for (Post post : testSprawdz){
            File file = new File("\\static" + post.getPhoto().getPath());

            miniaturesPath.add((file.getParent() + "\\resized" + "\\" + file.getName()).substring(7));
        }


        if(user!=null)
        model.addAttribute("user", user);
        model.addAttribute("matchingPosts", postsMatchTags(searchValue, allPosts));
        model.addAttribute("paths",miniaturesPath);
        model.addAttribute("searchValue", searchValue);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        User usercurr=userRepository.findUserByLogin(login);
        if(usercurr.getProfilePicPath()==null)
            model.addAttribute("profilepic", "/images/profpic/nopicture.jpg");
        else
            model.addAttribute("profilepic", usercurr.getProfilePicPath());


        return "searchResults";
    }


    @GetMapping("/test")
    @ResponseBody
    public String test(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());
        ObjectMapper mapper =  new ObjectMapper();
        String jsonString = null;
        try {
             jsonString = mapper.writeValueAsString(user);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("userek", "jajko");

        return jsonString;
    }

}
