package com.example.RejestracjaLogowanie;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "post")
public class Post {

   public  Post(String tags, String description, LocalDateTime date, User user){

       this.tags = tags;
       this.description = description;
       //this.date = date;
       this.user = user;
   }

    @Id
    @GeneratedValue
    @Column(name="idpost", table="post")
    private Long idPost;

    @Column(name="tagi", table="post")
    private String tags;

    @Column(name="opis", table="post")
    private String description;
/*
    @Column(name="datazamieszczenia", table="post")
    private LocalDateTime date;*/

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="uzytkownik_iduzytkownik", referencedColumnName = "iduzytkownik")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne(mappedBy = "post")
    @PrimaryKeyJoinColumn
    private Photo photo;






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
/*
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
*/

}
