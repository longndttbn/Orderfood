package com.example.kane.orderfood.FragmentApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.kane.orderfood.Activity.DangNhapActivity;
import com.example.kane.orderfood.Activity.SuaLoaiThucDonActivity;
import com.example.kane.orderfood.Activity.ThemThucDonActivity;
import com.example.kane.orderfood.Activity.TrangChuActivity;
import com.example.kane.orderfood.CustomAdapter.CustomAdapterHienBanAn;
import com.example.kane.orderfood.CustomAdapter.CustomAdapterHienLoaiMonAn;
import com.example.kane.orderfood.DataAdapterObject.LoaiMonAnDAO;
import com.example.kane.orderfood.DataAdapterObject.MonAnDAO;
import com.example.kane.orderfood.Database.CreateDatabase;
import com.example.kane.orderfood.Model.LoaiMonAn;
import com.example.kane.orderfood.R;

import java.util.ArrayList;

import static com.example.kane.orderfood.CustomAdapter.CustomAdapterHienBanAn.BUNDLE_MABAN;

/**
 * Created by Kane on 6/14/2017.
 */

public class HienThiThucDonFragment extends Fragment implements AdapterView.OnItemClickListener {
    private GridView gvHienThucDon;
    ArrayList<LoaiMonAn> arrLoaiMonAn;
    CustomAdapterHienLoaiMonAn adapterHienLoaiMonAn;
    LoaiMonAnDAO loaiMonAnDAO;
    MonAnDAO monAnDAO;
    public static final int REQUEST_CODE = 1;
    public static final int SUA_REQUEST_CODE = 2;
    public static final String BUNDLE_MALOAI = "maloai";
    public static final String ADDBACKSTACK = "Hienthiloai";
    FragmentManager manager;
    int maBan;
    int maQuyen = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hienthithucdon, container, false);
        setHasOptionsMenu(true);
        // Add title cho từng fragment
        ((TrangChuActivity) getActivity()).getSupportActionBar().setTitle(R.string.thucdon);
        gvHienThucDon = (GridView) view.findViewById(R.id.gvHienThucDon);
        manager = getActivity().getSupportFragmentManager();
        loaiMonAnDAO = new LoaiMonAnDAO(getActivity());
        monAnDAO = new MonAnDAO(getActivity());

        // Lấy mã quyền được save trong sharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(DangNhapActivity.FILE_NAME_SHARED, Context.MODE_PRIVATE);
        maQuyen = sharedPreferences.getInt(DangNhapActivity.MAQUYEN, 0);
        if (maQuyen == 1) {
            registerForContextMenu(gvHienThucDon);
        }
        adapter();
        // Nhận mã bàn
        Bundle bDuLieuBan = getArguments();
        if (bDuLieuBan != null) {
            maBan = bDuLieuBan.getInt(CustomAdapterHienBanAn.BUNDLE_MABAN);
        }
        addEvents();

        return view;
    }

    private void addEvents() {
        gvHienThucDon.setOnItemClickListener(this);
    }

    private void adapter() {
        arrLoaiMonAn = loaiMonAnDAO.getTatCaLoaiThucDon();
        adapterHienLoaiMonAn = new CustomAdapterHienLoaiMonAn(getActivity(), R.layout.custom_layout_hienloaimonan, arrLoaiMonAn);
        gvHienThucDon.setAdapter(adapterHienLoaiMonAn);
        adapterHienLoaiMonAn.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int maLoai = arrLoaiMonAn.get(vitri).getMaLoai();

        switch (id) {
            case R.id.itSua:
                Intent iSuaLoaiThucDon = new Intent(getActivity(), SuaLoaiThucDonActivity.class);
                iSuaLoaiThucDon.putExtra(CreateDatabase.TB_LOAIMONAN_MALOAI, maLoai);
                startActivityForResult(iSuaLoaiThucDon, SUA_REQUEST_CODE);

                break;
            case R.id.itXoa:
                boolean kiemTra = loaiMonAnDAO.XoaLoaiMonAnTheoMaLoai(maLoai);
                boolean kiemTraMonAn = monAnDAO.XoaMonAnTheoMaLoai(maLoai);
                if (kiemTra && kiemTraMonAn) {
                    adapter();
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
            getActivity().getMenuInflater().inflate(R.menu.item_monan, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itThemMonAn:
                xuLyThemThucDon(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void xuLyThemThucDon(MenuItem item) {
        Intent iThemThucDon = new Intent(getActivity(), ThemThucDonActivity.class);
        startActivityForResult(iThemThucDon, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra(ThemThucDonActivity.MONAN, false);
                if (kiemtra) {
                    adapter();
                    Toast.makeText(getActivity(), getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == SUA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra(SuaLoaiThucDonActivity.CAPNHATLOAI, false);
                if (kiemtra) {
                    adapter();
                    Toast.makeText(getActivity(), getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.suathatbai), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int maLoai = arrLoaiMonAn.get(position).getMaLoai();
        HienThiMonAnFragment hienThiMonAnFragment = new HienThiMonAnFragment();

        // Truyen du lieu
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_MALOAI, maLoai);
        bundle.putInt(BUNDLE_MABAN, maBan);
        hienThiMonAnFragment.setArguments(bundle);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content, hienThiMonAnFragment).addToBackStack(ADDBACKSTACK); // AddToBackStack: quay trở lại màn hình trc
        transaction.commit();
    }
}
