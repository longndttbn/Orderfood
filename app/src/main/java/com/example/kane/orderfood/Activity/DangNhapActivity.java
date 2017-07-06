package com.example.kane.orderfood.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kane.orderfood.DataAdapterObject.NhanVienDAO;
import com.example.kane.orderfood.R;

public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_tendangnhapDN, edt_matkhauDN;
    private Button btn_dangnhap, btn_dangky;
    NhanVienDAO nhanVienDAO;
    int maQuyen = 0;
    public static final String FILE_NAME_SHARED = "luuquyen";
    public static final String MAQUYEN = "maquyen";
    public static final String TEN_DN = "Tendangnhap";
    public static final String MA_NV = "MaNV";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        addControls();
        addEvents();
    }

    private void addControls() {
        edt_tendangnhapDN = (EditText) findViewById(R.id.edt_tendangnhapDN);
        edt_matkhauDN = (EditText) findViewById(R.id.edt_matkhauDN);
        btn_dangnhap = (Button) findViewById(R.id.btn_dangnhap);
        btn_dangky = (Button) findViewById(R.id.btn_dangky);

        HienThiButtonDangKyVSDangNhap();
    }

    private void addEvents() {
        btn_dangnhap.setOnClickListener(this);
        btn_dangky.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_dangnhap:
                xuLyDangNhap();
                break;
            case R.id.btn_dangky:
                xuLyDangKy();
                break;
        }
    }

    private void xuLyDangKy() {
        Intent iDangKy = new Intent(DangNhapActivity.this, DangKyActivity.class);
        iDangKy.putExtra("landangkydautien", 1); // 1: Admin
        startActivity(iDangKy);
    }

    private void xuLyDangNhap() {
        String sTenDangNhap = edt_tendangnhapDN.getText().toString();
        String sMatKhau = edt_matkhauDN.getText().toString();

        nhanVienDAO = new NhanVienDAO(this);
        int kiemtra = nhanVienDAO.kiemTraDangNhap(sTenDangNhap, sMatKhau); // kiemtra: là mã nhân viên
        maQuyen = nhanVienDAO.LayMaQuyenTheoMaNV(kiemtra);

        // Lưu mã quyền vào Shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME_SHARED, this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(MAQUYEN, maQuyen);
        editor.putInt(MA_NV,kiemtra);
        editor.commit();

        if (kiemtra != 0) {
            Intent iTrangChu = new Intent(DangNhapActivity.this, TrangChuActivity.class);
            iTrangChu.putExtra(TEN_DN, sTenDangNhap);
            iTrangChu.putExtra(MA_NV, kiemtra);
            startActivity(iTrangChu);

        } else {
            Toast.makeText(this, getResources().getString(R.string.dangnhapthatbai), Toast.LENGTH_SHORT).show();
        }
    }

    private void HienThiButtonDangKyVSDangNhap() {
        nhanVienDAO = new NhanVienDAO(this);
        boolean kiemtra = nhanVienDAO.kiemTraNhanVien();
        // Co nhan vien
        if (kiemtra) {
            btn_dangky.setVisibility(View.GONE);
            btn_dangnhap.setVisibility(View.VISIBLE);
        } else {
            btn_dangky.setVisibility(View.VISIBLE);
            btn_dangnhap.setVisibility(View.GONE);
        }
    }

    // Khi an quay tro lai no update tai khoan
    @Override
    protected void onResume() {
        super.onResume();
        HienThiButtonDangKyVSDangNhap();
    }
}
