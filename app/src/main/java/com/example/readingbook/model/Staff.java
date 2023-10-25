package com.example.readingbook.model;

public class Staff {
    private Integer id ;
    private String ten;
    private String sdt;
    private String email;
    private String password;
    private String image;

    public Staff(Integer id, String ten, String sdt, String email, String password, String image) {
        this.id = id;
        this.ten = ten;
        this.sdt = sdt;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public Staff(String ten, String sdt, String email, String password, String image) {
        this.ten = ten;
        this.sdt = sdt;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}


