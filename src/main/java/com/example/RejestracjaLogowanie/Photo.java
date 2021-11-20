package com.example.RejestracjaLogowanie;


import javax.persistence.*;

@Entity
@Table(name="zdjecie")
public class Photo {

   public Photo(){

    }

    public Photo( String path, Post post) {
        this.path = path;
        this.isForSale = 0;
        this.post = post;
    }

    @Id
    @GeneratedValue
    @Column(name = "idzdjecie")
    private Long photoId;

    @Column(name = "sciezka")
    private String path;



    @Column(name = "czynasprzedaz")
    private byte isForSale;


    @OneToOne
    @MapsId
    @JoinColumn(name="post_idpost")
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

    public byte getIsForSale() {
        return isForSale;
    }

    public void setIsForSale(byte isForSale) {
        this.isForSale = isForSale;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
