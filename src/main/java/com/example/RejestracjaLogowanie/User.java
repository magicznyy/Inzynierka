package com.example.RejestracjaLogowanie;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.DecimalFormat;

@Entity
@Table(name = "uzytkownik")

public class User {


    public User(){
        this.rola = 0;
        this.saldo = 0f;
        this.aktywnosc =  0;
        this.prywatnosckonta = 0;
        this.czyzbanowany = 0;
    }

    @Id
    @GeneratedValue
    private Long id;


    @NotNull
    @Pattern(regexp = "[A-Za-z0-9]{4,30}")

    private String login;
    private Float saldo;
    private byte aktywnosc;
    private byte prywatnosckonta; // do inzynierki: roznica pomiedzy prytwatnosckonta a prywatnoscKonta
    private byte czyzbanowany;
    private byte rola;



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
