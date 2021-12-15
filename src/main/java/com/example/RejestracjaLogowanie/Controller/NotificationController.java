package com.example.RejestracjaLogowanie.Controller;


import com.example.RejestracjaLogowanie.Notification;
import com.example.RejestracjaLogowanie.NotificationService;
import com.example.RejestracjaLogowanie.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;





@Controller
public class NotificationController {

    NotificationService notificationService;

    @ResponseBody
    @PostMapping("/sendReactionNotification")
    public String sendNotification(@RequestParam("currUser") User currUser, @RequestParam("targetUser") User targetUser){



        return "lala";
    }

    @ResponseBody
    @GetMapping("/checkingForNotification")
    public String checkingForNotification(){




        return "lala";
    }


}
