package com.example.kane.orderfood.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kane.orderfood.CustomAdapter.CustomAdapterHienLoaiMonAn;
import com.example.kane.orderfood.CustomAdapter.CustomAdapterHienThucDon;
import com.example.kane.orderfood.DataAdapterObject.LoaiMonAnDAO;
import com.example.kane.orderfood.DataAdapterObject.MonAnDAO;
import com.example.kane.orderfood.Database.CreateDatabase;
import com.example.kane.orderfood.Model.LoaiMonAn;
import com.example.kane.orderfood.Model.MonAn;
import com.example.kane.orderfood.R;

import java.util.ArrayList;

import static com.example.kane.orderfood.Activity.ThemThucDonActivity.MOANH;

public class SuaMonAnActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_suaTenMon;
    private EditText edt_suaGiaTien;
    private ImageView imv_suathucdon;
    private Spinner spn_sualoaithucdon;
    private Button btn_suamonan, btn_suahuymon, btn_suathemloaithucdon;
    int maMon;
    int maLoai;
    String sDuongDanAnh;
    MonAnDAO monAnDAO;
    ArrayList<MonAn> arrMonAn;
    LoaiMonAnDAO loaiMonAnDAO;
    ArrayList<LoaiMonAn> arrLoaiMonAn;
    CustomAdapterHienThucDon adapter;
    public static final String CAPNHATMONAN = "capnhat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_mon_an);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_suamonan.setOnClickListener(this);
        btn_suahuymon.setOnClickListener(this);
        imv_suathucdon.setOnClickListener(this);
        btn_suathemloaithucdon.setOnClickListener(this);
    }

    private void addControls() {
        edt_suaTenMon = (EditText) findViewById(R.id.edt_suaTenMonAn);
        edt_suaGiaTien = (EditText) findViewById(R.id.edt_suaGiaTien);
        imv_suathucdon = (ImageView) findViewById(R.id.imv_suathucdon);
        btn_suamonan = (Button) findViewById(R.id.btn_suamonan);
        btn_suahuymon = (Button) findViewById(R.id.btn_suahuymonan);
        spn_sualoaithucdon = (Spinner) findViewById(R.id.spn_sualoaithucdon);
        btn_suathemloaithucdon = (Button) findViewById(R.id.btn_suathemloaithucdon);

        maMon = getIntent().getIntExtra(CreateDatabase.TB_MONAN_MAMONAN, 0);
        maLoai = getIntent().getIntExtra(CreateDatabase.TB_MONAN_MALOAI, 0);

        monAnDAO = new MonAnDAO(this);

        arrMonAn = monAnDAO.getTatCaMonAn(maLoai);

        Log.d("arrMonAnSuaNhan ", arrMonAn.get(maMon - 1).getTenMonAn() + "");

        edt_suaTenMon.setText(arrMonAn.get(maMon - 1).getTenMonAn());
        edt_suaGiaTien.setText(arrMonAn.get(maMon - 1).getGiaTien());
        String viTriAnh = arrMonAn.get(maMon - 1).getHinhAnh();
        Uri uri = Uri.parse(viTriAnh);
        imv_suathucdon.setImageURI(uri);
        adapter();

    }

    private void adapter() {
        loaiMonAnDAO = new LoaiMonAnDAO(this);
        arrLoaiMonAn = loaiMonAnDAO.getTatCaLoaiThucDon();
        adapter = new CustomAdapterHienThucDon(this, R.layout.custom_layout_hienloaithucdon, arrLoaiMonAn);
        spn_sualoaithucdon.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_suamonan:
                xuLySuaMonAn();
                break;
            case R.id.btn_suahuymonan:
                finish();
                break;
            case R.id.imv_suathucdon:
                xuLySuaAnh();
                break;
            case R.id.btn_suathemloaithucdon:
                xuLySuaLoaiThucDon();
                break;
        }
    }

    private void xuLySuaLoaiThucDon() {
        Intent iThemLoaiThucDon = new Intent(SuaMonAnActivity.this, ThemLoaiThucDonActivity.class);
        startActivityForResult(iThemLoaiThucDon, ThemThucDonActivity.REQUESTCODE);
    }

    private void xuLySuaAnh() {
        Intent iMoAnh = new Intent();
        iMoAnh.setType("image/*");
        iMoAnh.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(iMoAnh, getResources().getString(R.string.chonhinh)), MOANH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ThemThucDonActivity.REQUESTCODE) {
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
                imv_suathucdon.setImageURI(data.getData());
//                imv_thucdon.setImageURI();
            }
        }
    }

    private void xuLySuaMonAn() {
        String sTenMon = edt_suaTenMon.getText().toString();
        String sGiaTien = edt_suaGiaTien.getText().toString();

        if (sTenMon == null || sTenMon.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.nhaptenmonan), Toast.LENGTH_SHORT).show();
        } else if (sGiaTien == null || sGiaTien.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.nhapgiatien), Toast.LENGTH_SHORT).show();
        } else {
            boolean kiemTra = monAnDAO.CapNhatMonAnTheoMaMonAn(maMon, sTenMon, sGiaTien, sDuongDanAnh, maLoai);
            Intent iCapNhatSuaMon = new Intent();
            iCapNhatSuaMon.putExtra(CAPNHATMONAN, iCapNhatSuaMon);
            setResult(RESULT_OK, iCapNhatSuaMon);
            finish();
        }
    }
}
