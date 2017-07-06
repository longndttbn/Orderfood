package com.example.kane.orderfood.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kane.orderfood.Model.NhanVien;
import com.example.kane.orderfood.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Kane on 6/30/2017.
 */

public class CustomAdapterHienNhanVien extends BaseAdapter {
    private Context context;
    private int resource;
    private ArrayList<NhanVien> arrNhanVien;
    ViewHolderNhanVien viewHolderNhanVien;

    public CustomAdapterHienNhanVien(Context context, int resource, ArrayList<NhanVien> arrNhanVien) {
        this.context = context;
        this.resource = resource;
        this.arrNhanVien = arrNhanVien;
    }

    @Override
    public int getCount() {
        return arrNhanVien.size();
    }

    @Override
    public Object getItem(int position) {
        return arrNhanVien.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrNhanVien.get(position).getMaNV();
    }

    class ViewHolderNhanVien {
        CircleImageView imv_nhanvien;
        TextView txtv_tennv, txtv_gioitinhnv, txtv_cmnd;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            viewHolderNhanVien = new ViewHolderNhanVien();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);

            viewHolderNhanVien.imv_nhanvien = (CircleImageView) view.findViewById(R.id.imv_nhanvien);
            viewHolderNhanVien.txtv_tennv = (TextView) view.findViewById(R.id.txtv_tennv);
            viewHolderNhanVien.txtv_gioitinhnv = (TextView) view.findViewById(R.id.txtv_gioitinhnv);
            viewHolderNhanVien.txtv_cmnd = (TextView) view.findViewById(R.id.txtv_cmnd);

            view.setTag(viewHolderNhanVien);
        } else {
            viewHolderNhanVien = (ViewHolderNhanVien) view.getTag();
        }

        NhanVien nv = arrNhanVien.get(position);

        String gioitinh = nv.getGioiTinh();
        if (gioitinh.equals("Nam")) {
            viewHolderNhanVien.imv_nhanvien.setImageResource(R.drawable.kitchen_staff);
        }else {
            viewHolderNhanVien.imv_nhanvien.setImageResource(R.drawable.chef);
        }
        viewHolderNhanVien.txtv_tennv.setText(context.getResources().getString(R.string.tennhanvien) + ": " + nv.getTenNV());

        viewHolderNhanVien.txtv_gioitinhnv.setText(context.getResources().getString(R.string.gioitinh) + ": " + nv.getGioiTinh());
        // Nhớ là kiểu số thì phải chuyển về String
        viewHolderNhanVien.txtv_cmnd.setText(context.getResources().getString(R.string.cmnd) + ": " + String.valueOf(nv.getCMND()));

        return view;
    }
}
