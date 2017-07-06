package com.example.kane.orderfood.DataAdapterObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kane.orderfood.Database.CreateDatabase;
import com.example.kane.orderfood.Model.Quyen;

import java.util.ArrayList;

/**
 * Created by Kane on 7/1/2017.
 */

public class QuyenDAO {
    SQLiteDatabase database;

    public QuyenDAO(Context context) {
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    // 1: Admin   2:Nhân viên

    public void themQuyen(String tenQuyen){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_QUYEN_TENQUYEN,tenQuyen);

        database.insert(CreateDatabase.TB_QUYEN,null,contentValues);
    }

    public ArrayList<Quyen> LayQuyen(){
        ArrayList<Quyen> arrQuyen = new ArrayList<Quyen>();
        String truyvan = "SELECT * FROM "+CreateDatabase.TB_QUYEN;

        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Quyen quyen = new Quyen();
            quyen.setMaQuyen(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_QUYEN_MAQUYEN)));
            quyen.setTenQuyen(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_QUYEN_TENQUYEN)));

            arrQuyen.add(quyen);

            cursor.moveToNext();
        }
        return arrQuyen;
    }
}
