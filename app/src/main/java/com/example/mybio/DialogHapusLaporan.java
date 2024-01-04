package com.example.mybio;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class DialogHapusLaporan extends Dialog {
    private CustomDialogListener listener;
    String id_report;
    public DialogHapusLaporan(@NonNull Context context, String id_report) {
        super(context);
        this.id_report = id_report;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_hapus_laporan);

        Context mycontext = this.getContext();

        MaterialTextView mtv_title = findViewById(R.id.mtv_title);
        MaterialButton mb_hapus = findViewById(R.id.mb_hapus);
        MaterialButton mb_cancel = findViewById(R.id.mb_cancel);
        mb_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper dbHelper = new DatabaseHelper(mycontext);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                db.execSQL("DELETE FROM reports WHERE id_report='"+ id_report +"';");
                db.close();

                Toast.makeText(mycontext, "Laporan Berhasil Dihapus", Toast.LENGTH_SHORT).show();

                if (listener != null) {
                    Log.d("Dialog Hapus Listener Not Null", listener.toString());
                    listener.onLaporanChanged();
                } else {
                    Log.d("Dialog Hapus Listener Null", listener.toString());
                }

                dismiss();
            }
        });

        mb_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
    public void setOnDataChangedListener(CustomDialogListener listener) {
        this.listener = listener;
    }
}

