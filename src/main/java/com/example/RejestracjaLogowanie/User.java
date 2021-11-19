package com.example.RejestracjaLogowanie;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
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
        @SecondaryTable(name  = "post", pkJoinColumns = @PrimaryKeyJoinColumn(name = "uzytkownik_iduzytkownik"))
})

public class User implements UserDetails {

    public User(){
        this.rola = 0;
        this.saldo = 0f;
        this.aktywnosc =  0;
        this.prywatnosckonta = 0;
        this.czyzbanowany = 0;

    }

    @Id
    @GeneratedValue
    @Column(name = "id")
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

    @OneToMany(cascade = CascadeType.ALL)
    private List <Post> posts = new ArrayList<>();

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






    //gettery i settery są potrzebne do zapisu i odczytu (chyba) z bazy
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    public byte isAktywnosc() {
        return aktywnosc;
    }

    public void setAktywnosc(byte aktywnosc) {
        this.aktywnosc = aktywnosc;
    }

    public byte isPrywatnoscKonta() {
        return prywatnosckonta;
    }

    public void setPrywatnoscKonta(byte prywatnoscKonta) {
        this.prywatnosckonta = prywatnoscKonta;
    }

    public byte isCzyZbanowany() {
        return czyzbanowany;
    }

    public void setCzyZbanowany(byte czyZbanowany) {
        this.czyzbanowany = czyZbanowany;
    }

    public byte isRola() {
        return rola;
    }

    public void setRola(byte rola) {
        this.rola = rola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashhasla() {
        return hashhasla;
    }

    public void setHashhasla(String hashhasla) {
        this.hashhasla = hashhasla;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getNrkontabankowego() {
        return nrkontabankowego;
    }

    public void setNrkontabankowego(String nrkontabankowego) {
        this.nrkontabankowego = nrkontabankowego;
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
