package com.example.readingbook.model;

import java.util.Date;

public class PhieuMuon {
    private Integer id;
    private String ngayMuon, ngayTra;
    private Integer maLoai;
    private Integer maThuThu;
    private String img;
    private String status;

    public PhieuMuon(Integer id, String ngayMuon, String ngayTra, Integer maLoai, Integer maThuThu, String img, String status) {
        this.id = id;
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayTra;
        this.maLoai = maLoai;
        this.maThuThu = maThuThu;
        this.img = img;
        this.status = status;
    }

    public PhieuMuon(Integer id, String ngayMuon, String ngayTra, String img, String status) {
        this.id = id;
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayTra;
        this.img = img;
        this.status = status;
    }

    public PhieuMuon(String ngayMuon, String ngayTra, Integer maLoai, Integer maThuThu, String img, String status) {
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayTra;
        this.maLoai = maLoai;
        this.maThuThu = maThuThu;
        this.img = img;
        this.status = status;
    }

    public PhieuMuon(String ngayMuon, String ngayTra, String img, String status) {
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayTra;
        this.img = img;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public Integer getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(Integer maLoai) {
        this.maLoai = maLoai;
    }

    public Integer getMaThuThu() {
        return maThuThu;
    }

    public void setMaThuThu(Integer maThuThu) {
        this.maThuThu = maThuThu;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
