package com.example.kane.orderfood.Model;

/**
 * Created by Kane on 7/1/2017.
 */

public class ThongKe {
    private int maThongKe;
    private String tenNhanvien;
    private int tongTien;
    private String ngayThanhToan;

    public int getMaThongKe() {
        return maThongKe;
    }

    public void setMaThongKe(int maThongKe) {
        this.maThongKe = maThongKe;
    }

    public String getTenNhanvien() {
        return tenNhanvien;
    }

    public void setTenNhanvien(String tenNhanvien) {
        this.tenNhanvien = tenNhanvien;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(String ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }
}
