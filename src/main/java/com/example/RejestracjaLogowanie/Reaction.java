package com.example.RejestracjaLogowanie;


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
    @GeneratedValue
    @Column(name="idReakcja")
    private Long idReaction;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="uzytkownik_iduzytkownik")
    private User user;

    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name="post_idpost")
    private Post  post;


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
}
