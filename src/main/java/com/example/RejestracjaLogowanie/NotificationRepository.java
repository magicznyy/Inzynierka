package com.example.RejestracjaLogowanie;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {


    List<Notification> findBytargetUser(User user);

}
