package com.example.kane.orderfood.CustomAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kane.orderfood.Model.MonAn;
import com.example.kane.orderfood.R;

import java.util.ArrayList;

/**
 * Created by Kane on 6/17/2017.
 */

public class CustomAdapterHienMonAn extends BaseAdapter {
    private Context context;
    private int resource;
    private ArrayList<MonAn> arrMonAn;
    ViewHolderHienMonAn viewHolderHienMonAn;
    public static final String GIATIEN = "Giá : ";

    public CustomAdapterHienMonAn(Context context, int resource, ArrayList<MonAn> arrMonAn) {
        this.context = context;
        this.resource = resource;
        this.arrMonAn = arrMonAn;
    }

    @Override
    public int getCount() {
        return arrMonAn.size();
    }

    @Override
    public Object getItem(int position) {
        return arrMonAn.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrMonAn.get(position).getMaMonAn();
    }

    private class ViewHolderHienMonAn {
        ImageView imv_monan;
        TextView txtv_tenmonan;
        TextView txtv_giatien;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
            viewHolderHienMonAn = new ViewHolderHienMonAn();

            viewHolderHienMonAn.imv_monan = (ImageView) view.findViewById(R.id.imv_monan);
            viewHolderHienMonAn.txtv_tenmonan = (TextView) view.findViewById(R.id.txtv_tenmonan);
            viewHolderHienMonAn.txtv_giatien = (TextView) view.findViewById(R.id.txtv_giatien);

            view.setTag(viewHolderHienMonAn);
        } else {
            viewHolderHienMonAn = (ViewHolderHienMonAn) view.getTag();
        }

        MonAn monAn = arrMonAn.get(position);
        String hinhAnh = monAn.getHinhAnh().toString();

        Uri uri = Uri.parse(hinhAnh);
        viewHolderHienMonAn.imv_monan.setImageURI(uri);
        viewHolderHienMonAn.txtv_tenmonan.setText(monAn.getTenMonAn());
        viewHolderHienMonAn.txtv_giatien.setText(GIATIEN + monAn.getGiaTien());
        return view;
    }
}
