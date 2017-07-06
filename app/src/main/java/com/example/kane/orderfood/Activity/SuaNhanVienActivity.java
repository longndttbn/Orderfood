package com.example.kane.orderfood.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.kane.orderfood.DataAdapterObject.NhanVienDAO;
import com.example.kane.orderfood.Database.CreateDatabase;
import com.example.kane.orderfood.FragmentApp.DatePickerFragment;
import com.example.kane.orderfood.Model.NhanVien;
import com.example.kane.orderfood.R;

import java.util.ArrayList;

public class SuaNhanVienActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private EditText edt_suatendangnhapDK, edt_suamatkhauDK, edt_suatennhanvienDK, edt_ngaysinhDK, edt_suacmndDK;
    private Button btn_suacapnhatnv, btn_suathoatnv;
    private RadioGroup rdg_suagioitinhDK;
    NhanVienDAO nhanVienDAO;
    ArrayList<NhanVien> arrNhanVien;
    int maNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_nhan_vien);

        // Lay ma nhan vien
        maNV = getIntent().getIntExtra(CreateDatabase.TB_NHANVIEN_MANV, 0);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_suacapnhatnv.setOnClickListener(this);
        btn_suathoatnv.setOnClickListener(this);
        edt_ngaysinhDK.setOnFocusChangeListener(this);
    }

    private void addControls() {
        edt_suatendangnhapDK = (EditText) findViewById(R.id.edt_suatendangnhapDK);
        edt_suamatkhauDK = (EditText) findViewById(R.id.edt_suamatkhauDK);
        edt_suatennhanvienDK = (EditText) findViewById(R.id.edt_suatennhanvienDK);
        edt_ngaysinhDK = (EditText) findViewById(R.id.edt_ngaysinhDK);
        edt_suacmndDK = (EditText) findViewById(R.id.edt_suacmndDK);
        rdg_suagioitinhDK = (RadioGroup) findViewById(R.id.rdg_suagioitinhDK);
        btn_suacapnhatnv = (Button) findViewById(R.id.btn_suacapnhatnv);
        btn_suathoatnv = (Button) findViewById(R.id.btn_suathoatnv);

        nhanVienDAO = new NhanVienDAO(this);
        arrNhanVien = nhanVienDAO.LayTatCaNhanVien();

        edt_suatendangnhapDK.setText(arrNhanVien.get(maNV - 1).getTenDN());
        edt_suamatkhauDK.setText(arrNhanVien.get(maNV - 1).getMatkhau());
        edt_suatennhanvienDK.setText(arrNhanVien.get(maNV - 1).getTenNV());
        edt_ngaysinhDK.setText(arrNhanVien.get(maNV - 1).getNgaySinh());
        edt_suacmndDK.setText(String.valueOf(arrNhanVien.get(maNV - 1).getCMND()));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_suacapnhatnv:
                //Todo
                String sTenDN = edt_suatendangnhapDK.getText().toString();
                String sMatKhau = edt_suamatkhauDK.getText().toString();
                String sTenNV = edt_suatennhanvienDK.getText().toString();
                String sGioiTinh = "";
                switch (rdg_suagioitinhDK.getCheckedRadioButtonId()) {
                    case R.id.rd_nam:
                        sGioiTinh = "Nam";
                        break;
                    case R.id.rd_nu:
                        sGioiTinh = "Nữ";
                        break;
                }
                String sNgaySinh = edt_ngaysinhDK.getText().toString();
                int sCMND = Integer.parseInt(edt_suacmndDK.getText().toString());

                if (sTenDN == null || sTenDN.equals("")) {
                    Toast.makeText(this, getResources().getString(R.string.nhaptendangnhap), Toast.LENGTH_SHORT).show();
                    edt_suatendangnhapDK.requestFocus();
                } else if (sMatKhau == null || sMatKhau.equals("")) {
                    Toast.makeText(this, getResources().getString(R.string.nhapmatkhau), Toast.LENGTH_SHORT).show();
                    edt_suamatkhauDK.requestFocus();
                } else if (sTenNV == null || sTenNV.equals("")) {
                    Toast.makeText(this, getResources().getString(R.string.nhaptennhanvien), Toast.LENGTH_SHORT).show();
                    edt_suatennhanvienDK.requestFocus();
                } else {
                    NhanVien nhanVien = new NhanVien();
                    nhanVien.setMaNV(maNV);
                    nhanVien.setTenDN(sTenDN);
                    nhanVien.setMatkhau(sMatKhau);
                    nhanVien.setTenNV(sTenNV);
                    nhanVien.setGioiTinh(sGioiTinh);
                    nhanVien.setNgaySinh(sNgaySinh);
                    nhanVien.setCMND(sCMND);
                    boolean kiemTraCapNhat = nhanVienDAO.CapNhatNhanVienTheoMaNV(nhanVien);
                    Intent iCapNhat = new Intent();
                    iCapNhat.putExtra("capnhat",kiemTraCapNhat);
                    setResult(RESULT_OK,iCapNhat);
                    finish();
                }
                break;
            case R.id.btn_suathoatnv:
                finish();
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.edt_ngaysinhDK:
                if (hasFocus) {
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getFragmentManager(), "Năm sinh");
                }
                break;
        }
    }
}
