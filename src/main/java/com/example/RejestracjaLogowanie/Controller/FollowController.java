package com.example.RejestracjaLogowanie.Controller;


import com.example.RejestracjaLogowanie.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
public class FollowController {


    @Autowired
    private FollowedUserRepository followedUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;


    @RequestMapping("/follow")
    public String follow(Model model, @RequestParam(name= "followedUserLogin") String followedUserLogin, HttpServletRequest httpServletRequest)
    {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());

        if(user.isAlreadyFollowed(followedUserLogin)==false)
        {
            FollowedUser followedUser= new FollowedUser(user, userRepository.findUserByLogin(followedUserLogin));
            user.addFollowedUser(followedUser);
            followedUserRepository.save(followedUser);

            String link = httpServletRequest.getRequestURL().toString();
            Notification notification = new Notification(link,user,userRepository.findUserByLogin(followedUserLogin));
            notificationRepository.save(notification);
            user.addNotification(notification);
        }
        else
        {
            FollowedUser followedUser = user.findFollowedUser(followedUserLogin);
            user.removeFollowedUser(followedUserLogin);
            System.out.println("ID: " + followedUser.getIdFollowedUser());
            try{
                followedUserRepository.delete(followedUser);
            }
            catch (NullPointerException e){
                System.out.println("Usunięty FollowedUser był null");
            }

        }


        return "redirect:/test/" + followedUserLogin;
    }
/*
    @RequestMapping("/unfollow")
    public String follow(@RequestParam(name= "followedUser") String followedUser)
    {




        return "redirect:/"
    }*/


}
