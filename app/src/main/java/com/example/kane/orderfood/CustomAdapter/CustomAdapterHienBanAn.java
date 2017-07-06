package com.example.kane.orderfood.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kane.orderfood.Activity.DangNhapActivity;
import com.example.kane.orderfood.Activity.ThanhToanActivity;
import com.example.kane.orderfood.Activity.TrangChuActivity;
import com.example.kane.orderfood.DataAdapterObject.BanAnDAO;
import com.example.kane.orderfood.DataAdapterObject.GoiMonDAO;
import com.example.kane.orderfood.Database.CreateDatabase;
import com.example.kane.orderfood.FragmentApp.HienThiThucDonFragment;
import com.example.kane.orderfood.Model.BanAn;
import com.example.kane.orderfood.Model.GoiMon;
import com.example.kane.orderfood.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Kane on 6/12/2017.
 */

public class CustomAdapterHienBanAn extends BaseAdapter implements View.OnClickListener{
    private Context context;
    private int resource;
    private ArrayList<BanAn> arrBanAn;
    ViewHolderBanAn viewHolderBanAn;
    BanAn banAn;
    BanAnDAO banAnDAO;
    GoiMon goiMon;
    GoiMonDAO goiMonDAO;
    FragmentManager fragmentManager;
    public static final String BUNDLE_MABAN = "maban";
    public static final String ADDSTACKHIENBANAN = "hienthibanan";
    int maBan;

    public CustomAdapterHienBanAn(Context context, int resource, ArrayList<BanAn> arrBanAn) {
        this.context = context;
        this.resource = resource;
        this.arrBanAn = arrBanAn;
        banAnDAO = new BanAnDAO(context);
        fragmentManager = ((TrangChuActivity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return arrBanAn.size();
    }

    @Override
    public Object getItem(int position) {
        return arrBanAn.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrBanAn.get(position).getMaBan();
    }

    public class ViewHolderBanAn{
        private TextView txtv_tenbanan;
        private ImageView imBanAn;
        private ImageButton imGoiMon, imThanhToan, imAnButton;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderBanAn = new ViewHolderBanAn();
            view = inflater.inflate(R.layout.custom_layout_hienbanan,parent,false);

            viewHolderBanAn.imBanAn = (ImageView) view.findViewById(R.id.imBanAn);
            viewHolderBanAn.imGoiMon = (ImageButton) view.findViewById(R.id.imGoiMon);
            viewHolderBanAn.imThanhToan= (ImageButton) view.findViewById(R.id.imThanhToan);
            viewHolderBanAn.imAnButton= (ImageButton) view.findViewById(R.id.imAnButton);
            viewHolderBanAn.txtv_tenbanan= (TextView) view.findViewById(R.id.txtv_tenbanan);

            view.setTag(viewHolderBanAn);
        }else {
            viewHolderBanAn = (ViewHolderBanAn) view.getTag();
        }
        // Kiem tra de hien an button
        if (arrBanAn.get(position).isDuocChon()){
            HienButton();
        }else {
            AnButton();
        }

        goiMonDAO = new GoiMonDAO(context);
        banAn = arrBanAn.get(position);

        // xet ảnh bàn ăn khi chuyển trạng thái
        String ktTinhTrang = banAnDAO.layTinhTrangBanAn(banAn.getMaBan());
        if (ktTinhTrang.equals("true")){
            viewHolderBanAn.imBanAn.setImageResource(R.drawable.banantrue);
        }else {
            viewHolderBanAn.imBanAn.setImageResource(R.drawable.banan);
        }


        viewHolderBanAn.txtv_tenbanan.setText(banAn.getTenBan());
        // Co dinh vi tri
        viewHolderBanAn.imBanAn.setTag(position);

        addEvents();
        // GoiMon
        return view;
    }

    private void HienButton(){
        viewHolderBanAn.imGoiMon.setVisibility(View.VISIBLE);
        viewHolderBanAn.imThanhToan.setVisibility(View.VISIBLE);
        viewHolderBanAn.imAnButton.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.anim_banan);
        viewHolderBanAn.imGoiMon.startAnimation(animation);
        viewHolderBanAn.imThanhToan.startAnimation(animation);
        viewHolderBanAn.imAnButton.startAnimation(animation);
    }

