package com.example.readingbook.model;

import android.content.Intent;

public class User {
    private Integer id;
    private String userName;
    private String email;
    private String pass;

    public User(Integer id, String userName, String email, String pass) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.pass = pass;
    }

    public User(String userName, String email, String pass) {
        this.userName = userName;
        this.email = email;
        this.pass = pass;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
