package com.example.kane.orderfood.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kane.orderfood.FragmentApp.HienThiBanAnFragment;
import com.example.kane.orderfood.FragmentApp.HienThiNhanVienFragment;
import com.example.kane.orderfood.FragmentApp.HienThiThongKeFragment;
import com.example.kane.orderfood.FragmentApp.HienThiThucDonFragment;
import com.example.kane.orderfood.R;

public class TrangChuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TextView txtv_tendangnhap;
    FragmentManager fragmentManager;
    int maNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);
        addControls();
        addEvents();
    }

    private void addEvents() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void addControls() {
        Intent intent = getIntent();
        String sTenDangNhap = intent.getStringExtra(DangNhapActivity.TEN_DN);
        maNV = intent.getIntExtra(DangNhapActivity.MA_NV,0);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view_trangchu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);  // app_name
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // back

        // xu ly drawerlayout
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(TrangChuActivity.this,drawerLayout,toolbar,R.string.mo,R.string.dong){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // Xu ly mau cho item cua navigationview
        navigationView.setItemIconTintList(null);

        // controls:  Do cai view nay nam trong navigation
        View view = navigationView.inflateHeaderView(R.layout.layout_hearder_navigation);
//        Log.d("Ten dang nhap: ", sTenDangNhap);
        txtv_tendangnhap = (TextView) view.findViewById(R.id.txtv_tendangnhap);
        txtv_tendangnhap.setText(sTenDangNhap);

        // fragment
        fragmentManager = getSupportFragmentManager();
        // Khi mở lên chạy luôn ra trang chủ
        FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
        HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
        tranHienThiBanAn.replace(R.id.content,hienThiBanAnFragment);
        tranHienThiBanAn.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itTrangchu:
                xuLyTrangChu(item);
                break;
            case R.id.itThucđon:
                xuLyThucDon(item);
                break;
            case R.id.itNhanvien:
                xuLyNhanVien(item);
                break;
            case R.id.itThongke:
                xuLyThongKe(item);
                break;
            case R.id.itLogout:
                xuLyLogout(item);
                break;
        }
        return false;
    }

    private void xuLyLogout(MenuItem item) {
        SharedPreferences preferences =getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        finish();
        Intent intent = new Intent(this,
                DangNhapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void xuLyThongKe(MenuItem item) {
        FragmentTransaction tranHienThongKe = fragmentManager.beginTransaction();
        HienThiThongKeFragment hienThiThongKeFragment = new HienThiThongKeFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(DangNhapActivity.MA_NV,maNV);

        hienThiThongKeFragment.setArguments(bundle);
        tranHienThongKe.replace(R.id.content,hienThiThongKeFragment);
        tranHienThongKe.commit();

        item.setChecked(true);
        drawerLayout.closeDrawers();
    }

    private void xuLyNhanVien(MenuItem item) {
        FragmentTransaction tranHienNhanVien = fragmentManager.beginTransaction();
        HienThiNhanVienFragment hienThiNhanVienFragment = new HienThiNhanVienFragment();
        tranHienNhanVien.replace(R.id.content,hienThiNhanVienFragment);
        tranHienNhanVien.commit();

        // ẩn navigation khi click vào từng item
        item.setChecked(true);
        drawerLayout.closeDrawers();
    }

    //Xu ly thuc dơn
    private void xuLyThucDon(MenuItem item) {
        FragmentTransaction tranHienThucDon = fragmentManager.beginTransaction();
        HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
        tranHienThucDon.replace(R.id.content, hienThiThucDonFragment);
        tranHienThucDon.commit();

        item.setChecked(true);
        drawerLayout.closeDrawers();
    }

    private void xuLyTrangChu(MenuItem item) {
        FragmentTransaction tranHienThiBanAn = fragmentManager.beginTransaction();
        HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
        // Animation
        tranHienThiBanAn.replace(R.id.content,hienThiBanAnFragment);
        tranHienThiBanAn.commit();
        // ẩn navigation khi click vào từng item
        item.setChecked(true);
        drawerLayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        TrangChuActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}
