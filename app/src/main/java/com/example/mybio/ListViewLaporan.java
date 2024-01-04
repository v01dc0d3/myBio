package com.example.mybio;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListViewLaporan extends ArrayAdapter<String> {
    private List<String> idlaporan = new ArrayList<>();
    private List<String> tanggal = new ArrayList<>();
    private List<String> totalGrabFood = new ArrayList<>();
    private List<String> totalShopeeFood = new ArrayList<>();
    private List<String> totalGofood = new ArrayList<>();
    private List<String> total = new ArrayList<>();
    private Context context;
    private CustomDialogListener cdlContext;

    public ListViewLaporan(List<String> id_laporan, List<String> tanggal, List<String> totalGrabFood, List<String> totalShopeeFood, List<String> totalGofood, List<String> total, Context context, CustomDialogListener cdlContext) {
        super(context, R.layout.item_laporan, tanggal);
        this.context = context;
        this.idlaporan = id_laporan;
        this.tanggal = tanggal;
        this.totalGrabFood = totalGrabFood;
        this.totalShopeeFood = totalShopeeFood;
        this.totalGofood = totalGofood;
        this.total = total;
        this.cdlContext = cdlContext;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View row = inflater.inflate(R.layout.item_laporan, parent, false);

        TextView tv_total = row.findViewById(R.id.total);
        tv_total.setText("Rp" + total.get(position));

        TextView tv_tanggal = row.findViewById(R.id.tanggal);
        tv_tanggal.setText(tanggal.get(position));

        ImageButton btn_detail = row.findViewById(R.id.ib_detail);
        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDetailLaporan dialogDetailLaporan = new DialogDetailLaporan(context, idlaporan.get(position), tanggal.get(position), totalGrabFood.get(position), totalShopeeFood.get(position), totalGofood.get(position), total.get(position));
                dialogDetailLaporan.show();
            }
        });

        ImageButton btn_edit = row.findViewById(R.id.ib_edit);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogEditLaporan dialogEditLaporan = new DialogEditLaporan(context, idlaporan.get(position), tanggal.get(position), totalGrabFood.get(position), totalShopeeFood.get(position), totalGofood.get(position));
                dialogEditLaporan.setOnDataChangedListener(cdlContext);
                dialogEditLaporan.show();
            }
        });

        ImageButton btn_delete = row.findViewById(R.id.ib_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHapusLaporan dialogHapusLaporan = new DialogHapusLaporan(context, idlaporan.get(position));
                dialogHapusLaporan.setOnDataChangedListener(cdlContext);
                dialogHapusLaporan.show();
            }
        });

        return row;
    }

}
