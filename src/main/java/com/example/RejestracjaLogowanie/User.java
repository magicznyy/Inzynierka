package com.example.RejestracjaLogowanie;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "uzytkownik")

@SecondaryTables({
        @SecondaryTable(name  = "danelogowania", pkJoinColumns = @PrimaryKeyJoinColumn(name = "uzytkownik_iduzytkownik")),
        @SecondaryTable(name  = "danekontaktowe", pkJoinColumns = @PrimaryKeyJoinColumn(name = "uzytkownik_iduzytkownik")),
        @SecondaryTable(name = "profil", pkJoinColumns = @PrimaryKeyJoinColumn(name = "uzytkownik_iduzytkownik"))
})
@Getter
@Setter
public class User implements UserDetails {

    public User(){
        this.rola = 0;
        this.saldo = 0f;
        this.aktywnosc =  0;
        this.prywatnosckonta = 0;
        this.czyzbanowany = 0;
        this.mapsCenterLatitude = 0d;
        this.mapsCenterLongitude = 0d;
    }

    @Id
    @GeneratedValue
    @Column(name = "iduzytkownik")
    private Long id;


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
    private byte prywatnosckonta;

    @Column(name = "czyzbanowany")// do inzynierki: roznica pomiedzy prytwatnosckonta a prywatnoscKonta
    private byte czyzbanowany;

    @Column(name = "rola")
    private byte rola;

    @OneToMany(targetEntity=Post.class , cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List <Post> posts = new ArrayList<>();

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    //tabela danelogowania
    @Column(name = "email", table = "danelogowania")
    private String email;

    @Column( name = "hashhasla",table= "danelogowania")
    private String hashhasla;

    //tabela danekontaktowe
    @Column( name = "imie",table= "danekontaktowe")
    private String imie;

    @Column( name = "nazwisko",table= "danekontaktowe")
    private String nazwisko;

    @Column( name = "nrkontabankowego",table= "danekontaktowe")
    private String nrkontabankowego;


    //tabela profil
    @Column(name = "opisprofilu" , table = "profil")
    private String profileDescription;

    @Column(name = "sciezkazdjecieprofilowe" , table = "profil")
    private String profilePicPath;

    @Column(name = "szerokoscgeograficzna" , table = "profil")
    private Double mapsCenterLongitude;

    @Column(name = "dlugoscgeograficzna" , table = "profil")
    private Double mapsCenterLatitude;



    //gettery i settery są potrzebne do zapisu i odczytu (chyba) z bazy

    public byte isAktywnosc() {
        return aktywnosc;
    }


    public byte isPrywatnoscKonta() {
        return prywatnosckonta;
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
                ", saldo='" + saldo + '\'' +
                ", aktywnosc='" + aktywnosc + '\'' +
                ", prywatnoscKonta='" + prywatnosckonta + '\'' +
                ", czyZbanowany='" + czyzbanowany + '\'' +
                ", rola='" + rola + '\'' +
                ", hash='" + hashhasla + '\'' +
                '}';
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
