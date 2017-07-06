package com.example.kane.orderfood.FragmentApp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kane.orderfood.Activity.DangNhapActivity;
import com.example.kane.orderfood.Activity.ThanhToanActivity;
import com.example.kane.orderfood.Activity.TrangChuActivity;
import com.example.kane.orderfood.CustomAdapter.CustomAdapterHienThongKe;
import com.example.kane.orderfood.DataAdapterObject.NhanVienDAO;
import com.example.kane.orderfood.DataAdapterObject.ThongKeDAO;
import com.example.kane.orderfood.Model.ThanhToan;
import com.example.kane.orderfood.Model.ThongKe;
import com.example.kane.orderfood.R;

import java.util.ArrayList;

/**
 * Created by Kane on 7/1/2017.
 */

public class HienThiThongKeFragment extends Fragment {
    private ListView lv_dsThongKe;
    ArrayList<ThongKe> arrThongKe;
    CustomAdapterHienThongKe adapterHienThongKe;
    ThongKeDAO thongKeDAO;
    NhanVienDAO nhanVienDAO;
    int maQuyen;
    int maThongKe;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hienthithongke, container, false);

        ((TrangChuActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.thongke));
        nhanVienDAO = new NhanVienDAO(getActivity());
        thongKeDAO = new ThongKeDAO(getActivity());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(DangNhapActivity.FILE_NAME_SHARED, Context.MODE_PRIVATE);
        maQuyen = sharedPreferences.getInt(DangNhapActivity.MAQUYEN, 0);

        lv_dsThongKe = (ListView) view.findViewById(R.id.lv_dsthongke);
        if (maQuyen == 1) {
            registerForContextMenu(lv_dsThongKe);
        }
        dsthongke();
        return view;
    }

    private void dsthongke() {
        arrThongKe = thongKeDAO.LayToanBoThongKe();
        adapterHienThongKe = new CustomAdapterHienThongKe(getActivity(), R.layout.custom_layout_hienthongke, arrThongKe);
        lv_dsThongKe.setAdapter(adapterHienThongKe);
        adapterHienThongKe.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_thongke_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        maThongKe = arrThongKe.get(vitri).getMaThongKe();

        switch (id){
            case R.id.itXoa:
                boolean kiemtra = thongKeDAO.XoaThongKeTheoMaThongKe(maThongKe);
                if (kiemtra){
                    dsthongke();
                    Toast.makeText(getActivity(), getResources().getString(R.string.xoathanhcong), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.xoathatbai), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }
}
