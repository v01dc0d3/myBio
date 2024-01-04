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

public class DialogTambahLaporan extends Dialog {
    private CustomDialogListener listener;

    private Button pickDateBtn;
    private TextView selectedDateTV;
    String title;
    public DialogTambahLaporan(@NonNull Context context, String title) {
        super(context);
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tambah_laporan);

        Context mycontext = this.getContext();

        // on below line we are initializing our variables.
        pickDateBtn = findViewById(R.id.idBtnPickDate);
        selectedDateTV = findViewById(R.id.idTVSelectedDate);

        // on below line we are adding click listener for our pick date button
        pickDateBtn.setOnClickListener(new View.OnClickListener() {
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
                                selectedDateTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        MaterialTextView mtv_title = findViewById(R.id.mtv_title);
        MaterialButton mb_tambah = findViewById(R.id.mb_tambah);
        mb_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText idTVSelectedDate = findViewById(R.id.idTVSelectedDate);
                TextInputEditText totalGrabFood = findViewById(R.id.totalGrabFood);
                TextInputEditText totalShopeeFood = findViewById(R.id.totalShopeeFood);
                TextInputEditText totalGoFood = findViewById(R.id.totalGoFood);

                if ( TextUtils.isEmpty(idTVSelectedDate.getText())) {
                    idTVSelectedDate.setError("Pilih Tanggal Dahulu!");
                } else if ( TextUtils.isEmpty(totalGrabFood.getText())) {
                    totalGrabFood.setError("Input Total Pemasukan GrabFood!");
                } else if ( TextUtils.isEmpty(totalShopeeFood.getText())) {
                    totalShopeeFood.setError("Input Total Pemasukan ShopeeFood!");
                } else if ( TextUtils.isEmpty(totalGoFood.getText())) {
                    totalGoFood.setError("Input Total Pemasukan GoFood!");
                } else {
                    DatabaseHelper dbHelper = new DatabaseHelper(mycontext);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    Integer total = Integer.parseInt(totalGrabFood.getText().toString()) + Integer.parseInt(totalShopeeFood.getText().toString()) + Integer.parseInt(totalGoFood.getText().toString());

                    db.execSQL("INSERT INTO reports VALUES (NULL, '"+ idTVSelectedDate.getText().toString() +"', '"+ totalGrabFood.getText().toString() +"', '"+ totalShopeeFood.getText().toString() +"', '"+ totalGoFood.getText().toString() +"', '"+ total.toString() +"');");
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
