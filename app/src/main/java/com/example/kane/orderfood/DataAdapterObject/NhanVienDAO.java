package com.example.kane.orderfood.DataAdapterObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kane.orderfood.Database.CreateDatabase;
import com.example.kane.orderfood.Model.NhanVien;

import java.util.ArrayList;

/**
 * Created by Kane on 6/10/2017.
 */

public class NhanVienDAO {
    SQLiteDatabase database;

    public NhanVienDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemNhanVien(NhanVien nv) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NHANVIEN_TENDN, nv.getTenDN());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MATKHAU, nv.getMatkhau());
        contentValues.put(CreateDatabase.TB_NHANVIEN_TENNV, nv.getTenNV());
        contentValues.put(CreateDatabase.TB_NHANVIEN_GIOITINH, nv.getGioiTinh());
        contentValues.put(CreateDatabase.TB_NHANVIEN_NGAYSINH, nv.getNgaySinh());
        contentValues.put(CreateDatabase.TB_NHANVIEN_CMND, nv.getCMND());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MAQUYEN, nv.getMaQuyen());

        long kiemtra = database.insert(CreateDatabase.TB_NHANVIEN, null, contentValues);

        return kiemtra;
    }

    public boolean kiemTraNhanVien(){
        String truyvan = "SELECT * FROM "+CreateDatabase.TB_NHANVIEN;
        Cursor cursor = database.rawQuery(truyvan,null);
        if (cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    public int kiemTraDangNhap(String sTenDangNhap, String sMatKhau){
        int maNV = 0;
        String truyvan = "SELECT * FROM "+CreateDatabase.TB_NHANVIEN+" WHERE "+ CreateDatabase.TB_NHANVIEN_TENDN +" = '"+sTenDangNhap
                +"' AND "+ CreateDatabase.TB_NHANVIEN_MATKHAU + " = '"+sMatKhau+"'";
        Cursor cursor = database.rawQuery(truyvan,null);
        // Dang nhap thanh cong hay that bai

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            maNV = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MANV));

            cursor.moveToNext();
        }
        return maNV;
    }

    //  Gửi mã quyền khi đăng nhập
    public int LayMaQuyenTheoMaNV(int maNV){
        String truyvan = "SELECT * FROM "+CreateDatabase.TB_NHANVIEN +" WHERE "+CreateDatabase.TB_NHANVIEN_MANV+" = "+maNV;
        int maQuyen = 0;
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            maQuyen = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MAQUYEN));
            cursor.moveToNext();
        }
        return maQuyen;
    }


    public ArrayList<NhanVien> LayTatCaNhanVien(){
        String truyvan = "SELECT * FROM "+CreateDatabase.TB_NHANVIEN;
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        ArrayList<NhanVien> arrNhanVien = new ArrayList<NhanVien>();
        while (!cursor.isAfterLast()){
            NhanVien nhanVien = new NhanVien();
            nhanVien.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MANV)));
            nhanVien.setTenDN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_TENDN)));
            nhanVien.setMatkhau(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MATKHAU)));
            nhanVien.setNgaySinh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_NGAYSINH)));
            nhanVien.setTenNV(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_TENNV)));
            nhanVien.setGioiTinh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_GIOITINH)));
            nhanVien.setCMND(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_CMND)));

            arrNhanVien.add(nhanVien);
            cursor.moveToNext();
        }
        return arrNhanVien;
    }
    public boolean XoaNhanVienTheoMaNV(int maNV){
        String dieukien = CreateDatabase.TB_NHANVIEN_MANV +" = "+maNV;
        long kiemtra = database.delete(CreateDatabase.TB_NHANVIEN,dieukien,null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean CapNhatNhanVienTheoMaNV(NhanVien nv){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NHANVIEN_TENDN,nv.getTenDN());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MATKHAU,nv.getMatkhau());
        contentValues.put(CreateDatabase.TB_NHANVIEN_TENNV,nv.getTenNV());
        contentValues.put(CreateDatabase.TB_NHANVIEN_GIOITINH,nv.getGioiTinh());
        contentValues.put(CreateDatabase.TB_NHANVIEN_NGAYSINH,nv.getNgaySinh());
        contentValues.put(CreateDatabase.TB_NHANVIEN_CMND,nv.getCMND());

        String dieukien = CreateDatabase.TB_NHANVIEN_MANV +" = "+nv.getMaNV();
        long kiemTra = database.update(CreateDatabase.TB_NHANVIEN,contentValues,dieukien, null);
        if (kiemTra != 0){
            return true;
        }else {
            return false;
        }
    }

    public String LayTenNhanVienTheoMaNV(int maNV){
        String tenNhanVien="";
        String truyvan = "SELECT * FROM "+CreateDatabase.TB_NHANVIEN+" WHERE "+CreateDatabase.TB_NHANVIEN_MANV+" = "+maNV;
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tenNhanVien = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_TENNV));

            cursor.moveToNext();
        }
        return tenNhanVien;
    }
}
