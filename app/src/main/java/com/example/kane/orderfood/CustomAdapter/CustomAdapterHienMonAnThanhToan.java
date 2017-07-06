package com.example.kane.orderfood.CustomAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kane.orderfood.Model.ThanhToan;
import com.example.kane.orderfood.R;

import java.util.ArrayList;

/**
 * Created by Kane on 6/28/2017.
 */

public class CustomAdapterHienMonAnThanhToan extends BaseAdapter {
    private Context context;
    private int resource;
    private ArrayList<ThanhToan> arrThanhToan;
    ViewHolderThanhToan viewHolderThanhToan;

    public CustomAdapterHienMonAnThanhToan(Context context, int resource, ArrayList<ThanhToan> arrThanhToan) {
        this.context = context;
        this.resource = resource;
        this.arrThanhToan = arrThanhToan;
    }

    @Override
    public int getCount() {
        return arrThanhToan.size();
    }

    @Override
    public Object getItem(int position) {
        return arrThanhToan.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolderThanhToan {
        TextView txtv_tenMonThanhToan, txtv_soLuongThanhToan, txtv_giaTienThanhToan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            // tao moi
            viewHolderThanhToan = new ViewHolderThanhToan();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);

            viewHolderThanhToan.txtv_tenMonThanhToan = (TextView) view.findViewById(R.id.txtv_tenMonThanhToan);
            viewHolderThanhToan.txtv_soLuongThanhToan = (TextView) view.findViewById(R.id.txtv_soLuongThanhToan);
            viewHolderThanhToan.txtv_giaTienThanhToan = (TextView) view.findViewById(R.id.txtv_giaTienThanhToan);

            view.setTag(viewHolderThanhToan);
        }else {
            viewHolderThanhToan = (ViewHolderThanhToan) view.getTag();
        }

        ThanhToan thanhToan = arrThanhToan.get(position);
        Log.d("thanhtoanvitri: ",thanhToan+"");
        viewHolderThanhToan.txtv_tenMonThanhToan.setText(thanhToan.getTenMonAn());
        viewHolderThanhToan.txtv_soLuongThanhToan.setText(String.valueOf(thanhToan.getSoLuong()));
        viewHolderThanhToan.txtv_giaTienThanhToan.setText(String.valueOf(thanhToan.getGiaTien()));

        return view;
    }
}
