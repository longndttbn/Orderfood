package com.example.kane.orderfood.FragmentApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.kane.orderfood.Activity.SoLuongActivity;
import com.example.kane.orderfood.Activity.SuaBanAnActivity;
import com.example.kane.orderfood.Activity.SuaMonAnActivity;
import com.example.kane.orderfood.CustomAdapter.CustomAdapterHienBanAn;
import com.example.kane.orderfood.CustomAdapter.CustomAdapterHienMonAn;
import com.example.kane.orderfood.DataAdapterObject.MonAnDAO;
import com.example.kane.orderfood.Database.CreateDatabase;
import com.example.kane.orderfood.Model.MonAn;
import com.example.kane.orderfood.R;

import java.util.ArrayList;

/**
 * Created by Kane on 6/17/2017.
 */
// hien thi mon an
public class HienThiMonAnFragment extends Fragment {
    private GridView gvHienThiMonAn;
    ArrayList<MonAn> arrMonAn;
    CustomAdapterHienMonAn adapterHienMonAn;
    MonAnDAO monAnDAO;
    int iMaBan;
    int iMaLoai;
    int maMon;
    public static final int SUA_REQUESTCODE = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hienthithucdon, container, false);

        gvHienThiMonAn = (GridView) view.findViewById(R.id.gvHienThucDon);
        registerForContextMenu(gvHienThiMonAn);
        // Nhận data
        Bundle bundle = getArguments();
        if (bundle != null) {
            iMaLoai = bundle.getInt(HienThiThucDonFragment.BUNDLE_MALOAI);
            iMaBan = bundle.getInt(CustomAdapterHienBanAn.BUNDLE_MABAN);
            adapter(iMaLoai);
            gvHienThiMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // Kiểm tra xem có mã bàn không?
                    if (iMaBan != 0){
                        // Hiện thị popup số lượng
                        arrMonAn = monAnDAO.getTatCaMonAn(iMaLoai);
                        Intent iSoLuong = new Intent(getActivity(), SoLuongActivity.class);
                        iSoLuong.putExtra(CustomAdapterHienBanAn.BUNDLE_MABAN,iMaBan);
                        iSoLuong.putExtra(CreateDatabase.TB_MONAN_MAMONAN,arrMonAn.get(position).getMaMonAn());
//                        Log.d("vitri: ", arrMonAn.get(position).getMaMonAn()+"");
                        startActivity(iSoLuong);
                    }else {
                        // thêm mới mã bàn
                    }
                }
            });
        }

        // quay lai
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack(HienThiThucDonFragment.ADDBACKSTACK, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });


        return view;
    }

    private void adapter(int iMaLoai) {
        monAnDAO = new MonAnDAO(getActivity());
        arrMonAn = monAnDAO.getTatCaMonAn(iMaLoai);
        adapterHienMonAn = new CustomAdapterHienMonAn(getActivity(),R.layout.custom_layout_hienmonan,arrMonAn);
        gvHienThiMonAn.setAdapter(adapterHienMonAn);
        adapterHienMonAn.notifyDataSetChanged();
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
        maMon = arrMonAn.get(vitri).getMaMonAn();

        switch (id){
            case R.id.itSua:
                xuLySuaMonAn();
                break;
            case R.id.itXoa:
                boolean kiemTra = monAnDAO.XoaMonAnTheoMaMon(maMon);
                if (kiemTra){
                    adapter(iMaLoai);
                    Toast.makeText(getActivity(), getResources().getString(R.string.xoathanhcong), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.xoathatbai), Toast.LENGTH_SHORT).show();
                }
                break;

        }
        return super.onContextItemSelected(item);
    }

    private void xuLySuaMonAn() {
        Intent iSuaMonAn = new Intent(getActivity(), SuaMonAnActivity.class);
        iSuaMonAn.putExtra(CreateDatabase.TB_MONAN_MAMONAN,maMon);
        iSuaMonAn.putExtra(CreateDatabase.TB_MONAN_MALOAI,iMaLoai);
        startActivityForResult(iSuaMonAn,SUA_REQUESTCODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SUA_REQUESTCODE){
            if (resultCode == Activity.RESULT_OK){
                Intent i = data;
                boolean kiemTra = i.getBooleanExtra(SuaMonAnActivity.CAPNHATMONAN,false);
                if (kiemTra){
                    adapter(iMaLoai);
                    Toast.makeText(getActivity(), getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.suathatbai), Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

