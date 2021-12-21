package com.example.RejestracjaLogowanie;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {


    @Autowired
    NotificationRepository notificationRepository;

    public NotificationService() {
    }


    public Notification createNotification()
    {
        Notification notification =  new Notification() ;
        return notification;
    }


    public void saveNotification(Notification notification){
        notificationRepository.save(notification);
    }




}
