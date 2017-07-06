package com.example.kane.orderfood.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kane.orderfood.DataAdapterObject.BanAnDAO;
import com.example.kane.orderfood.Database.CreateDatabase;
import com.example.kane.orderfood.FragmentApp.HienThiBanAnFragment;
import com.example.kane.orderfood.R;

public class SuaBanAnActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edt_suaTenBanAn;
    private Button btn_dongYSuaBanAn, btn_huySuaBanAn;
    int maBan;
    BanAnDAO banAnDAO;
    public static final String CAPNHATTENBAN="capnhat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_ban_an);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_dongYSuaBanAn.setOnClickListener(this);
        btn_huySuaBanAn.setOnClickListener(this);
    }

    private void addControls() {
        edt_suaTenBanAn = (EditText) findViewById(R.id.edt_suaTenBanAn);
        btn_dongYSuaBanAn = (Button) findViewById(R.id.btn_dongySuaBanAn);
        btn_huySuaBanAn = (Button) findViewById(R.id.btn_huySuaBanAn);

        maBan = getIntent().getIntExtra(CreateDatabase.TB_BANAN_MABAN,0);
        banAnDAO = new BanAnDAO(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_dongySuaBanAn:
                xuLySuaBanAn();
                break;
            case R.id.btn_huySuaBanAn:
                finish();
                break;
        }
    }

    private void xuLySuaBanAn() {
        String tenBan = edt_suaTenBanAn.getText().toString();
        if (tenBan.trim().equals("") || tenBan.trim() == null){
            Toast.makeText(this, R.string.nhaptenbanan, Toast.LENGTH_SHORT).show();
        }else {
            boolean capnhat = banAnDAO.capNhatLaiTenBanTheoMaBan(maBan,tenBan);
            Intent iCapNhatTenBan = new Intent();
            iCapNhatTenBan.putExtra(CAPNHATTENBAN,capnhat);
            setResult(RESULT_OK,iCapNhatTenBan);
            finish();
        }
    }

}
