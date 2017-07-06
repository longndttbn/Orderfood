package com.example.kane.orderfood.DataAdapterObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kane.orderfood.Database.CreateDatabase;
import com.example.kane.orderfood.Model.LoaiMonAn;

import java.util.ArrayList;

/**
 * Created by Kane on 6/14/2017.
 */

public class LoaiMonAnDAO {
    SQLiteDatabase database;

    public LoaiMonAnDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean themLoaiThucDon(String sTenLoaiThucDon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_LOAIMONAN_TENLOAI, sTenLoaiThucDon);

        long kiemtra = database.insert(CreateDatabase.TB_LOAIMONAN, null, contentValues);
        if (kiemtra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<LoaiMonAn> getTatCaLoaiThucDon() {
        ArrayList<LoaiMonAn> arrLoaiMonAn = new ArrayList<LoaiMonAn>();
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_LOAIMONAN;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LoaiMonAn loaiMonAn = new LoaiMonAn();
            loaiMonAn.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_MALOAI)));
            loaiMonAn.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_TENLOAI)));

            arrLoaiMonAn.add(loaiMonAn);
            cursor.moveToNext();
        }

        return arrLoaiMonAn;
    }

    public String getHinhAnhLoaiMonAn(int maLoai) {
        String hinhAnh = "";
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_MONAN + " WHERE "
                + CreateDatabase.TB_MONAN_MALOAI + " = '" + maLoai + "' AND "
                + CreateDatabase.TB_MONAN_HINHANH + " != '' ORDER BY "
                + CreateDatabase.TB_MONAN_MAMONAN + " LIMIT 1";

        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            hinhAnh = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH));
            cursor.moveToNext();
        }
        Log.d("hinhanh ", hinhAnh);
        return hinhAnh;
    }

    public boolean XoaLoaiMonAnTheoMaLoai(int maLoai){
        long kiemtra = database.delete(CreateDatabase.TB_LOAIMONAN,CreateDatabase.TB_LOAIMONAN_MALOAI + " = "+maLoai,null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean CapNhatLoaiTheoMaLoai(int maLoai, String tenLoai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_LOAIMONAN_TENLOAI,tenLoai);

        long kiemTra = database.update(CreateDatabase.TB_LOAIMONAN,contentValues,CreateDatabase.TB_LOAIMONAN_MALOAI +" = "+maLoai,null);
        if (kiemTra != 0){
            return true;
        }else {
            return false;
        }
    }
}
