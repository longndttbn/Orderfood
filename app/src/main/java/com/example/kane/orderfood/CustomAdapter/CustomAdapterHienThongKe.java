package com.example.kane.orderfood.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kane.orderfood.Model.ThongKe;
import com.example.kane.orderfood.R;

import java.util.ArrayList;

/**
 * Created by Kane on 7/1/2017.
 */

public class CustomAdapterHienThongKe extends BaseAdapter {
    private Context context;
    private int resource;
    private ArrayList<ThongKe> arrThongKe;
    ViewHolderThongKe viewHolderThongKe;

    public CustomAdapterHienThongKe(Context context, int resource, ArrayList<ThongKe> arrThongKe) {
        this.context = context;
        this.resource = resource;
        this.arrThongKe = arrThongKe;
    }

    @Override
    public int getCount() {
        return arrThongKe.size();
    }

    @Override
    public Object getItem(int position) {
        return arrThongKe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrThongKe.get(position).getMaThongKe();
    }

    class ViewHolderThongKe {
        TextView txtv_tenNhanVien, txtv_tongTienThanhToan, txtv_ngayThanhToan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            viewHolderThongKe = new ViewHolderThongKe();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);

            viewHolderThongKe.txtv_tenNhanVien = (TextView) view.findViewById(R.id.txtv_tenNhanvien);
            viewHolderThongKe.txtv_tongTienThanhToan = (TextView) view.findViewById(R.id.txtv_tongtienthanhtoan);
            viewHolderThongKe.txtv_ngayThanhToan = (TextView) view.findViewById(R.id.txtv_ngaythanhtoan);

            view.setTag(viewHolderThongKe);
        }else {
            viewHolderThongKe = (ViewHolderThongKe) view.getTag();
        }
        ThongKe thongKe = arrThongKe.get(position);
        viewHolderThongKe.txtv_tenNhanVien.setText(thongKe.getTenNhanvien());
        viewHolderThongKe.txtv_tongTienThanhToan.setText(String.valueOf(thongKe.getTongTien()));
        viewHolderThongKe.txtv_ngayThanhToan.setText(thongKe.getNgayThanhToan());

        return view;
    }
}
