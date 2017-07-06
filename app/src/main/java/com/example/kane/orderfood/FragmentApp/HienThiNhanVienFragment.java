package com.example.kane.orderfood.FragmentApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kane.orderfood.Activity.DangKyActivity;
import com.example.kane.orderfood.Activity.DangNhapActivity;
import com.example.kane.orderfood.Activity.SuaNhanVienActivity;
import com.example.kane.orderfood.Activity.TrangChuActivity;
import com.example.kane.orderfood.CustomAdapter.CustomAdapterHienNhanVien;
import com.example.kane.orderfood.DataAdapterObject.NhanVienDAO;
import com.example.kane.orderfood.Database.CreateDatabase;
import com.example.kane.orderfood.Model.NhanVien;
import com.example.kane.orderfood.R;

import java.util.ArrayList;

/**
 * Created by Kane on 6/30/2017.
 */

public class HienThiNhanVienFragment extends Fragment {
    private ListView lv_dsnhanvien;
    ArrayList<NhanVien> arrNhanVien;
    CustomAdapterHienNhanVien customAdapterHienNhanVien;
    NhanVienDAO nhanVienDAO;
    public static final int THEM_REQUESTCODE_NHANVIEN = 1;
    public static final int SUA_REQUESTCODE_NHANVIEN = 2;
    int maQuyen = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hienthinhanvien, container, false);
        ((TrangChuActivity) getActivity()).getSupportActionBar().setTitle(R.string.nhanvien);
        // Them nhan vien
        setHasOptionsMenu(true);
        nhanVienDAO = new NhanVienDAO(getActivity());

        // Lấy mã quyền được save trong sharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(DangNhapActivity.FILE_NAME_SHARED, Context.MODE_PRIVATE);
        maQuyen = sharedPreferences.getInt(DangNhapActivity.MAQUYEN, 0);

        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {

    }

    private void addControls(View v) {
        lv_dsnhanvien = (ListView) v.findViewById(R.id.lv_dsnhanvien);
        if (maQuyen == 1) {
            registerForContextMenu(lv_dsnhanvien);
        }
        adapterHienNhanVien();
    }

    private void adapterHienNhanVien() {
        arrNhanVien = nhanVienDAO.LayTatCaNhanVien();
        customAdapterHienNhanVien = new CustomAdapterHienNhanVien(getActivity(), R.layout.custom_layout_hienthinhanvien, arrNhanVien);
        lv_dsnhanvien.setAdapter(customAdapterHienNhanVien);
        customAdapterHienNhanVien.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Lay vitri nhanvien
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
//        Log.d("ArrNhanVien ",arrNhanVien.size()+"");
        int maNV = arrNhanVien.get(vitri).getMaNV();
        Log.d("MaNV   ", maNV + "");
        switch (id) {
            case R.id.itSua:
                //Todo
                Intent iSuaNhanVien = new Intent(getActivity(), SuaNhanVienActivity.class);
                iSuaNhanVien.putExtra(CreateDatabase.TB_NHANVIEN_MANV, maNV);
                startActivityForResult(iSuaNhanVien, SUA_REQUESTCODE_NHANVIEN);
                break;
            case R.id.itXoa:
                boolean kiemTraXoa = nhanVienDAO.XoaNhanVienTheoMaNV(maNV);
                if (kiemTraXoa) {
                    adapterHienNhanVien();
                    Toast.makeText(getActivity(), getResources().getString(R.string.xoathanhcong), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.xoathatbai), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (maQuyen == 1) {
            getActivity().getMenuInflater().inflate(R.menu.item_nhanvien, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itThemNhanVien:
                xuLyThemNhanVien();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void xuLyThemNhanVien() {
        Intent iThemNhanVien = new Intent(getActivity(), DangKyActivity.class);
        startActivityForResult(iThemNhanVien, THEM_REQUESTCODE_NHANVIEN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == THEM_REQUESTCODE_NHANVIEN) {
            if (resultCode == Activity.RESULT_OK) {
                adapterHienNhanVien();
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SUA_REQUESTCODE_NHANVIEN) {
            if (resultCode == Activity.RESULT_OK) {
                Intent i = data;
                boolean kiemtra = i.getBooleanExtra("capnhat", false);
                if (kiemtra) {
                    adapterHienNhanVien();
                    Toast.makeText(getActivity(), getResources().getString(R.string.capnhatthanhcong), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.capnhatthatbai), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
