package com.example.readingbook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.readingbook.database.Dbhelper;
import com.example.readingbook.model.Book;

import java.util.ArrayList;

public class SachDAO {
    Dbhelper dbhelper;

    public SachDAO(Context context) {
        dbhelper = new Dbhelper(context);
    }
    public ArrayList<Book> getAllBook() {
        ArrayList<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String sql = "SELECT * FROM SACH";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String author = cursor.getString(2);
            String loai = cursor.getString(3);
            int imgRes = cursor.getInt(4);
            bookList.add(new Book(id,name, author, loai, imgRes));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return bookList;
    }
    public ArrayList<Book> locSachTheoLoai(String category) {
        ArrayList<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String sql = "SELECT * FROM SACH WHERE loai = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{category});
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String author = cursor.getString(2);
            String loai = cursor.getString(3);
            int imgRes = cursor.getInt(4);
            bookList.add(new Book(id, name, author, loai, imgRes));
        }
        cursor.close();
        db.close();
        return bookList;
    }


    public boolean insert(Book book){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",book.getName());
        values.put("author",book.getAuthor());
        values.put("loai",book.getLoai());
        values.put("hinh",book.getImage());
        long check = db.insert("SACH",null,values);
        return check!=-1;
    }
    public boolean update(Book item ){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",item.getName());
        values.put("author",item.getAuthor());
        values.put("loai",item.getLoai());
        values.put("hinh",item.getImage());
        int row = db.update("SACH",values,"ma_sach=?",new String[]{String.valueOf(item.getId())});
        return row>0;
    }
    public boolean update(Integer index ){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        int row = db.update("SACH",values,"ma_sach=?",new String[]{String.valueOf(index)});
        return row>0;
    }
}
