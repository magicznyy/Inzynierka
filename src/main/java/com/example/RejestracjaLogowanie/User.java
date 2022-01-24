package com.example.RejestracjaLogowanie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "uzytkownik")

@SecondaryTables({
        @SecondaryTable(name  = "danelogowania", pkJoinColumns = @PrimaryKeyJoinColumn(name = "uzytkownik_iduzytkownik")),
        @SecondaryTable(name  = "danekontaktowe", pkJoinColumns = @PrimaryKeyJoinColumn(name = "uzytkownik_iduzytkownik")),
        @SecondaryTable(name = "profil", pkJoinColumns = @PrimaryKeyJoinColumn(name = "uzytkownik_iduzytkownik")),
        @SecondaryTable(name = "preferencjepogodowe", pkJoinColumns = @PrimaryKeyJoinColumn(name = "uzytkownik_iduzytkownik"))
})
@Getter
@Setter
public class User implements UserDetails {

    public User(){
        this.rola = 0;
        this.saldo = 0f;
        this.aktywnosc =  0;
        this.prywatnosckonta = null;
        this.czyzbanowany = 0;
        this.profilePicPath = "/images/profpic/nopicture.jpg";
        this.notificationsCounter = 0;
        this.mapsCenterLatitude = 52.237049;
        this.mapsCenterLongitude = 21.017532;
        this.preftemperatura = 15;
        this.prefpredkoscWiatru = 5;
        this.prefprawdopodobOpadow = 0;
        this.prefstopienZachmurzenia = 20;
        this.notificationsCounter = 0;
    }

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY )
    @Column(name = "iduzytkownik")
    private Long id;

    @Column(name = "iloscpowiadomien")
    private int notificationsCounter;

    //tabela uzytkownik
    @NotNull
    @Pattern(regexp = "[A-Za-z0-9]{4,30}")
    @Column(name = "login")
    private String login;

    @Column(name = "saldo")
    private Float saldo;

    @Column(name = "aktywnosc")
    private byte aktywnosc;

    @Column(name = "prywatnosckonta")
    private String prywatnosckonta;

    @Column(name = "czyzbanowany")// do inzynierki: roznica pomiedzy prytwatnosckonta a prywatnoscKonta
    private byte czyzbanowany;

    @Column(name = "rola")
    private byte rola;


    public List<Post> getPosts() {
        return posts;
    }

    @JsonIgnore
    @OneToMany(targetEntity=Post.class, cascade =CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    public List <Post> posts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(targetEntity=FollowedUser.class , orphanRemoval = true,fetch = FetchType.LAZY)
    private List <FollowedUser> followedUsers= new ArrayList<>();

    @JsonIgnore
    @OneToMany(targetEntity=Notification.class , orphanRemoval = true,fetch = FetchType.LAZY)
    private List <Notification> notifications = new ArrayList<>();;

    @JsonIgnore
    @OneToMany(targetEntity = Comment.class, cascade =CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List <Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(targetEntity = Reaction.class, cascade =CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List <Reaction> reactions = new ArrayList<>();

    @JsonIgnore
    @Column(name = "Temperatura")
    private int preftemperatura;

    @JsonIgnore
    @Column(name = "PredkoscWiatru")
    private int prefpredkoscWiatru;

    @JsonIgnore
    @Column(name = "StopienZachmurzenia")
    private int prefstopienZachmurzenia;

    @JsonIgnore
    @Column(name = "PrawdopodobienstwoOpadow")
    private int prefprawdopodobOpadow;

    public int getPreftemperatura() {
        return preftemperatura;
    }

    public int getPrefpredkoscWiatru() {
        return prefpredkoscWiatru;
    }

    public int getPrefstopienZachmurzenia() {
        return prefstopienZachmurzenia;
    }

    public int getPrefprawdopodobOpadow() {
        return prefprawdopodobOpadow;
    }

    public void setPreftemperatura(int preftemperatura) {
        this.preftemperatura = preftemperatura;
    }

    public void setPrefpredkoscWiatru(int prefpredkoscWiatru) {
        this.prefpredkoscWiatru = prefpredkoscWiatru;
    }

    public void setPrefstopienZachmurzenia(int prefstopienZachmurzenia) {
        this.prefstopienZachmurzenia = prefstopienZachmurzenia;
    }

    public void setPrefprawdopodobOpadow(int prefprawdopodobOpadow) {
        this.prefprawdopodobOpadow = prefprawdopodobOpadow;
    }

    public int getNotificationsCounter() {
        return notificationsCounter;
    }

    public void setNotificationsCounter(int notificationsCounter) {
        this.notificationsCounter = notificationsCounter;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public void addNotification(Notification notification){ this.notifications.add(notification);}

    public void addReaction(Reaction reaction)
    {
        this.reactions.add(reaction);
    }
    public void removeReaction(Reaction reaction){
        this.reactions.remove(reaction);
    }
    public void addComment(Comment comment){
        this.comments.add(comment);
    }
    public void addPost(Post post){
        this.posts.add(post);
    }

    @JsonIgnore
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void addFollowedUser(FollowedUser followedUser){
        this.followedUsers.add(followedUser);

    }

    public void removeFollowedUser(String login){
        FollowedUser followedUser = findFollowedUser(login);
        followedUsers.remove(followedUser);
    }

    public boolean isAlreadyFollowed(String login){

        for (FollowedUser user: followedUsers) {

           if(user.getFollowedUser().getLogin().equals(login)){
               return true;
           }

        }
        return false;
    }

    public FollowedUser findFollowedUser(String login){
        FollowedUser followedUser = null;
        for (FollowedUser user: followedUsers) {

            if(user.getFollowedUser().getLogin().equals(login))
            followedUser = user;
        }
        return followedUser;
    }



    public void setFollowedUsers(List<FollowedUser> followedUsers) {
        this.followedUsers = followedUsers;
    }

    //tabela danelogowania
    @JsonIgnore
    @Column(name = "email", table = "danelogowania")
    private String email;

    @JsonIgnore
    @Column( name = "hashhasla",table= "danelogowania")
    private String hashhasla;

    //tabela danekontaktowe
    @JsonIgnore
    @Column( name = "imie",table= "danekontaktowe")
    private String imie;

    @JsonIgnore
    @Column( name = "nazwisko",table= "danekontaktowe")
    private String nazwisko;

    @JsonIgnore
    @Column( name = "nrkontabankowego",table= "danekontaktowe")
    private String nrkontabankowego;


    //tabela profil
    @JsonIgnore
    @Column(name = "opisprofilu" , table = "profil")
    private String profileDescription;


    @Column(name = "sciezkazdjecieprofilowe" , table = "profil")
    private String profilePicPath;

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public void setProfilePicPath(String profilePicPath) {
        this.profilePicPath = profilePicPath;
    }

    @JsonIgnore
    @Column(name = "szerokoscgeograficzna" , table = "profil")
    private Double mapsCenterLatitude;

    @JsonIgnore
    @Column(name = "dlugoscgeograficzna" , table = "profil")
    private Double mapsCenterLongitude;



    //gettery i settery są potrzebne do zapisu i odczytu (chyba) z bazy

    public byte isAktywnosc() {
        return aktywnosc;
    }


    public void setPrywatnosckonta(String prywatnosckonta) {
        this.prywatnosckonta = prywatnosckonta;
    }



    public byte isCzyZbanowany() {
        return czyzbanowany;
    }


    public byte isRola() {
        return rola;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", aktywnosc='" + aktywnosc + '\'' +
                ", prywatnoscKonta='" + prywatnosckonta + '\'' +
                ", czyZbanowany='" + czyzbanowany + '\'' +
                ", rola='" + rola + '\'' +
                ", hash='" + hashhasla + '\''+
                '}';
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("USER"));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return null;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return null;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return false;
    }
}
