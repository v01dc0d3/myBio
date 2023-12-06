package com.example.mybio;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class CustomDialog extends Dialog {

    String title;
    public CustomDialog(@NonNull Context context, String title) {
        super(context);
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modal_layout); // Mengatur layout modal

        MaterialTextView mtv_title = findViewById(R.id.mtv_title);

        if(this.title == "Tambah Laporan") {
            mtv_title.setText(this.title);
        } else {
            mtv_title.setText(this.title);
            TextInputEditText tie_total = findViewById(R.id.tie_total);
            tie_total.setText("Rp. 2,350,000");
            TextInputEditText tie_tanggal = findViewById(R.id.tie_tanggal);
            tie_tanggal.setText("Januari 2023");
            TextInputEditText tie_vendor = findViewById(R.id.tie_vendor);
            tie_vendor.setText("Shopee Food");
        }


        MaterialButton mb_tambah = findViewById(R.id.mb_tambah);
        mb_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

}

