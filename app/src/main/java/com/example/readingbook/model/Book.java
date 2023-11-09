package com.example.readingbook.model;

public class Book {
    private Integer id;
    private String name;
    private String author;
    private String loai;
    private String image;

    public Book(Integer id, String name, String author, String loai, String image) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.loai = loai;
        this.image = image;
    }

    public Book(String name, String author, String loai) {
        this.name = name;
        this.author = author;
        this.loai = loai;
    }

    public Book(String name, String author, String loai, String image) {
        this.name = name;
        this.author = author;
        this.loai = loai;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}


