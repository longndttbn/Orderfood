package com.example.kane.orderfood.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kane.orderfood.DataAdapterObject.BanAnDAO;
import com.example.kane.orderfood.R;

public class ThemBanAnActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_tenbanan;
    private Button btn_dongy;
    private Button btn_huy;
    public static final String TEN_BAN_AN = "TenBanAn";
    BanAnDAO banAnDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thembanan);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_dongy.setOnClickListener(this);
        btn_huy.setOnClickListener(this);
    }

    private void addControls() {
        edt_tenbanan = (EditText) findViewById(R.id.edt_tenbanan);
        btn_dongy = (Button) findViewById(R.id.btn_dongy);
        btn_huy = (Button) findViewById(R.id.btn_huy);
        banAnDAO = new BanAnDAO(ThemBanAnActivity.this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_dongy:
                xuLyDongY();
                break;
            case R.id.btn_huy:
                xuLyHuy();
                break;
        }
    }

    private void xuLyHuy() {
        Toast.makeText(this, R.string.cancel, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void xuLyDongY() {

        String sTenBanAn = edt_tenbanan.getText().toString();
        if (sTenBanAn == null || sTenBanAn.equals("")) {
            Toast.makeText(this, "Bạn chưa nhập tên bàn!"+sTenBanAn, Toast.LENGTH_SHORT).show();
        } else if (sTenBanAn != null || !sTenBanAn.equals("")) {
            boolean kiemtra = banAnDAO.themBanAn(sTenBanAn);
            Intent iTenBanAn = new Intent();
            iTenBanAn.putExtra(TEN_BAN_AN, kiemtra);
            setResult(RESULT_OK, iTenBanAn);
            finish();
        }
    }
}
