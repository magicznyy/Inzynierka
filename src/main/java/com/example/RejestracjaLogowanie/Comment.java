package com.example.RejestracjaLogowanie;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Komentarz")
public class Comment {


    public Comment() {
    }

    public Comment(String tresc, User user, Post post) {
        this.data = new Date();
        this.content = tresc;
        this.post = post;
        this.user = user;
    }

    @Id
    @GeneratedValue
  
    @Column(name="idkomentarz")
    private Long idComment;

    @Column(name="datazamieszczenia")
    private Date data;

    @Column(name = "tresc")
    private String content;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="uzytkownik_iduzytkownik")
    private User user;

    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name="post_idpost")
    private Post post;

    @OneToOne(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private Notification notification;


    public Long getIdComment() {
        return idComment;
    }

    public void setIdComment(Long idComment) {
        this.idComment = idComment;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String tresc) {
        this.content = tresc;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }


    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
