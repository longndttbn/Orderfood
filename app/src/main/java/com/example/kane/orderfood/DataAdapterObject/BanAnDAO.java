package com.example.kane.orderfood.DataAdapterObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kane.orderfood.Database.CreateDatabase;
import com.example.kane.orderfood.Model.BanAn;

import java.util.ArrayList;

/**
 * Created by Kane on 6/12/2017.
 */

public class BanAnDAO {

    SQLiteDatabase database;

    public BanAnDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean themBanAn(String sTenBanAn){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN,sTenBanAn);
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG,"false");

        long kiemtra = database.insert(CreateDatabase.TB_BANAN,null,contentValues);

        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public ArrayList<BanAn> hienTatCaBanAn(){
        ArrayList<BanAn> arrBanAn = new ArrayList<BanAn>();
        String truyvan = "SELECT * FROM "+CreateDatabase.TB_BANAN;
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            BanAn banAn = new BanAn();
            banAn.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_BANAN_MABAN)));
            banAn.setTenBan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TENBAN)));

            arrBanAn.add(banAn);
            cursor.moveToNext();
        }
        return arrBanAn;
    }

    public String layTinhTrangBanAn(int iMaBan){
        String tinhtrang="";
        String truyvan = "SELECT * FROM "+CreateDatabase.TB_BANAN + " WHERE "+CreateDatabase.TB_BANAN_MABAN+" = '"+iMaBan+"'";
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tinhtrang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TINHTRANG));
            cursor.moveToNext();
        }
        return tinhtrang;
    }

    public boolean capNhatLaiTinhTrangBan(int iMaBan, String sTinhTrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG,sTinhTrang);

        long kiemtra = database.update(CreateDatabase.TB_BANAN,contentValues,CreateDatabase.TB_BANAN_MABAN +" = '"+iMaBan+"'",null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    // Xoa ban
    public boolean XoaBanAnTheoMaBan(int maBan){
        long kiemtra = database.delete(CreateDatabase.TB_BANAN,CreateDatabase.TB_BANAN_MABAN +" = "+maBan, null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean capNhatLaiTenBanTheoMaBan(int iMaBan, String sTenBan){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN,sTenBan);

        long kiemtra = database.update(CreateDatabase.TB_BANAN,contentValues,CreateDatabase.TB_BANAN_MABAN +" = '"+iMaBan+"'",null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }
}











