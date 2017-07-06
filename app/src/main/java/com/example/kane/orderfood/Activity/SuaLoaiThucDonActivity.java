package com.example.kane.orderfood.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kane.orderfood.DataAdapterObject.LoaiMonAnDAO;
import com.example.kane.orderfood.Database.CreateDatabase;
import com.example.kane.orderfood.R;

public class SuaLoaiThucDonActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edt_suaLoai;
    private Button btn_suaDongYLoai, btn_suaHuyLoai;
    int maLoai;
    LoaiMonAnDAO loaiMonAnDAO;
    public static final String CAPNHATLOAI = "capnhatloai";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_loai_thuc_don);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_suaDongYLoai.setOnClickListener(this);
        btn_suaHuyLoai.setOnClickListener(this);
    }

    private void addControls() {
        edt_suaLoai = (EditText) findViewById(R.id.edt_suaLoaiThucDon);
        btn_suaDongYLoai = (Button) findViewById(R.id.btn_suaDongYLoai);
        btn_suaHuyLoai= (Button) findViewById(R.id.btn_SuaHuyLoai);

        maLoai = getIntent().getIntExtra(CreateDatabase.TB_LOAIMONAN_MALOAI,0);
        loaiMonAnDAO = new LoaiMonAnDAO(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_suaDongYLoai:
                xuLyDongYLoai();
                break;
            case R.id.btn_SuaHuyLoai:
                finish();
                break;
        }
    }

    private void xuLyDongYLoai() {
        String tenLoai = edt_suaLoai.getText().toString();
        boolean kiemTra = loaiMonAnDAO.CapNhatLoaiTheoMaLoai(maLoai,tenLoai);
        Intent iCapNhatLoai = new Intent();
        iCapNhatLoai.putExtra(CAPNHATLOAI,kiemTra);
        setResult(RESULT_OK,iCapNhatLoai);
        finish();
    }
}