    private void AnButton(){
        viewHolderBanAn.imGoiMon.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imThanhToan.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imAnButton.setVisibility(View.INVISIBLE);

    }

    private void addEvents() {
        viewHolderBanAn.imBanAn.setOnClickListener(this);
        viewHolderBanAn.imGoiMon.setOnClickListener(this);
        viewHolderBanAn.imThanhToan.setOnClickListener(this);
        viewHolderBanAn.imAnButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // gettag: lay cai da luu san
        int id = v.getId();
        viewHolderBanAn = (ViewHolderBanAn) ((View)v.getParent()).getTag();

        switch (id){
            case R.id.imBanAn:
                xuLyClickBanAn(v);
                break;
            case R.id.imGoiMon:
                xuLyGoiMon(v);
                break;
            case R.id.imThanhToan:
                xuLyThanhToan();
                break;
            case R.id.imAnButton:
                viewHolderBanAn.imGoiMon.setVisibility(View.INVISIBLE);
                viewHolderBanAn.imThanhToan.setVisibility(View.INVISIBLE);
                viewHolderBanAn.imAnButton.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void xuLyThanhToan() {
        int vitriBan = (int) viewHolderBanAn.imBanAn.getTag();
        maBan = arrBanAn.get(vitriBan).getMaBan();
        Intent iThanhToan = new Intent(context, ThanhToanActivity.class);
        iThanhToan.putExtra(BUNDLE_MABAN,maBan);
//        Log.d("vitriBanTT ", maBan+"");
        context.startActivity(iThanhToan);
    }

    private void xuLyGoiMon(View v) {
        int vitriBan = (int) viewHolderBanAn.imBanAn.getTag();
        maBan = arrBanAn.get(vitriBan).getMaBan();
        String tinhTrang = banAnDAO.layTinhTrangBanAn(maBan);
        Intent iTrangChu = ((TrangChuActivity)context).getIntent();
        int maNV = iTrangChu.getIntExtra(DangNhapActivity.MA_NV,0);

        Log.d("maban + tinh trang",maBan + tinhTrang);
        // Nếu = false(chưa gọi món) thì cho phép gọi món
        if (tinhTrang.equals("false")){

            // Ngày gọi
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            format.setTimeZone(calendar.getTimeZone());
            String ngayGoiMon = format.format(calendar.getTime());
//            Log.d("date: ", ngayGoiMon);
            goiMon = new GoiMon();
            goiMon.setMaBanAn(maBan);
            goiMon.setMaNV(maNV);
            goiMon.setNgayGoi(ngayGoiMon);
            goiMon.setTinhTrang(tinhTrang);

            boolean kiemtra = goiMonDAO.themGoiMon(goiMon);
            if(kiemtra){
                banAnDAO.capNhatLaiTinhTrangBan(maBan,"true");
            }
        }

        FragmentTransaction tranThucDon = fragmentManager.beginTransaction();
        HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();

        Bundle bDuLieuBan = new Bundle();
        bDuLieuBan.putInt(BUNDLE_MABAN, maBan);
        Log.d("getMaBan: ",maBan+"");

        hienThiThucDonFragment.setArguments(bDuLieuBan);
        tranThucDon.replace(R.id.content,hienThiThucDonFragment).addToBackStack(ADDSTACKHIENBANAN);
        tranThucDon.commit();

    }

    private void xuLyClickBanAn(View v) {
        int vitri = (int) v.getTag();
        arrBanAn.get(vitri).setDuocChon(true);
        HienButton();
    }
}
