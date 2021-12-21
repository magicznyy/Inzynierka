package com.example.RejestracjaLogowanie;


import javax.persistence.*;


@Entity
@Table(name = "pinezka")
public class Pin {


    public Pin() {
    }

    public Pin(Double latitude, Double longitude, String pinDescription, String pinColor, User user, Photo photo) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.pinDescription = pinDescription;
        this.pinColor = pinColor;
        this.user = user;
        this.photo = photo;
    }


    @Id
    @GeneratedValue
    @Column(name = "idpinezka")
    private Long pinId;

    @Column(name = "szerokoscgeograficzna")
    private Double latitude;


    @Column(name = "dlugoscgeograficzna")
    private Double longitude;

    @Column(name = "opispinezki")
    private String pinDescription;

    @Column(name = "kolorpinezki")
    private String pinColor;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uzytkownik_iduzytkownik", referencedColumnName = "iduzytkownik")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "zdjecie_idzdjecie", referencedColumnName = "idzdjecie")
    private Photo photo;

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getPinId() {
        return pinId;
    }

    public void setPinId(Long pinId) {
        this.pinId = pinId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPinDescription() {
        return pinDescription;
    }

    public void setPinDescription(String pinDescription) {
        this.pinDescription = pinDescription;
    }

    public String getPinColor() {
        return pinColor;
    }

    public void setPinColor(String pinColor) {
        this.pinColor = pinColor;
    }

}