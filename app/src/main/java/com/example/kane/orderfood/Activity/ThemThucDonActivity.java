package com.example.kane.orderfood.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kane.orderfood.CustomAdapter.CustomAdapterHienThucDon;
import com.example.kane.orderfood.DataAdapterObject.LoaiMonAnDAO;
import com.example.kane.orderfood.DataAdapterObject.MonAnDAO;
import com.example.kane.orderfood.Model.LoaiMonAn;
import com.example.kane.orderfood.Model.MonAn;
import com.example.kane.orderfood.R;

import java.util.ArrayList;

public class ThemThucDonActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_themloai;
    private Spinner spn_loaithucdon;
    private ImageView imv_thucdon;
    private EditText edt_tenmon, edt_giatien;
    private Button btn_themmon, btn_huymon;
    String sDuongDanAnh;
    public static final String MONAN = "MONAN";

    MonAnDAO monAnDAO;
    ArrayList<LoaiMonAn> arrLoaiMonAn = null;
    CustomAdapterHienThucDon adapter;
    LoaiMonAnDAO loaiMonAnDAO;
    public static final int REQUESTCODE = 1;
    public static final int MOANH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themthucdon);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_themloai.setOnClickListener(this);
        imv_thucdon.setOnClickListener(this);
        btn_themmon.setOnClickListener(this);
        btn_huymon.setOnClickListener(this);
//        spn_loaithucdon.setOnItemSelectedListener(this);
    }

    private void addControls() {
        edt_tenmon = (EditText) findViewById(R.id.edt_tenmonan);
        edt_giatien = (EditText) findViewById(R.id.edt_giatien);
        btn_themloai = (Button) findViewById(R.id.btn_themloaithucdon);
        btn_themmon = (Button) findViewById(R.id.btn_themmonan);
        btn_huymon = (Button) findViewById(R.id.btn_huymonan);
        spn_loaithucdon = (Spinner) findViewById(R.id.spn_loaithucdon);
        imv_thucdon = (ImageView) findViewById(R.id.imv_thucdon);
        adapter();
        monAnDAO = new MonAnDAO(this);
    }

    private void adapter() {
        loaiMonAnDAO = new LoaiMonAnDAO(this);
        arrLoaiMonAn = loaiMonAnDAO.getTatCaLoaiThucDon();
        adapter = new CustomAdapterHienThucDon(this, R.layout.custom_layout_hienloaithucdon, arrLoaiMonAn);
        spn_loaithucdon.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_themloaithucdon:
                xuLyThemLoaiThucDon();
                break;
            case R.id.imv_thucdon:
                xuLyAnhThucDon();
                break;
            case R.id.btn_themmonan:
                xuLyThemMonAn();
                break;
            case R.id.btn_huymonan:
                finish();
                break;
        }
    }

    private void xuLyThemMonAn() {
        String sTenMon = edt_tenmon.getText().toString();
        String sGiaMon = edt_giatien.getText().toString();

        int vitri = spn_loaithucdon.getSelectedItemPosition();
        int iMaLoai = arrLoaiMonAn.get(vitri).getMaLoai();

        if (sTenMon == null || sTenMon.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.nhaptenmonan), Toast.LENGTH_SHORT).show();
        } else if (sGiaMon == null || sGiaMon.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.nhapgiatien), Toast.LENGTH_SHORT).show();
        } else {
            MonAn monAn = new MonAn();
            monAn.setTenMonAn(sTenMon);
            monAn.setGiaTien(sGiaMon);
            monAn.setMaLoaiMonAn(iMaLoai);
            monAn.setHinhAnh(sDuongDanAnh);

            boolean kiemtra = monAnDAO.themMonAn(monAn);
            if (kiemtra) {
                edt_tenmon.setText("");
                edt_giatien.setText("");

                Intent intent = new Intent();
                intent.putExtra(MONAN, kiemtra);
                setResult(Activity.RESULT_OK, intent);
                finish();
//                Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void xuLyAnhThucDon() {
        Intent iMoAnh = new Intent();
        if (Build.VERSION.SDK_INT < 19) {
            iMoAnh.setType("image/*");
            iMoAnh.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(iMoAnh, getResources().getString(R.string.chonhinh)), MOANH);
        } else {
            iMoAnh = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            iMoAnh.addCategory(Intent.CATEGORY_OPENABLE);
            iMoAnh.setType("*/*");
            startActivityForResult(Intent.createChooser(iMoAnh, getResources().getString(R.string.chonhinh)), MOANH);
        }
    }

    private void xuLyThemLoaiThucDon() {
        Intent iThemLoaiThucDon = new Intent(ThemThucDonActivity.this, ThemLoaiThucDonActivity.class);
        startActivityForResult(iThemLoaiThucDon, REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE) {
            if (resultCode == Activity.RESULT_OK) {
                Intent i = data;
                boolean kiemtra = i.getBooleanExtra(ThemLoaiThucDonActivity.TEN_LOAI, false);
                if (kiemtra) {
                    // Cap nhat lai
                    adapter();
                    Toast.makeText(this, R.string.themthanhcong, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.themthatbai, Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == MOANH) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                sDuongDanAnh = data.getData().toString();
                imv_thucdon.setImageURI(data.getData());
//                imv_thucdon.setImageURI();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
