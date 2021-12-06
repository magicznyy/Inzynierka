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
        this.tresc = tresc;
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
    private String tresc;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="uzytkownik_iduzytkownik")
    private User user;

    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name="post_idpost")
    private Post post;

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

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
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
