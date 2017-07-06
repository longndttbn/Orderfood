package com.example.kane.orderfood.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kane.orderfood.DataAdapterObject.LoaiMonAnDAO;
import com.example.kane.orderfood.R;

public class ThemLoaiThucDonActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_tenloaithucdon;
    private Button btn_dongy, btn_huy;
    LoaiMonAnDAO loaiMonAnDAO;
    public static final String TEN_LOAI = "Tenloai";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_loai_thuc_don);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_dongy.setOnClickListener(this);
        btn_huy.setOnClickListener(this);
    }

    private void addControls() {
        edt_tenloaithucdon = (EditText) findViewById(R.id.edt_tenloaithucdon);
        btn_dongy = (Button) findViewById(R.id.btn_dongy);
        btn_huy = (Button) findViewById(R.id.btn_huy);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_dongy:
                xuLyDongY();
                break;
            case R.id.btn_huy:
                finish();
                break;
        }
    }

    private void xuLyDongY() {
        String sTenLoai = edt_tenloaithucdon.getText().toString();

        if (sTenLoai == null || sTenLoai.equals("")) {
            Toast.makeText(this, "Mời nhập tên loại thực đơn", Toast.LENGTH_SHORT).show();
        } else {
            loaiMonAnDAO = new LoaiMonAnDAO(this);
            boolean kiemtra = loaiMonAnDAO.themLoaiThucDon(sTenLoai);
            Intent intent = new Intent();
            intent.putExtra(TEN_LOAI,kiemtra);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}
