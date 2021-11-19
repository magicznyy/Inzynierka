package com.example.RejestracjaLogowanie;


import javax.persistence.*;

@Entity
@Table(name="zdjecie")
public class Photo {

   public Photo(){

    }

    @Id
    @GeneratedValue
    @Column(name = "idzdjecie")
    private Long photoId;

    @Column(name = "sciezka")
    private String path;

    @Column(name = "czynasprzedaz")
    private Long isForSale;

    @OneToOne
    @MapsId
    @JoinColumn(name="postid")
    private Post post;

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getIsForSale() {
        return isForSale;
    }

    public void setIsForSale(Long isForSale) {
        this.isForSale = isForSale;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
