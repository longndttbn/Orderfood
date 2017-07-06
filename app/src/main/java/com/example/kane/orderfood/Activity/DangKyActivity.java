package com.example.kane.orderfood.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kane.orderfood.DataAdapterObject.NhanVienDAO;
import com.example.kane.orderfood.DataAdapterObject.QuyenDAO;
import com.example.kane.orderfood.FragmentApp.DatePickerFragment;
import com.example.kane.orderfood.FragmentApp.HienThiNhanVienFragment;
import com.example.kane.orderfood.Model.NhanVien;
import com.example.kane.orderfood.Model.Quyen;
import com.example.kane.orderfood.R;

import java.util.ArrayList;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private EditText edt_tendangnhapDK, edt_matkhauDK, edt_tennhanvienDK, edt_ngaysinhDK, edt_cmndDK;
    private RadioGroup rdg_gioitinhDK;
    private Button btn_dongy, btn_thoat;
    private TextView txtv_quyen;
    private Spinner spn_quyen;
    NhanVienDAO nhanVienDAO;
    int landangkydautien = 0;
    QuyenDAO quyenDAO;
    ArrayList<Quyen> arrQuyen;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_dongy.setOnClickListener(this);
        btn_thoat.setOnClickListener(this);
        edt_ngaysinhDK.setOnFocusChangeListener(this);
    }

    private void addControls() {
        edt_tendangnhapDK = (EditText) findViewById(R.id.edt_tendangnhapDK);
        edt_matkhauDK = (EditText) findViewById(R.id.edt_matkhauDK);
        edt_tennhanvienDK = (EditText) findViewById(R.id.edt_tennhanvienDK);
        edt_ngaysinhDK = (EditText) findViewById(R.id.edt_ngaysinhDK);
        edt_cmndDK = (EditText) findViewById(R.id.edt_cmndDK);
        txtv_quyen = (TextView) findViewById(R.id.txtv_quyen);
        rdg_gioitinhDK = (RadioGroup) findViewById(R.id.rdg_gioitinhDK);
        spn_quyen = (Spinner) findViewById(R.id.spn_quyen);
        btn_dongy = (Button) findViewById(R.id.btn_dongy);
        btn_thoat = (Button) findViewById(R.id.btn_thoat);
        nhanVienDAO = new NhanVienDAO(DangKyActivity.this);
        quyenDAO = new QuyenDAO(this);
        arrayList = new ArrayList<String>();

        landangkydautien = getIntent().getIntExtra("landangkydautien", 0);  // =1: Admin
        arrQuyen = quyenDAO.LayQuyen();
        if (arrQuyen.size() == 0){
            quyenDAO.themQuyen("Admin");
            quyenDAO.themQuyen("Nhân viên");
        }
        if (landangkydautien != 0){
            // Mặc định là Admin
            txtv_quyen.setVisibility(View.GONE);
            spn_quyen.setVisibility(View.GONE);
        }
        quyen();
    }

    private void quyen() {
        arrQuyen = quyenDAO.LayQuyen();
        // Do Adapter mặc định nhận vào kiểu String nên phải chuyển từ Quyen ve kieu String
        // Duyệt mảng
        for (int i=0; i< arrQuyen.size(); i++){
            String tenQuyen = arrQuyen.get(i).getTenQuyen();
            arrayList.add(tenQuyen);
        }
        adapter = new ArrayAdapter<String >(this,android.R.layout.simple_list_item_1,arrayList);
        spn_quyen.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_dongy:
                xuLyDongY();
                break;
            case R.id.btn_thoat:
                if (HienThiNhanVienFragment.THEM_REQUESTCODE_NHANVIEN != 0) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                }
                if (landangkydautien != 0){
                    Intent intent = new Intent(this,DangNhapActivity.class);
                    startActivity(intent);
                }
                finish();
                break;
        }
    }

    private void xuLyDongY() {
        String sTenDangNhap = edt_tendangnhapDK.getText().toString();
        String sMatKhau = edt_matkhauDK.getText().toString();
        String sTenNhanVien = edt_tennhanvienDK.getText().toString();

        String sGioiTinh = "";
        switch (rdg_gioitinhDK.getCheckedRadioButtonId()) {
            case R.id.rd_nam:
                sGioiTinh = "Nam";
                break;
            case R.id.rd_nu:
                sGioiTinh = "Nữ";
                break;
        }
        String sNgaySinh = edt_ngaysinhDK.getText().toString();
        int sCMND = Integer.parseInt(edt_cmndDK.getText().toString());

        if (sTenDangNhap == null || sTenDangNhap.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.nhaptendangnhap), Toast.LENGTH_SHORT).show();
            edt_tendangnhapDK.requestFocus();
        } else if (sMatKhau == null || sMatKhau.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.nhapmatkhau), Toast.LENGTH_SHORT).show();
            edt_matkhauDK.requestFocus();
        } else if (sTenNhanVien == null || sTenNhanVien.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.nhaptennhanvien), Toast.LENGTH_SHORT).show();
            edt_tennhanvienDK.requestFocus();
        } else {
            NhanVien nhanVien = new NhanVien();
            nhanVien.setTenDN(sTenDangNhap);
            nhanVien.setMatkhau(sMatKhau);
            nhanVien.setTenNV(sTenNhanVien);
            nhanVien.setGioiTinh(sGioiTinh);
            nhanVien.setNgaySinh(sNgaySinh);
            nhanVien.setCMND(sCMND);

            // Xét quyền
            if (landangkydautien != 0){
                nhanVien.setMaQuyen(1); // Mặc định là Admin
            }else {
                // Lay vi tri cua quyền
                int vitri = spn_quyen.getSelectedItemPosition();
                int maQuyen = arrQuyen.get(vitri).getMaQuyen();

                nhanVien.setMaQuyen(maQuyen);
            }
            long kiemtra = nhanVienDAO.ThemNhanVien(nhanVien);
            if (kiemtra != 0) {
                edt_tendangnhapDK.setText("");
                edt_matkhauDK.setText("");
                edt_tennhanvienDK.setText("");
                edt_ngaysinhDK.setText("");
                edt_cmndDK.setText("");
                Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }
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
