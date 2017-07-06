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
import android.view.KeyEvent;
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
import com.example.kane.orderfood.Activity.SuaBanAnActivity;
import com.example.kane.orderfood.Activity.ThemBanAnActivity;
import com.example.kane.orderfood.Activity.TrangChuActivity;
import com.example.kane.orderfood.CustomAdapter.CustomAdapterHienBanAn;
import com.example.kane.orderfood.DataAdapterObject.BanAnDAO;
import com.example.kane.orderfood.Database.CreateDatabase;
import com.example.kane.orderfood.Model.BanAn;
import com.example.kane.orderfood.R;

import java.util.ArrayList;

/**
 * Created by Kane on 6/12/2017.
 */

public class HienThiBanAnFragment extends Fragment {

    public static final int REQUESTCODE = 1;
    public static final int SUA_REQUEST_CODE = 2;
    private GridView gvHienBanAn;
    ArrayList<BanAn> arrBanAn;
    CustomAdapterHienBanAn adapterHienBanAn;
    BanAnDAO banAnDAO;
    int maQuyen =0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hienthibanan, container, false);

        // Có menu
        setHasOptionsMenu(true);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.trangchu);
        // Lấy mã quyền được save trong sharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(DangNhapActivity.FILE_NAME_SHARED, Context.MODE_PRIVATE);
        maQuyen = sharedPreferences.getInt(DangNhapActivity.MAQUYEN,0);

        addControls(view);
        return view;
    }

    private void addControls(View view) {
        gvHienBanAn = (GridView) view.findViewById(R.id.gvHienBanAn);
        banAnDAO = new BanAnDAO(getActivity());
        if (maQuyen == 1){
            registerForContextMenu(gvHienBanAn);
        }
        HienBanAnAdapter();
    }

    private void HienBanAnAdapter() {
        arrBanAn = banAnDAO.hienTatCaBanAn();
        adapterHienBanAn = new CustomAdapterHienBanAn(getActivity(),R.layout.custom_layout_hienbanan,arrBanAn);
        gvHienBanAn.setAdapter(adapterHienBanAn);
        adapterHienBanAn.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        // Lay mã bàn
        int maBan = arrBanAn.get(vitri).getMaBan();
        switch (id){
            case R.id.itSua:
                Intent iSuaBanAn = new Intent(getActivity(), SuaBanAnActivity.class);
                iSuaBanAn.putExtra(CreateDatabase.TB_BANAN_MABAN,maBan);
                startActivityForResult(iSuaBanAn,SUA_REQUEST_CODE);
                break;
            case R.id.itXoa:
                banAnDAO.XoaBanAnTheoMaBan(maBan);
                HienBanAnAdapter();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Mã quyền = 1: Admin
        if (maQuyen == 1){
            MenuItem itemThemBanAn = menu.add(1, R.id.itThemBanAn, 1, R.string.thembanan);
            itemThemBanAn.setIcon(R.drawable.thembanan);
            itemThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itThemBanAn:
                xuLyThemBanAn(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void xuLyThemBanAn(MenuItem item) {
        Intent iThemBanAn = new Intent(getActivity(), ThemBanAnActivity.class);
        startActivityForResult(iThemBanAn, REQUESTCODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE) {
            if (resultCode == Activity.RESULT_OK) {
                Intent iTenBanAn = data;
                boolean kiemtra = iTenBanAn.getBooleanExtra(ThemBanAnActivity.TEN_BAN_AN,false);
                if (kiemtra) {
                    HienBanAnAdapter();
                    Toast.makeText(getActivity(), getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
                }
            }
        }else if (requestCode == SUA_REQUEST_CODE){
            Intent iTenBan = data;
            boolean kiemTraCapNhatTenBan = iTenBan.getBooleanExtra(SuaBanAnActivity.CAPNHATTENBAN,false);
            if (kiemTraCapNhatTenBan) {
                HienBanAnAdapter();
                Toast.makeText(getActivity(), getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), getResources().getString(R.string.suathatbai), Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
