package com.example.kane.orderfood.DataAdapterObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kane.orderfood.Database.CreateDatabase;
import com.example.kane.orderfood.Model.ChiTietGoiMon;
import com.example.kane.orderfood.Model.GoiMon;
import com.example.kane.orderfood.Model.ThanhToan;

import java.util.ArrayList;

/**
 * Created by Kane on 6/18/2017.
 */

public class GoiMonDAO {
    SQLiteDatabase database;

    public GoiMonDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    // Them GoiMon
    public boolean themGoiMon(GoiMon goiMon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_MABAN, goiMon.getMaBanAn());
        contentValues.put(CreateDatabase.TB_GOIMON_MANV, goiMon.getMaNV());
        contentValues.put(CreateDatabase.TB_GOIMON_NGAYGOI, goiMon.getNgayGoi());
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG, goiMon.getTinhTrang());

        long kiemtra = database.insert(CreateDatabase.TB_GOIMON, null, contentValues);
        if (kiemtra != 0) {
            return true;
        } else {
            return false;
        }
    }

    // Xem thanh toán chưa? cho chi tiết gọi món
    public long LayMaGoiMonTheoMaBan(int maBan, String tinhTrang) {
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_GOIMON + " WHERE " + CreateDatabase.TB_GOIMON_MABAN + " = " + maBan +
                " AND " + CreateDatabase.TB_GOIMON_TINHTRANG + " = '" + tinhTrang + "'";

        long magoimon = 0;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            magoimon = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_MAGOIMON));
            cursor.moveToNext();
        }
        return magoimon;
    }

    // Kiểm tra có mã món ăn chưa?
    public boolean kiemTraMonAnDaTonTai(int maGoiMon, int maMonAn) {
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + maGoiMon +
                " AND " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = " + maMonAn;
        Cursor cursor = database.rawQuery(truyvan, null);
        // kiểm tra nó khác 0
        if (cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }

    // Lấy số lượng
    public int LaySoLuongMonAn(int maGoiMon, int maMonAn) {
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON +
                " = " + maGoiMon + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = " + maMonAn;
        int soluong = 0;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            soluong = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG));

            cursor.moveToNext();
        }
        return soluong;
    }

    // Cap nhat so luong
    public boolean CapNhatSoLuong(ChiTietGoiMon chiTietGoiMon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG, chiTietGoiMon.getSoLuong());

        int kiemTraCapNhat = database.update(CreateDatabase.TB_CHITIETGOIMON, contentValues,
                CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + chiTietGoiMon.getMaGoiMon() +
                        " AND " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = " + chiTietGoiMon.getMaMonAn(), null);
        if (kiemTraCapNhat != 0) {
            return true;
        } else {
            return false;
        }
    }
    // Them chi tiet goi mon

    public boolean ThemChiTietGoiMon(ChiTietGoiMon chiTietGoiMon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG, chiTietGoiMon.getSoLuong());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAGOIMON, chiTietGoiMon.getMaGoiMon());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAMONAN, chiTietGoiMon.getMaMonAn());

        long kiemTra = database.insert(CreateDatabase.TB_CHITIETGOIMON, null, contentValues);
        if (kiemTra != 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<ThanhToan> LayDanhSachMonAnTheoMaGoiMon(int magoimon){
        String truyvan = "SELECT * FROM "+CreateDatabase.TB_CHITIETGOIMON+" ct, "+CreateDatabase.TB_MONAN+" ma "+" WHERE "+
                "ct."+CreateDatabase.TB_CHITIETGOIMON_MAMONAN +" = "+"ma."+CreateDatabase.TB_MONAN_MAMONAN+" AND "+
                CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = '"+magoimon+"'";
        ArrayList<ThanhToan> arrThanhToan = new ArrayList<ThanhToan>();
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ThanhToan thanhToan = new ThanhToan();
            thanhToan.setSoLuong(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG)));
            thanhToan.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));
            thanhToan.setGiaTien(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));

            arrThanhToan.add(thanhToan);

            cursor.moveToNext();
        }
        return arrThanhToan;
    }

    public boolean CapNhatMaGoiMonTheoMaBan(int maBan, String tinhTrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG, tinhTrang);

        long kiemtra = database.update(CreateDatabase.TB_GOIMON,contentValues,CreateDatabase.TB_GOIMON_MABAN+" = "+maBan,null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }
}
