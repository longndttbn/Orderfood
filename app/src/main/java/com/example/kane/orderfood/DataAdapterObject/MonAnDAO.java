package com.example.kane.orderfood.DataAdapterObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kane.orderfood.Database.CreateDatabase;
import com.example.kane.orderfood.Model.MonAn;

import java.util.ArrayList;

/**
 * Created by Kane on 6/15/2017.
 */

public class MonAnDAO {
    SQLiteDatabase database;

    public MonAnDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean themMonAn(MonAn monAn){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_MONAN_TENMONAN,monAn.getTenMonAn());
        contentValues.put(CreateDatabase.TB_MONAN_GIATIEN, monAn.getGiaTien());
        contentValues.put(CreateDatabase.TB_MONAN_MALOAI, monAn.getMaLoaiMonAn());
        contentValues.put(CreateDatabase.TB_MONAN_HINHANH, monAn.getHinhAnh());

        long kiemtra = database.insert(CreateDatabase.TB_MONAN,null,contentValues);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public ArrayList<MonAn> getTatCaMonAn(int iMaLoai){
        ArrayList<MonAn> arrMonAn = new ArrayList<MonAn>();
        String truyvan = "SELECT * FROM "+CreateDatabase.TB_MONAN+" WHERE "
                + CreateDatabase.TB_MONAN +"."+CreateDatabase.TB_MONAN_MALOAI+" = '"+iMaLoai+"'";

        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            MonAn monAn = new MonAn();
            monAn.setMaMonAn(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MAMONAN)));
            monAn.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));
            monAn.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            monAn.setMaLoaiMonAn(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MALOAI)));
            monAn.setHinhAnh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH))+"");

            arrMonAn.add(monAn);
            cursor.moveToNext();
        }
        return arrMonAn;
    }

    public boolean XoaMonAnTheoMaMon(int maMon){
        long kiemtra = database.delete(CreateDatabase.TB_MONAN,CreateDatabase.TB_MONAN_MAMONAN+ " = "+maMon,null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaMonAnTheoMaLoai(int maLoai){
        long kiemtra = database.delete(CreateDatabase.TB_MONAN,CreateDatabase.TB_MONAN_MALOAI+ " = "+maLoai,null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean CapNhatMonAnTheoMaMonAn(int maMon, String sTenMon, String sGiaTien, String sHinhAnh, int iMaLoai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_MONAN_TENMONAN,sTenMon);
        contentValues.put(CreateDatabase.TB_MONAN_GIATIEN,sGiaTien);
        contentValues.put(CreateDatabase.TB_MONAN_HINHANH,sHinhAnh);
        contentValues.put(CreateDatabase.TB_MONAN_MALOAI,iMaLoai);

        String dieukien = CreateDatabase.TB_MONAN + "."+CreateDatabase.TB_MONAN_MAMONAN+" = "+maMon;
        long kiemTra = database.update(CreateDatabase.TB_MONAN,contentValues,dieukien,null);
        if (kiemTra != 0){
            return true;
        }else {
            return false;
        }
    }
}
