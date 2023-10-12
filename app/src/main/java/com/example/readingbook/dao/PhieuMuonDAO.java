package com.example.readingbook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.readingbook.database.Dbhelper;
import com.example.readingbook.model.PhieuMuon;

import java.util.ArrayList;
import java.util.Date;

//String phieu_muon = "CREATE TABLE PHIEUMUON(ma_phieu_muon integer primary key autoincrement," +
//        "ma_thanh_vien_pm integer," +
//        "ma_sach_pm integer," +
//        "ma_thu_thu_pm integer," +
//        "ngay_muon date," +
//        "ngay_tra date," +
//        "hinh_pm text," +
//        "foreign key(ma_thanh_vien_pm) references THANHVIEN(ma_thanh_vien)," +
//        "foreign key(ma_sach_pm) references SACH(ma_sach)," +
//        "foreign key(ma_thu_thu_pm) references THUTHU(ma_thu_thu))";


//private Integer id;
//private Date ngayMuon, ngayTra;
//private Integer maLoai;
//private Integer maThuThu;
//private String img;
public class PhieuMuonDAO {
    Dbhelper helper;
    public PhieuMuonDAO (Context context){
        helper = new Dbhelper(context);
    }
    public ArrayList<PhieuMuon> getAll(){
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String phieu_muon = "SELECT * FROM PHIEUMUON";
        Cursor cursor = db.rawQuery(phieu_muon,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Integer id = cursor.getInt(0);
            String ngayMuon  = cursor.getString(1);
            String ngayTra = cursor.getString(2);
            String img = cursor.getString(3);
            String status = cursor.getString(4);
            list.add(new PhieuMuon(id,ngayMuon,ngayTra,img,status));
            cursor.moveToNext();
        }
        db.close();
        return list;
    }
    public boolean insert(PhieuMuon item ){
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("ngay_muon",item.getNgayMuon());
        values.put("ngay_muon",item.getNgayMuon());
        values.put("ngay_tra",item.getNgayTra());
        values.put("status",item.getStatus());
        values.put("hinh_pm",item.getImg());
        long row = db.insert("PHIEUMUON",null,values);
        return row!=-1;
    }
    public boolean update(PhieuMuon item ){
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        long row = db.update("PHIEUMUON",values,"ma_phieu_muon=?",new String[]{String.valueOf(item.getId())});
        return row>0;
    }
    public boolean delete(Integer index ){
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        long row = db.delete("PHIEUMUON","ma_phieu_muon=?",new String[]{String.valueOf(index)});
        return row>0;
    }

}
