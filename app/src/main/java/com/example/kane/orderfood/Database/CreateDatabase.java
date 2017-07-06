package com.example.kane.orderfood.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kane on 6/10/2017.
 */

public class CreateDatabase extends SQLiteOpenHelper {
    public static final String TB_NHANVIEN = "NHANVIEN";
    public static final String TB_MONAN = "MONAN";
    public static final String TB_LOAIMONAN = "LOAIMONAN";
    public static final String TB_BANAN = "BANAN";
    public static final String TB_GOIMON = "GOIMON";
    public static final String TB_CHITIETGOIMON = "CHITIETGOIMON";
    public static final String TB_QUYEN = "QUYEN";
    public static final String TB_THONGKE = "THONGKE";

    // CAC TRUONG TRONG BANG
    // NHAN VIEN
    public static final String TB_NHANVIEN_MANV = "MANV";
    public static final String TB_NHANVIEN_TENDN = "TENDN";
    public static final String TB_NHANVIEN_MATKHAU = "MATKHAU";
    public static final String TB_NHANVIEN_TENNV = "TENNV";
    public static final String TB_NHANVIEN_GIOITINH = "GIOITINH";
    public static final String TB_NHANVIEN_NGAYSINH = "NGAYSINH";
    public static final String TB_NHANVIEN_CMND = "CMND";
    public static final String TB_NHANVIEN_MAQUYEN= "MAQUYEN";

    // QUYEN
    public static final String TB_QUYEN_MAQUYEN = "MAQUYEN";
    public static final String TB_QUYEN_TENQUYEN = "TENQUYEN";

    // LOAI MON AN
    public static final String TB_LOAIMONAN_MALOAI = "MALOAI";
    public static final String TB_LOAIMONAN_TENLOAI = "TENLOAI";

    // MON AN
    public static final String TB_MONAN_MAMONAN = "MAMONAN";
    public static final String TB_MONAN_TENMONAN = "TENMONAN";
    public static final String TB_MONAN_GIATIEN = "GIATIEN";
    public static final String TB_MONAN_MALOAI = "MALOAI";
    public static final String TB_MONAN_HINHANH = "HINHANH";

    // BAN AN
    public static final String TB_BANAN_MABAN = "MABAN";
    public static final String TB_BANAN_TENBAN = "TENBAN";
    public static final String TB_BANAN_TINHTRANG = "TINHTRANG";

    // GOI MON
    public static final String TB_GOIMON_MAGOIMON = "MAGOIMON";
    public static final String TB_GOIMON_MANV = "MANV";
    public static final String TB_GOIMON_NGAYGOI = "NGAYGOI";
    public static final String TB_GOIMON_TINHTRANG = "TINHTRANG";
    public static final String TB_GOIMON_MABAN = "MABAN";

    // CHI TIET GOI MON
    public static final String TB_CHITIETGOIMON_MAGOIMON = "MAGOIMON";
    public static final String TB_CHITIETGOIMON_MAMONAN = "MAMONAN";
    public static final String TB_CHITIETGOIMON_SOLUONG = "SOLUONG";

    // THONG KE
    public static final String TB_THONGKE_MATHONGKE = "MATHONGKE";
    public static final String TB_THONGKE_MANHANVIEN = "MANHANVIEN";
    public static final String TB_THONGKE_TONGTIEN  = "TONGTIEN";
    public static final String TB_THONGKE_NGAYTHANHTOAN = "NGAYTHANHTOAN";

    public CreateDatabase(Context context) {
        super(context, "OrderFood", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbNHANVIEN = "CREATE TABLE " + TB_NHANVIEN + " ( " + TB_NHANVIEN_MANV + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_NHANVIEN_TENDN + " TEXT, " + TB_NHANVIEN_MATKHAU + " TEXT, " + TB_NHANVIEN_TENNV + " TEXT, " + TB_NHANVIEN_GIOITINH + " TEXT, "
                + TB_NHANVIEN_NGAYSINH + " TEXT, " +TB_NHANVIEN_MAQUYEN+" INTEGER, "+ TB_NHANVIEN_CMND + " INTEGER )";

        String tbBANAN = "CREATE TABLE " + TB_BANAN + " ( " + TB_BANAN_MABAN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_BANAN_TENBAN + " TEXT, " + TB_BANAN_TINHTRANG + " TEXT )";

        String tbMONAN = "CREATE TABLE " + TB_MONAN + " ( " + TB_MONAN_MAMONAN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_MONAN_TENMONAN + " TEXT, " + TB_MONAN_GIATIEN + " TEXT, " + TB_MONAN_MALOAI + " TEXT, " +TB_MONAN_HINHANH+" TEXT )";

        String tbLOAIMONAN = "CREATE TABLE " + TB_LOAIMONAN + " ( " + TB_LOAIMONAN_MALOAI + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB_LOAIMONAN_TENLOAI + " TEXT )";

        String tbQUYEN = "CREATE TABLE " + TB_QUYEN + " ( " + TB_QUYEN_MAQUYEN+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB_QUYEN_TENQUYEN+ " TEXT )";

        String tbGOIMON = "CREATE TABLE " + TB_GOIMON + " ( " + TB_GOIMON_MAGOIMON + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_GOIMON_MANV + " INTEGER, " + TB_GOIMON_NGAYGOI + " TEXT, " + TB_GOIMON_TINHTRANG + " TEXT, " + TB_GOIMON_MABAN + " INTEGER )";

        String tbCHITIETGOIMON = "CREATE TABLE " + TB_CHITIETGOIMON + " ( " + TB_CHITIETGOIMON_MAGOIMON + " INTEGER, "
                + TB_CHITIETGOIMON_MAMONAN + " INTEGER, " + TB_CHITIETGOIMON_SOLUONG + " INTEGER, PRIMARY KEY ( " + TB_CHITIETGOIMON_MAGOIMON + ", " + TB_CHITIETGOIMON_MAMONAN + "))";

        String tbTHONGKE = "CREATE TABLE " + TB_THONGKE+ " ( " + TB_THONGKE_MATHONGKE+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_THONGKE_MANHANVIEN+ " INTEGER, " + TB_THONGKE_TONGTIEN+ " TEXT, " + TB_THONGKE_NGAYTHANHTOAN+ " TEXT )";
        db.execSQL(tbNHANVIEN);
        db.execSQL(tbBANAN);
        db.execSQL(tbMONAN);
        db.execSQL(tbQUYEN);
        db.execSQL(tbLOAIMONAN);
        db.execSQL(tbGOIMON);
        db.execSQL(tbCHITIETGOIMON);
        db.execSQL(tbTHONGKE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
}
