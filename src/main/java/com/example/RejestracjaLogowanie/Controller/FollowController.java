package com.example.RejestracjaLogowanie.Controller;


import com.example.RejestracjaLogowanie.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FollowController {


    @Autowired
    private FollowedUserRepository followedUserRepository;

    @Autowired
    private UserRepository userRepository;


    @RequestMapping("/follow")
    public String follow(@RequestParam(name= "followedUserLogin") String followedUserLogin)
    {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());

        if(user.isAlreadyFollowed(followedUserLogin)==false)
        {
            FollowedUser followedUser= new FollowedUser(user, userRepository.findUserByLogin(followedUserLogin));
            user.addFollowedUser(followedUser);
            followedUserRepository.save(followedUser);
        }



        return "redirect:/test/$followedUser";
    }
/*
    @RequestMapping("/unfollow")
    public String follow(@RequestParam(name= "followedUser") String followedUser)
    {




        return "redirect:/"
    }*/


}
