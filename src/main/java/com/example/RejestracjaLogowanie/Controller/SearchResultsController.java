package com.example.RejestracjaLogowanie.Controller;


import com.example.RejestracjaLogowanie.Post;
import com.example.RejestracjaLogowanie.PostRepository;
import com.example.RejestracjaLogowanie.User;
import com.example.RejestracjaLogowanie.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchResultsController {

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
        System.out.println(allPosts);

        try{
            if(Character.toString(searchValue.charAt(0)).matches("#"))
                searchValue = searchValue.substring(1);
        }
        catch(Exception e){
             System.out.println("Pusto");
        }


        if(user!=null)
        model.addAttribute("user", user);
        model.addAttribute("allPosts", allPosts);
        model.addAttribute("searchValue", searchValue);
        return "searchResults";
    }

}
