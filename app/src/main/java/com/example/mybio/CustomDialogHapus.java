package com.example.mybio;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;


import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class CustomDialogHapus extends Dialog {

    public CustomDialogHapus(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modal_hapus); // Mengatur layout modal

        MaterialButton mb_tidak_hapus = findViewById(R.id.mb_tidak_hapus);
        mb_tidak_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        MaterialButton mb_hapus = findViewById(R.id.mb_hapus);
        mb_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

}

