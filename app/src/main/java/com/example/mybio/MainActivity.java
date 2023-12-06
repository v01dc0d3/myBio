package com.example.mybio;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] bulan = {"2020", "2021", "2022", "2023"};

        Spinner dropdownSpinner = findViewById(R.id.spinner_bulan);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_bulan, bulan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownSpinner.setAdapter(adapter);

        // Menangani kejadian pada dropdown
        dropdownSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedValue = (String) parentView.getItemAtPosition(position);
                // Lakukan sesuatu dengan nilai yang dipilih
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle jika tidak ada yang dipilih
            }
        });

        LineChart lineChart = findViewById(R.id.lineChart);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setGridColor(Color.WHITE);

        String[] label_bulan = {"Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov"};

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(label_bulan));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setLabelRotationAngle(0); // Menonaktifkan rotasi label
//        xAxis.setGranularity(2f); // Atur jarak minimum antara label
        xAxis.setLabelCount(6); // Atur jumlah label yang ditampilkan
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setGridColor(Color.WHITE);

        ArrayList<Entry> dataEntries = new ArrayList<>();
        dataEntries.add(new Entry(1, 4700000));
        dataEntries.add(new Entry(2, 4850000));
        dataEntries.add(new Entry(3, 4855000));
        dataEntries.add(new Entry(4, 4655000));
        dataEntries.add(new Entry(5, 4855000));
        dataEntries.add(new Entry(6, 4855000));
        // Tambahkan lebih banyak data sesuai kebutuhan

        // Inisialisasi data dan dataset
        LineData lineData = new LineData();
        LineDataSet lineDataSet = new LineDataSet(dataEntries, "Label");
        lineDataSet.setLineWidth(2f); // Lebar garis
        lineData.addDataSet(lineDataSet);

        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        lineDataSet.setCubicIntensity(0.2f); // Atur intensitas kurva sesuai kebutuhan
        lineDataSet.setDrawCircles(true); // Nonaktifkan penanda titik
        lineChart.setHardwareAccelerationEnabled(true); // Aktifkan akselerasi perangkat keras

        // Atur format nilai sumbu X jika diperlukan
//        lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                // Mengubah nilai float ke string sesuai dengan kebutuhan Anda
////                return "Nilai " + value;
//                return Float.toString(value);
//            }
//        });

        // Atur data ke grafik
        lineChart.setData(lineData);

        // Atur deskripsi grafik (opsional)
        Description description = new Description();
        description.setText("Grafik Total Keuntungan");
        lineChart.setDescription(description);

        // ...
        lineChart.invalidate(); // Perbarui grafik

        FloatingActionButton fab = findViewById(R.id.mfab_add_laporan);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(MainActivity.this, "Tambah Laporan");
                customDialog.show(); // Menampilkan dialog ketika tombol diklik
            }
        });

        ImageButton ib_delete1 = findViewById(R.id.ib_delete1);
        ib_delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogHapus customDialogHapus = new CustomDialogHapus(MainActivity.this);
                customDialogHapus.show(); // Menampilkan dialog ketika tombol diklik
            }
        });
        ImageButton ib_edit1 = findViewById(R.id.ib_edit1);
        ib_edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(MainActivity.this, "Edit Laporan");
                customDialog.show(); // Menampilkan dialog ketika tombol diklik
            }
        });


    }
}