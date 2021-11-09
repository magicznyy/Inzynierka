package com.example.RejestracjaLogowanie;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.DecimalFormat;

@Entity
@Table(name = "uzytkownik")

@SecondaryTables({
        @SecondaryTable(name  = "danelogowania", pkJoinColumns = @PrimaryKeyJoinColumn(name = "uzytkownik_iduzytkownik")),
        @SecondaryTable(name  = "danekontaktowe", pkJoinColumns = @PrimaryKeyJoinColumn(name = "uzytkownik_iduzytkownik"))
})

public class User {


    public User(){
        this.rola = 0;
        this.saldo = 0f;
        this.aktywnosc =  0;
        this.prywatnosckonta = 0;
        this.czyzbanowany = 0;
        //sol na razie tylko taka, żeby była
        this.solhasla ="soldupa2";
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


    //tabela danelogowania
    @Column(name = "email", table = "danelogowania")
    private String email;

    @Column( name = "hashhasla",table= "danelogowania")
    private String hashhasla;

    @Column( name = "solhasla",table= "danelogowania")
    private String solhasla;


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

    public String getSolhasla() {
        return solhasla;
    }

    public void setSolhasla(String solhasla) {
        this.solhasla = solhasla;
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
                '}';
    }
}
