package com.example.kane.orderfood.Model;

/**
 * Created by Kane on 6/12/2017.
 */

public class BanAn {
    private int maBan;
    private String tenBan;
    private boolean duocChon;

    public boolean isDuocChon() {
        return duocChon;
    }

    public void setDuocChon(boolean duocChon) {
        this.duocChon = duocChon;
    }

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }
}
