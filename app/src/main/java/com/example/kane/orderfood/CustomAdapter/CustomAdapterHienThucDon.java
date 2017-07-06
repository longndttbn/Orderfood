package com.example.kane.orderfood.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kane.orderfood.Model.LoaiMonAn;
import com.example.kane.orderfood.R;

import java.util.ArrayList;

/**
 * Created by Kane on 6/15/2017.
 */

// Custom cho spinner

public class CustomAdapterHienThucDon extends BaseAdapter {
    private Context context;
    private int resource;
    private ArrayList<LoaiMonAn> arrLoaiMonAn;
    ViewHolderHienThucDon viewHolderHienThucDon;

    public CustomAdapterHienThucDon(Context context, int resource, ArrayList<LoaiMonAn> arrLoaiMonAn) {
        this.context = context;
        this.resource = resource;
        this.arrLoaiMonAn = arrLoaiMonAn;
    }

    @Override
    public int getCount() {
        return arrLoaiMonAn.size();
    }

    @Override
    public Object getItem(int position) {
        return arrLoaiMonAn.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrLoaiMonAn.get(position).getMaLoai();
    }

    private class ViewHolderHienThucDon{
        TextView txtv_tenloaithucdon;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderHienThucDon = new ViewHolderHienThucDon();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout_hienloaithucdon,parent,false);

            // rỗng sẽ tạo mới
            viewHolderHienThucDon.txtv_tenloaithucdon = (TextView) view.findViewById(R.id.txtv_tenloaithucdon);
            view.setTag(viewHolderHienThucDon);
        }else {
            viewHolderHienThucDon = (ViewHolderHienThucDon) view.getTag();
        }

        LoaiMonAn loaiMonAn = arrLoaiMonAn.get(position);
        viewHolderHienThucDon.txtv_tenloaithucdon.setText(loaiMonAn.getTenLoai());
        // Để lấy mã và tên cùng lúc
        viewHolderHienThucDon.txtv_tenloaithucdon.setTag(loaiMonAn.getMaLoai());

        return view;
    }
}
