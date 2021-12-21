package com.example.RejestracjaLogowanie.Controller;


import com.example.RejestracjaLogowanie.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        if(user!=null)
        model.addAttribute("user", user);
        model.addAttribute("matchingPosts", postsMatchTags(searchValue, allPosts));
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

}
