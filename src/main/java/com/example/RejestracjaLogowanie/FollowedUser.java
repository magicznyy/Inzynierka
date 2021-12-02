package com.example.RejestracjaLogowanie;


import javax.persistence.*;

@Entity
@IdClass(FollowedUserId.class)
@Table(name="obserwowanyuzytkownik")
public class FollowedUser {

    FollowedUser( User user, User followedUser){

        this.user = user;
        this.followedUser = followedUser;
    }

    @Id
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "uzytkownik_iduzytkownik")
    private User user;

    @Id
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "uzytkownik_iduzytkownikobserwowany")
    private User followedUser;

    private Long userId;
    private Long followedUserId;

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
