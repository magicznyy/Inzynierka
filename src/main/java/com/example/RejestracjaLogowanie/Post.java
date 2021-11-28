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

    public  Post(String tags, String description, LocalDateTime date, User user, Photo photo){

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
    private LocalDateTime date;

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
    @JoinColumn(name="zdjecie_idzdjecie",referencedColumnName = "idzdjecie")
    private Photo photo;

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


    public List<String> extractTags() {
        Pattern pattern = Pattern.compile("#(\\S+)");
        Matcher matcher = pattern.matcher(this.getTags());
        List<String> tags = new ArrayList<String>();
        while (matcher.find()) {
            tags.add(matcher.group(1));
        }
        return tags;
    }

    public boolean matchTag(String value){
            List<String> tags = this.extractTags();
            for (String tag:tags) {
                if(tag.equals(value))
                    return true;
            }
        return false;
        }



}
