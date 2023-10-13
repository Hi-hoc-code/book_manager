package com.example.readingbook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.readingbook.database.Dbhelper;
import com.example.readingbook.model.KhachHang;
import com.example.readingbook.model.PhieuMuon;

import java.util.ArrayList;

public class ThanhVienDAO {
    Dbhelper dbhelper;
    public ThanhVienDAO(Context context){
        dbhelper = new Dbhelper(context);
    }
    public ArrayList<KhachHang> getAll(){
        ArrayList<KhachHang> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String khach_hang = "SELECT * FROM KHACHHANG";
        Cursor cursor = db.rawQuery(khach_hang,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Integer id = cursor.getInt(0);
            String ten  = cursor.getString(1);
            String sdt = cursor.getString(2);
            String email = cursor.getString(3);
            String password = cursor.getString(4);
            String img = cursor.getString(5);
            list.add(new KhachHang(id, ten,sdt, email,password,img));
            cursor.moveToNext();
        }
        db.close();
        return list;
    }
    public boolean insert(KhachHang item ){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        ContentValues values = new ContentValues();
//        values.put("ma_khach_hang",item.getId());
        values.put("ten_khach_hang",item.getTen());
        values.put("sdt",item.getSdt());
        values.put("email",item.getEmail());
        values.put("password",item.getPassword());
        values.put("image",item.getImage());
        long row = db.insert("KHACHHANG",null,values);
        return row!=-1;
    }
    public boolean update(KhachHang item ){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        int row = db.update("KHACHHANG",values,"ma_khach_hang=?",new String[]{String.valueOf(item.getId())});
        return row>0;
    }
    public boolean delete(Integer index ){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        int row = db.delete("KHACHHANG","ma_khach_hang=?",new String[]{String.valueOf(index)});
        return row>0;
    }
}
