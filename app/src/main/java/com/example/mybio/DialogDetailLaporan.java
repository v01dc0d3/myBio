package com.example.mybio;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.Calendar;

public class DialogDetailLaporan extends Dialog {
    private TextInputEditText tiet_SelectedDate, tiet_totalGrabFood, tiet_totalShopeeFood, tiet_totalGoFood, tiet_total;
    String idLaporan, tanggal, totalGrabFood, totalShopeeFood, totalGoFood, total;
    public DialogDetailLaporan(@NonNull Context context, String idLaporan, String tanggal, String totalGrabFood, String totalShopeeFood, String totalGoFood, String total) {
        super(context);
        this.idLaporan = idLaporan;
        this.tanggal = tanggal;
        this.totalGrabFood = totalGrabFood;
        this.totalShopeeFood = totalShopeeFood;
        this.totalGoFood = totalGoFood;
        this.total = total;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_detail_laporan);

        tiet_SelectedDate = findViewById(R.id.tiet_SelectedDate);
        tiet_totalGrabFood = findViewById(R.id.tiet_totalGrabFood);
        tiet_totalShopeeFood = findViewById(R.id.tiet_totalShopeeFood);
        tiet_totalGoFood = findViewById(R.id.tiet_totalGoFood);
        tiet_total = findViewById(R.id.tiet_total);

        tiet_SelectedDate.setEnabled(false);
        tiet_totalGrabFood.setEnabled(false);
        tiet_totalShopeeFood.setEnabled(false);
        tiet_totalGoFood.setEnabled(false);
        tiet_total.setEnabled(false);

        tiet_SelectedDate.setText(this.tanggal);
        tiet_totalGrabFood.setText(this.totalGrabFood);
        tiet_totalShopeeFood.setText(this.totalShopeeFood);
        tiet_totalGoFood.setText(this.totalGoFood);
        tiet_total.setText(this.total);
    }
}


