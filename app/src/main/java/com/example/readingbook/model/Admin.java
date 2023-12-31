package com.example.readingbook.model;

import java.util.HashMap;

public class Admin {
    private String id;
    private String userName;
    private String email;
    private String pass;

    public Admin(String id, String userName, String email, String pass) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.pass = pass;
    }

    public Admin(String userName, String email, String pass) {
        this.userName = userName;
        this.email = email;
        this.pass = pass;
    }

    public Admin() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    public HashMap<String, Object> convertHashMap(){
        HashMap<String, Object> work = new HashMap<>();
        work.put("id", id);
        work.put("username", userName);
        work.put("email", email);
        work.put("pass", pass);
        return  work;
    }
}
