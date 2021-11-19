package com.example.RejestracjaLogowanie;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post")
public class Post {

   public  Post(){

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
