package com.example.kane.orderfood.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kane.orderfood.CustomAdapter.CustomAdapterHienBanAn;
import com.example.kane.orderfood.CustomAdapter.CustomAdapterHienMonAnThanhToan;
import com.example.kane.orderfood.DataAdapterObject.BanAnDAO;
import com.example.kane.orderfood.DataAdapterObject.GoiMonDAO;
import com.example.kane.orderfood.DataAdapterObject.NhanVienDAO;
import com.example.kane.orderfood.DataAdapterObject.ThongKeDAO;
import com.example.kane.orderfood.FragmentApp.HienThiBanAnFragment;
import com.example.kane.orderfood.FragmentApp.HienThiThongKeFragment;
import com.example.kane.orderfood.Model.ThanhToan;
import com.example.kane.orderfood.Model.ThongKe;
import com.example.kane.orderfood.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView lv_HienMonAnThanhToan;
    ArrayList<ThanhToan> arrThanhToan;
    CustomAdapterHienMonAnThanhToan adapterThanhToan;
    GoiMonDAO goiMonDAO;
    int tongtien = 0;
    private TextView txtv_tongtien;
    private Button btn_thanhtoan, btn_huythanhtoan;
    FragmentManager fragmentManager;
    BanAnDAO banAnDAO;
    int maBanThanhToan;
    String ngayThanhToan;
    int maNV;
    ThongKeDAO thongKeDAO;
    String tenNV;
    NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        goiMonDAO = new GoiMonDAO(this);
        banAnDAO = new BanAnDAO(this);
        thongKeDAO = new ThongKeDAO(this);
        nhanVienDAO = new NhanVienDAO(this);

        fragmentManager = getSupportFragmentManager();
        addControls();
        Intent iThanhToan = getIntent();
        maBanThanhToan = iThanhToan.getIntExtra(CustomAdapterHienBanAn.BUNDLE_MABAN, 0);
        if (maBanThanhToan != 0) {

            adapter();

            for (int i = 0; i < arrThanhToan.size(); i++) {
                int soLuong = arrThanhToan.get(i).getSoLuong();
                int giaTien = arrThanhToan.get(i).getGiaTien();

                tongtien += (soLuong * giaTien);
            }
            txtv_tongtien.setText(String.valueOf(tongtien));
        }
        addEvents();
    }

    private void addEvents() {
        btn_thanhtoan.setOnClickListener(this);
        btn_huythanhtoan.setOnClickListener(this);
    }

    private void addControls() {
        lv_HienMonAnThanhToan = (ListView) findViewById(R.id.lvHienThiMonAn);
        txtv_tongtien = (TextView) findViewById(R.id.txtv_tongtien);
        btn_thanhtoan = (Button) findViewById(R.id.btn_thanhtoan);
        btn_huythanhtoan = (Button) findViewById(R.id.btn_huyThanhtoan);
    }

    private void adapter() {
        int maGoiMon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maBanThanhToan, "false");
//        Log.d("maGoiMon ", maGoiMon+"");
        arrThanhToan = goiMonDAO.LayDanhSachMonAnTheoMaGoiMon(maGoiMon);
        adapterThanhToan = new CustomAdapterHienMonAnThanhToan(ThanhToanActivity.this, R.layout.custom_layout_hienthimonan, arrThanhToan);
        lv_HienMonAnThanhToan.setAdapter(adapterThanhToan);
        adapterThanhToan.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_thanhtoan:
                xuLyThanhToan();
                break;
            case R.id.btn_huyThanhtoan:
                xuLyHuyThanhToan();
                break;
        }
    }

    private void xuLyHuyThanhToan() {
        adapter();
        finish();
    }


    private void xuLyThanhToan() {
        boolean kiemTraBanAn = banAnDAO.capNhatLaiTinhTrangBan(maBanThanhToan, "false"); // Cap nhat ma ban: chưa ai ngồi. false: chưa ai ngồi, true: có ng ngồi
        boolean kiemTraGoiMon = goiMonDAO.CapNhatMaGoiMonTheoMaBan(maBanThanhToan, "true"); // Cập nhật đã thah toán. false: chưa thanh toán, true: đã thanh toán
        String tien = txtv_tongtien.getText().toString();
        if (tien.equals("0")) {
            Toast.makeText(this, getString(R.string.goimon), Toast.LENGTH_SHORT).show();
        } else {
            if (kiemTraBanAn && kiemTraGoiMon) {
                txtv_tongtien.setText("");
                Toast.makeText(this, R.string.dathanhtoan, Toast.LENGTH_SHORT).show();
                adapter();

                Calendar calendar = new GregorianCalendar();
                SimpleDateFormat format = new SimpleDateFormat("hh:mm dd/MM/yyyy");
                format.setTimeZone(calendar.getTimeZone());
                ngayThanhToan = format.format(calendar.getTime());
                Log.d("tongtienthanhtoan", tongtien + "");

                SharedPreferences sharedPreferences = getSharedPreferences(DangNhapActivity.FILE_NAME_SHARED, MODE_PRIVATE);
                maNV = sharedPreferences.getInt(DangNhapActivity.MA_NV, 0);
                tenNV = nhanVienDAO.LayTenNhanVienTheoMaNV(maNV);

                ThongKe thongKe = new ThongKe();
                thongKe.setNgayThanhToan(ngayThanhToan);
                thongKe.setTongTien(tongtien);
                thongKe.setTenNhanvien(tenNV);

                thongKeDAO.themThongKeTheoMaThongKe(thongKe);

            } else {
                Toast.makeText(this, R.string.thanhtoanthatbai, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
