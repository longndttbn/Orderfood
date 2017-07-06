package com.example.kane.orderfood.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kane.orderfood.CustomAdapter.CustomAdapterHienBanAn;
import com.example.kane.orderfood.DataAdapterObject.GoiMonDAO;
import com.example.kane.orderfood.Database.CreateDatabase;
import com.example.kane.orderfood.FragmentApp.HienThiMonAnFragment;
import com.example.kane.orderfood.Model.ChiTietGoiMon;
import com.example.kane.orderfood.R;

public class SoLuongActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edt_soluong;
    private Button btn_dongy, btn_huy;
    GoiMonDAO goiMonDAO;
    int maBan;
    int maMonAn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soluong);
        addControls();
        addEvents();
    }

    private void addControls() {
        Intent iSoLuong = getIntent();
        maBan = iSoLuong.getIntExtra(CustomAdapterHienBanAn.BUNDLE_MABAN, 0);
        maMonAn = iSoLuong.getIntExtra(CreateDatabase.TB_MONAN_MAMONAN,0);
//        Log.d("maMonAn: ", maMonAn+"");
        edt_soluong = (EditText) findViewById(R.id.edt_soluongmonan);
        btn_dongy = (Button) findViewById(R.id.btn_dongy);
        btn_huy = (Button) findViewById(R.id.btn_huy);

        goiMonDAO = new GoiMonDAO(this);
//        Log.d("Mabansoluong: ",maBan+"");
    }

    private void addEvents() {
        btn_dongy.setOnClickListener(this);
        btn_huy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_dongy:
                xuLyDongY(v);
                break;
            case R.id.btn_huy:
                finish();
                break;
        }
    }

    private void xuLyDongY(View v) {
        // Lấy tình trạng món ăn
        long maGoiMon = goiMonDAO.LayMaGoiMonTheoMaBan(maBan,"false");
        // Kiểm tra xem món ăn tôn tại chưa?
        boolean kiemtraTonTai = goiMonDAO.kiemTraMonAnDaTonTai((int) maGoiMon,maMonAn);
        if (kiemtraTonTai){
            // đã có: cập nhật lại
            int soluongcu = goiMonDAO.LaySoLuongMonAn((int) maGoiMon,maMonAn);
            int soluongmoi = Integer.parseInt(edt_soluong.getText().toString());
            int tongsoluong = soluongcu +soluongmoi;


            ChiTietGoiMon chiTietGoiMon = new ChiTietGoiMon();
            chiTietGoiMon.setMaGoiMon((int) maGoiMon);
            chiTietGoiMon.setMaMonAn(maMonAn);
            chiTietGoiMon.setSoLuong(tongsoluong);

            boolean kiemTraCapNhat = goiMonDAO.CapNhatSoLuong(chiTietGoiMon);
            if (kiemTraCapNhat){
                Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }

        }else {
            // nếu chưa có thì tạo mới
            int soLuongGoi = Integer.parseInt(edt_soluong.getText().toString());
            ChiTietGoiMon chiTietGoiMon = new ChiTietGoiMon();
            chiTietGoiMon.setMaMonAn(maMonAn);
            chiTietGoiMon.setMaGoiMon((int) maGoiMon);
            chiTietGoiMon.setSoLuong(soLuongGoi);

            goiMonDAO.ThemChiTietGoiMon(chiTietGoiMon);
        }
        finish();
    }
}
