package com.example.RejestracjaLogowanie;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post")
@SecondaryTables({
        @SecondaryTable(name="zdjecie",  pkJoinColumns = @PrimaryKeyJoinColumn(name = "post_idpost"))
})
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



    @Column(name="sciezka", table = "zdjecie")
    private String path;

    @Column(name="czynasprzedaz", table = "zdjecie")
    private byte isForSale;



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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte getIsForSale() {
        return isForSale;
    }

    public void setIsForSale(byte isForSale) {
        this.isForSale = isForSale;
    }
}
