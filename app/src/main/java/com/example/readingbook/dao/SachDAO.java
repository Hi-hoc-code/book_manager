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
        String sql = "SELECT * FROM BOOK";
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


    public boolean addBook(Book book, byte[] imageByteArray){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",book.getName());
        values.put("author",book.getAuthor());
        values.put("loai",book.getLoai());
        values.put("hinh",imageByteArray);
        long check = db.insert("BOOK",null,values);
        return check!=-1;
    }
}
