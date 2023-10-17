package com.example.readingbook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.readingbook.database.Dbhelper;
import com.example.readingbook.model.KindBook;

import java.util.ArrayList;

public class KindBookDAO {
    Dbhelper dbhelper;
    public KindBookDAO(Context context){
        dbhelper = new Dbhelper(context);
    }
    public ArrayList<KindBook> getAll(){
        ArrayList<KindBook> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String loaiSach = "SELECT * FROM LOAISACH";
        Cursor cursor = db.rawQuery(loaiSach,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Integer maLoai = cursor.getInt(0);
            String tenLoai = cursor.getString(1);
            String img = cursor.getString(2);
            list.add(new KindBook(maLoai, tenLoai,img));
            cursor.moveToNext();
        }
        db.close();
        return list;
    }
    public boolean add(KindBook item){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ten_loai",item.getName());
        values.put("hinh",item.getImage());
        long row = db.insert("LoaiSach",null,values);
        return row !=-1;
    }
    public boolean delete(Integer index){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        int row = db.delete("LoaiSach","ma_loai=?", new String[]{String.valueOf(index)});
        return row >0;
    }
    public boolean update(KindBook item){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ten_loai",item.getName());
        values.put("hinh",item.getImage());
        int row = db.update("LoaiSach",values,"ma_loai  =?",new String[]{String.valueOf(item.getId())});
        return row >0;
    }
}
