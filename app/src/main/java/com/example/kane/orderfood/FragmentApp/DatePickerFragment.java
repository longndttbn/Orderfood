package com.example.kane.orderfood.FragmentApp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.kane.orderfood.R;

import java.util.Calendar;

/**
 * Created by Kane on 6/11/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Lấy ngày tháng năm
        Calendar calendar = Calendar.getInstance();
        int iYear = calendar.get(Calendar.YEAR);
        int iMonth = calendar.get(Calendar.MONTH);
        int iDay = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this,iDay,iMonth,iYear);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        EditText edt_ngaysinhDK = (EditText) getActivity().findViewById(R.id.edt_ngaysinhDK);
        edt_ngaysinhDK.setText(dayOfMonth +"/"+(month+1)+"/"+year);
    }
}
