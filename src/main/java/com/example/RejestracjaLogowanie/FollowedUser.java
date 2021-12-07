package com.example.RejestracjaLogowanie;


import javax.persistence.*;

@Entity
@Table(name="obserwowanyuzytkownik")
public class FollowedUser {

    public FollowedUser() {
    }

    public FollowedUser(User user, User followedUser){

        this.user = user;
        this.followedUser = followedUser;
    }

    @Id
    @GeneratedValue
    @Column(name="idobserwowanyuzytkownik")
    private Long idFollowedUser;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "uzytkownik_iduzytkownik")
    private User user;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "uzytkownik_iduzytkownikobserwowany")
    private User followedUser;


    public Long getIdFollowedUser() {
        return idFollowedUser;
    }

    public void setIdFollowedUser(Long idFollowedUser) {
        this.idFollowedUser = idFollowedUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFollowedUser() {
        return followedUser;
    }

    public void setFollowedUser(User followedUser) {
        this.followedUser = followedUser;
    }
}
