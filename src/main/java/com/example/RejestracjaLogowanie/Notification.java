package com.example.RejestracjaLogowanie;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Powiadomienie")
public class Notification {


    public Notification() {
    }

    public Notification(String contentLink, User targetUser, User senderUser, Comment comment) {

        this.notificationContent = "napisał(a) komentarz pod Twoim postem!";
        this.contentLink = contentLink;
        this.sendDate = new Date();
        this.targetUser = targetUser;
        this.senderUser = senderUser;
        this.comment = comment;
    }

    public Notification(String contentLink, User targetUser, User senderUser, Reaction reaction) {

        this.notificationContent = " polubił(a) Twój post!";
        this.contentLink = contentLink;
        this.sendDate = new Date();
        this.targetUser = targetUser;
        this.senderUser = senderUser;
        this.reaction = reaction;
    }

    public Notification(String contentLink, User targetUser, User senderUser) {

        this.notificationContent = " obserwuje Cię!";
        this.contentLink = contentLink;
        this.sendDate = new Date();
        this.targetUser = targetUser;
        this.senderUser = senderUser;
    }

    @Id
    @GeneratedValue
    @Column(name = "idpowiadomienie")
    private Long idNotification;

    @Column(name="trescpowiadomienia")
    private String notificationContent;

    @Column(name="linktresci")
    private String contentLink;

    @Column(name="datawyslania")
    private Date sendDate;


    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="uzytkownik_iduzytkownik1")
    private User senderUser;


    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="uzytkownik_iduzytkownik")
    private User targetUser;


    @OneToOne
    @JoinColumn(name="reakcja_idreakcja", referencedColumnName = "idreakcja")
    private Reaction reaction;

    @OneToOne
    @JoinColumn(name="komentarz_idkomentarz", referencedColumnName = "idkomentarz")
    private Comment comment;





    public Long getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(Long idNotification) {
        this.idNotification = idNotification;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public String getContentLink() {
        return contentLink;
    }

    public void setContentLink(String contentLink) {
        this.contentLink = contentLink;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User targetUser) {
        this.targetUser = targetUser;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(User senderUser) {
        this.senderUser = senderUser;
    }

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
