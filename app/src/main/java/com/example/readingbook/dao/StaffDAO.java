package com.example.readingbook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.readingbook.database.Dbhelper;
import com.example.readingbook.model.Staff;

import java.util.ArrayList;

public class StaffDAO {
    Dbhelper dbhelper;
    public StaffDAO(Context context){
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
    public ArrayList<Staff> getAll(){
        ArrayList<Staff> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper .getReadableDatabase();
        String nhanVien ="SELECT * FROM THUTHU";
        Cursor cursor = db.rawQuery(nhanVien,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Integer maNhanVien = cursor.getInt(0);
            String tenNhanVien = cursor.getString(1);
            String sdt = cursor.getString(2);
            String email = cursor.getString(3);
            String matKhau = cursor.getString(4);
            String img = cursor.getString(5);
            list.add(new Staff(maNhanVien, tenNhanVien,sdt, email, matKhau, img));
            cursor.moveToNext();
        }
        db.close();
        return list;
    }
    public boolean insert(Staff item ){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ten_thu_thu",item.getTen());
        values.put("sdt",item.getSdt());
        values.put("email",item.getEmail());
        values.put("password",item.getPassword());
        values.put("image",item.getImage());
        long row = db.insert("THUTHU",null,values);
        return row!=1;
    }
    public boolean update(Staff item){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ten_thu_thu",item.getTen());
        values.put("sdt",item.getSdt());
        values.put("email",item.getEmail());
        values.put("password",item.getPassword());
        values.put("image",item.getImage());
        int row = db.update("THUTHU",values,"ma_thu_thu=?",new String[]{String.valueOf(item.getId())});
        return row>0;
    }
    public boolean delete(Integer index){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        long row = db.delete("THUTHU","ma_thu_thu=? ",new String[]{String.valueOf(index)});
        return row>0;
    }
}
