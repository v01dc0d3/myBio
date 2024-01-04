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

public class DialogEditLaporan extends Dialog {
    private CustomDialogListener listener;
    private Button btn_PickDate;
    private TextInputEditText tiet_SelectedDate;
    private TextInputEditText tiet_totalGrabFood;
    private TextInputEditText tiet_totalShopeeFood;
    private TextInputEditText tiet_totalGoFood;
    String idLaporan, tanggal, totalGrabFood, totalShopeeFood, totalGoFood;
    public DialogEditLaporan(@NonNull Context context, String idLaporan, String tanggal, String totalGrabFood, String totalShopeeFood, String totalGoFood) {
        super(context);
        this.idLaporan = idLaporan;
        this.tanggal = tanggal;
        this.totalGrabFood = totalGrabFood;
        this.totalShopeeFood = totalShopeeFood;
        this.totalGoFood = totalGoFood;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_laporan);

        Context mycontext = this.getContext();

        btn_PickDate = findViewById(R.id.btn_PickDate);
        tiet_SelectedDate = findViewById(R.id.tiet_SelectedDate);
        tiet_totalGrabFood = findViewById(R.id.tiet_totalGrabFood);
        tiet_totalShopeeFood = findViewById(R.id.tiet_totalShopeeFood);
        tiet_totalGoFood = findViewById(R.id.tiet_totalGoFood);

        tiet_SelectedDate.setText(this.tanggal);
        tiet_totalGrabFood.setText(this.totalGrabFood);
        tiet_totalShopeeFood.setText(this.totalShopeeFood);
        tiet_totalGoFood.setText(this.totalGoFood);

        btn_PickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        mycontext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                tiet_SelectedDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        MaterialButton mb_edit = findViewById(R.id.mb_edit);
        mb_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( TextUtils.isEmpty(tiet_SelectedDate.getText())) {
                    tiet_SelectedDate.setError("Pilih Tanggal Dahulu!");
                } else if ( TextUtils.isEmpty(tiet_totalGrabFood.getText())) {
                    tiet_totalGrabFood.setError("Input Total Pemasukan GrabFood!");
                } else if ( TextUtils.isEmpty(tiet_totalShopeeFood.getText())) {
                    tiet_totalShopeeFood.setError("Input Total Pemasukan ShopeeFood!");
                } else if ( TextUtils.isEmpty(tiet_totalGoFood.getText())) {
                    tiet_totalGoFood.setError("Input Total Pemasukan GoFood!");
                } else {
                    DatabaseHelper dbHelper = new DatabaseHelper(mycontext);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    Integer total = Integer.parseInt(tiet_totalGrabFood.getText().toString()) + Integer.parseInt(tiet_totalShopeeFood.getText().toString()) + Integer.parseInt(tiet_totalGoFood.getText().toString());

                    db.execSQL("UPDATE reports SET tanggal='"+ tiet_SelectedDate.getText().toString() +"', totalGrabFood='"+ tiet_totalGrabFood.getText().toString() +"', totalShopeeFood='"+ tiet_totalShopeeFood.getText().toString() +"', totalGofood='"+ tiet_totalGoFood.getText().toString() +"', total='"+ total.toString() +"' WHERE id_report='"+ idLaporan.toString() +"';");
                    db.close();

                    Toast.makeText(mycontext, "Laporan Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();

                    if (listener != null) {
                        Log.d("Dialog Tambah Listener Not Null", listener.toString());
                        listener.onLaporanChanged();
                    } else {
                        Log.d("Dialog Tambah Listener Null", "Listener Null");
                    }

                    dismiss();
                }
            }
        });

    }
    public void setOnDataChangedListener(CustomDialogListener listener) {
        this.listener = listener;
    }

}

