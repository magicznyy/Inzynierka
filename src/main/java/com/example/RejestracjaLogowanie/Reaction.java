package com.example.RejestracjaLogowanie;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="Reakcja")
public class Reaction {


    public Reaction() {
    }

    public Reaction(Post post, User user) {

        this.post = post;
        this.user = user;
    }

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY )
    @Column(name="idreakcja")
    private Long idReaction;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="uzytkownik_iduzytkownik")
    private User user;


    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name="post_idpost")
    private Post  post;

    @JsonIgnore
    @OneToOne(mappedBy = "reaction", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private Notification notification;




    public Long getIdReaction() {
        return idReaction;
    }

    public void setIdReaction(Long idReaction) {
        this.idReaction = idReaction;
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
