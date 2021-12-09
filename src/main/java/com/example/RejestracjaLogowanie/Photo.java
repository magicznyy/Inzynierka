package com.example.RejestracjaLogowanie;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="zdjecie")
public class Photo {

   public Photo(){

    }

    public Photo( String path) {
        this.path = path;
        this.isForSale = 0;

    }

    @Id
    @GeneratedValue
    @Column(name = "idzdjecie")
    private Long photoId;

    @Column(name = "sciezka")
    private String path;


    @Column(name = "czynasprzedaz")
    private byte isForSale;

    @JsonIgnore
    @OneToOne(mappedBy = "photo", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private Post post;

   @OneToOne(mappedBy = "photo", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private Pin pin;

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

    public Pin getPin() {
        return pin;
    }

    public void setPin(Pin pin) {
        this.pin = pin;
    }
}
