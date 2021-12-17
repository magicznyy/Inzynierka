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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


@Controller
public class NotificationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @ResponseBody
    @GetMapping("/checkingForNotifications")
    public String checkingForNotification(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());

        List<Notification> notifications = new ArrayList<>();
        notifications = notificationRepository.findBytargetUser(user);

        System.out.println("Ostatnia przed: " + notifications.get(notifications.size()-1).getIdNotification());
        notifications.sort(Comparator.comparing(Notification::getIdNotification));

        System.out.println("Ostatnia: " + notifications.get(notifications.size()-1).getIdNotification());
        //sortuje po dacie, najnowsze powiadomienia są na poczatku listy
        int numberOfNewNotifications = notifications.size() - user.getNotificationsCounter();

        //do newNotifications kopiuje tylko nowe powiadomienia z notifications
        List<Notification> newNotifications = new ArrayList<>();
        for (int i = notifications.size()-1; i>=notifications.size()-numberOfNewNotifications; i--){
            System.out.println("DUPAAAAA");
            newNotifications.add(notifications.get(i));
        }

        user.setNotificationsCounter(notifications.size());
        userRepository.save(user);



        ObjectMapper mapper =  new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(newNotifications);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(jsonString);

        return jsonString;
    }


}
