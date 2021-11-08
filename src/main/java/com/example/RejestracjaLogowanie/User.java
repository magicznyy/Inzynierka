package com.example.RejestracjaLogowanie;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "uzytkownik")

public class User {


    @Id
    @GeneratedValue
    private Long id;


    @NotNull
    @Pattern(regexp = "[A-Za-z0-9]{4,30}")
    private String login;



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



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
