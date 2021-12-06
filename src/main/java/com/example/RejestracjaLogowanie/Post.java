package com.example.RejestracjaLogowanie;


import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "post")
public class Post {


    public Post() {
    }

    public  Post(String tags, String description, Date date, User user, Photo photo){

       this.tags = tags;
       this.description = description;
       this.date = date;
       this.user = user;
       this.photo = photo;
   }


    @Id
    @GeneratedValue
    @Column(name="idpost", table="post")
    private Long idPost;

    @Column(name="tagi", table="post")
    private String tags;

    @Column(name="opis", table="post")
    private String description;

    @Column(name="datazamieszczenia", table="post")
    private Date date;

    @Override
    public String toString() {
        return "Post{" +
                "idPost=" + idPost +
                ", tags='" + tags + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", user=" + user +
                ", photo=" + photo +
                '}';
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "uzytkownik_iduzytkownik")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="zdjecie_idzdjecie", referencedColumnName = "idzdjecie")
    private Photo photo;

    @OneToMany(targetEntity= Comment.class , cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List <Comment> comments = new ArrayList<>();

    @OneToMany(targetEntity = Reaction.class, orphanRemoval = true,fetch = FetchType.LAZY)
    private List <Reaction> reactions = new ArrayList<>();

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Long getIdPost() {
        return idPost;
    }

    public void setIdPost(Long idPost) {
        this.idPost = idPost;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addComment(Comment comment){

        this.comments.add(comment);

    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
