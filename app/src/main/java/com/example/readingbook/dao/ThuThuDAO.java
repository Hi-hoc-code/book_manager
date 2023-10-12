package com.example.readingbook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.readingbook.database.Dbhelper;
import com.example.readingbook.model.NhanVien;

import java.util.ArrayList;

public class ThuThuDAO {
    Dbhelper dbhelper;
    public ThuThuDAO (Context context){
        dbhelper = new Dbhelper(context);
    }
    public boolean checkLoginNhanVien(String username, String password) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        String[]columns = { "ma_thu_thu" };
        String selection= "ten_dang_nhap = ? AND mat_khau = ?";
        String[]selectionArgs = { username, password };
        Cursor cursor = db.query("THUTHU", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }
    public ArrayList<NhanVien> getAll(){
        ArrayList<NhanVien> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper .getReadableDatabase();
        String nhanVien ="SELECT * FROM THUTHU";
        Cursor cursor = db.rawQuery(nhanVien,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Integer maNhanVien = cursor.getInt(0);
            String tenNhanVien = cursor.getString(1);
            String tenDangNhap = cursor.getString(2);
            String matKhau = cursor.getString(3);
            String img = cursor.getString(4);
            list.add(new NhanVien(maNhanVien,tenNhanVien,tenDangNhap,matKhau,img));
            cursor.moveToNext();
        }
        db.close();
        return list;
    }
    public boolean insert(NhanVien item ){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ten",item.getHoTen());
        values.put("ten_dang_nhap",item.getTenDangNhap());
        values.put("mat_khau", item.getMatKhau());
        values.put("avatar",item.getImg());
        long row = db.insert("THUTHU",null,values);
        return row!=1;
    }
    public boolean update(NhanVien item){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        int row = db.update("THUTHU",null,"ma_thu_thu=?",new String[]{String.valueOf(item.getMaNhanVien())});
        return row>0;
    }
    public boolean update(Integer index){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        int row = db.delete("THUTHU","ma_thu_thu=?",new String[]{String.valueOf(index)});
        return row>0;
    }
}
