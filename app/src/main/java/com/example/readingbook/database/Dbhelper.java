package com.example.readingbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.readingbook.R;

import java.io.ByteArrayOutputStream;

public class Dbhelper extends SQLiteOpenHelper {
    public Dbhelper( Context context) {
        super(context, "BM", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String loai_sach = "CREATE TABLE LOAISACH(ma_loai integer primary key autoincrement," +
                "ten_loai text," +
                "hinh text)";
        db.execSQL(loai_sach);
        String sach = "CREATE TABLE SACH(ma_sach integer primary key autoincrement," +
                "ten_sach text," +
                "tac_gia text," +
                "loai text," +
                "hinh text," +
                "ma_loai integer," +
                "foreign key (ma_loai) references LOAISACH(ma_loai))";
        db.execSQL(sach);
        String phieu_muon = "CREATE TABLE PHIEUMUON(ma_phieu_muon integer primary key autoincrement," +
                "ma_thanh_vien_pm integer," +
                "ma_sach_pm integer," +
                "ma_thu_thu_pm integer," +
                "ngay_muon date," +
                "ngay_tra date," +
                "status text," +
                "hinh_pm text," +
                "foreign key(ma_thanh_vien_pm) references THANHVIEN(ma_thanh_vien)," +
                "foreign key(ma_sach_pm) references SACH(ma_sach)," +
                "foreign key(ma_thu_thu_pm) references THUTHU(ma_thu_thu))";
        db.execSQL(phieu_muon);
        String thu_thu = "CREATE TABLE THUTHU(ma_thu_thu integer primary key autoincrement," +
                "ten text," +
                "ten_dang_nhap text," +
                "mat_khau text," +
                "avatar)";
        db.execSQL(thu_thu);
        String khach_hang = "CREATE TABLE KHACHHANG(" +
                "ma_khach_hang integer primary key autoincrement," +
                "ten_khach_hang text," +
                "sdt text," +
                "email text," +
                "password text," +
                "image text)";
        db.execSQL(khach_hang);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS LOAISACH");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SACH");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THANHVIEN");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THUTHU");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS KHACHHANG");
        onCreate(sqLiteDatabase);
    }
}
