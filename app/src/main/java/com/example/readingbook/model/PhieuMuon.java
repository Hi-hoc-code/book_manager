package com.example.readingbook.model;

import java.util.Date;

public class PhieuMuon {
    private Integer id;
    private Date date;
    private String note;

    public PhieuMuon(Integer id, Date date, String note) {
        this.id = id;
        this.date = date;
        this.note = note;
    }

    public PhieuMuon(Date date, String note) {
        this.date = date;
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
