package com.example.kane.orderfood.CustomAdapter;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kane.orderfood.DataAdapterObject.LoaiMonAnDAO;
import com.example.kane.orderfood.Model.LoaiMonAn;
import com.example.kane.orderfood.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Kane on 6/16/2017.
 */

public class CustomAdapterHienLoaiMonAn extends BaseAdapter {
    private Activity activity;
    private Context context;
    private int resource;
    private ArrayList<LoaiMonAn> arrLoaiMonAn;
    LoaiMonAnDAO loaiMonAnDAO;
    ViewHolderHienThiLoaiMonAn viewHolderHienThiLoaiMonAn;

    public CustomAdapterHienLoaiMonAn(Context context, int resource, ArrayList<LoaiMonAn> arrLoaiMonAn) {
        this.context = context;
        this.resource = resource;
        this.arrLoaiMonAn = arrLoaiMonAn;
        loaiMonAnDAO = new LoaiMonAnDAO(context);
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

    private class ViewHolderHienThiLoaiMonAn {
        ImageView imv_loaimonan;
        TextView txtv_tenloaimonan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderHienThiLoaiMonAn = new ViewHolderHienThiLoaiMonAn();
            view = inflater.inflate(R.layout.custom_layout_hienloaimonan, parent, false);

            viewHolderHienThiLoaiMonAn.imv_loaimonan = (ImageView) view.findViewById(R.id.imv_loaimonan);
            viewHolderHienThiLoaiMonAn.txtv_tenloaimonan = (TextView) view.findViewById(R.id.txtv_tenloaimonan);

            view.setTag(viewHolderHienThiLoaiMonAn);
        } else {
            viewHolderHienThiLoaiMonAn = (ViewHolderHienThiLoaiMonAn) view.getTag();
        }

        LoaiMonAn loaiMonAn = arrLoaiMonAn.get(position);
        int maLoai = loaiMonAn.getMaLoai();
        Log.d("maloai", maLoai+"");
        String hinhAnh = loaiMonAnDAO.getHinhAnhLoaiMonAn(maLoai);
//        Log.d("pathloaimonan: ", hinhAnh);
        Uri uri = Uri.parse(hinhAnh);
        viewHolderHienThiLoaiMonAn.imv_loaimonan.setImageURI(uri);
        viewHolderHienThiLoaiMonAn.txtv_tenloaimonan.setText(loaiMonAn.getTenLoai());
        return view;
    }

}
