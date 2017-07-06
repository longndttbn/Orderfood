package com.example.kane.orderfood.DataAdapterObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kane.orderfood.Database.CreateDatabase;
import com.example.kane.orderfood.Model.ThongKe;

import java.util.ArrayList;

/**
 * Created by Kane on 7/1/2017.
 */

public class ThongKeDAO {
    SQLiteDatabase database;

    public ThongKeDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean themThongKeTheoMaThongKe(ThongKe thongKe){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_THONGKE_MANHANVIEN,thongKe.getTenNhanvien());
        contentValues.put(CreateDatabase.TB_THONGKE_TONGTIEN,thongKe.getTongTien());
        contentValues.put(CreateDatabase.TB_THONGKE_NGAYTHANHTOAN,thongKe.getNgayThanhToan());

        long kiemTra = database.insert(CreateDatabase.TB_THONGKE,null,contentValues);
        if (kiemTra != 0){
            return true;
        }else {
            return false;
        }
    }

    public ArrayList<ThongKe> LayToanBoThongKe(){
        ArrayList<ThongKe> arrThongKe = new ArrayList<ThongKe>();
        String truyvan = "SELECT * FROM "+CreateDatabase.TB_THONGKE+" tk, "+CreateDatabase.TB_NHANVIEN+" nv "+
                " WHERE "+"tk."+CreateDatabase.TB_THONGKE_MANHANVIEN+" = "+"nv."+CreateDatabase.TB_NHANVIEN_TENNV;
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ThongKe thongKe = new ThongKe();
            thongKe.setMaThongKe(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_THONGKE_MATHONGKE)));
            thongKe.setTenNhanvien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_TENNV)));
            // Todo
            thongKe.setTongTien(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_THONGKE_TONGTIEN)));
            thongKe.setNgayThanhToan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_THONGKE_NGAYTHANHTOAN)));

            arrThongKe.add(thongKe);

            cursor.moveToNext();
        }
        return arrThongKe;
    }

    public boolean XoaThongKeTheoMaThongKe(int maThongKe){
        long kiemtra = database.delete(CreateDatabase.TB_THONGKE,CreateDatabase.TB_THONGKE_MATHONGKE+" = "+maThongKe,null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }
}
;