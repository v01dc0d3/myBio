package com.example.mybio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity implements CustomDialogListener {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    public ListViewLaporan lv_laporan_adapter;
    private List<String> list_idlaporan = new ArrayList<>();
    private List<String> list_tanggal = new ArrayList<>();
    private List<String> list_totalGrabFood = new ArrayList<>();
    private List<String> list_totalShopeeFood = new ArrayList<>();
    private List<String> list_totalGofood = new ArrayList<>();
    private List<String> list_total = new ArrayList<>();
    private ListView lv_laporan;
    private String[] nomor_bulan = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    String[] label_bulan = {"Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov"};
    private LineChart lineChart;
    private List<Integer> list_total_bulanan = new ArrayList<>();
    private String yearInString;
    private TextInputEditText tiet_filterTahun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init current year
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        yearInString = String.valueOf(year);
        tiet_filterTahun = findViewById(R.id.tiet_filterTahun);
        tiet_filterTahun.setText(yearInString);

        // Init Chart Laporan
        initChartLaporan();

        // Init Adapter Laporan
        lv_laporan = findViewById(R.id.lv_laporan);
        initLaporanAdapter();

        Button btn_filterTahun = findViewById(R.id.btn_filterTahun);
        btn_filterTahun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                int settedYear;

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                tiet_filterTahun.setText(String.valueOf(year));
                                changeChartLaporan();
                                onLaporanChanged();
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.mfab_add_laporan);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogTambahLaporan dialogTambahLaporan = new DialogTambahLaporan(MainActivity.this, "Tambah Laporan");
                dialogTambahLaporan.setOnDataChangedListener(MainActivity.this);
                dialogTambahLaporan.show();
            }
        });

    }
    @Override
    public void onLaporanChanged() {
        Log.d("MainActivity", "DataChanged");
        dbHelper = new DatabaseHelper(MainActivity.this);
        db = dbHelper.getWritableDatabase();

        list_idlaporan.clear();
        list_tanggal.clear();
        list_totalGrabFood.clear();
        list_totalShopeeFood.clear();
        list_totalGofood.clear();
        list_total.clear();

        cursor = db.rawQuery("SELECT * FROM reports ORDER BY id_report DESC", null);
        while (cursor.moveToNext()) {
            String db_id_report = cursor.getString(cursor.getColumnIndexOrThrow("id_report"));
            String db_tanggal = cursor.getString(cursor.getColumnIndexOrThrow("tanggal"));
            String db_totalGrabFood = cursor.getString(cursor.getColumnIndexOrThrow("totalGrabFood"));
            String db_totalShopeeFood = cursor.getString(cursor.getColumnIndexOrThrow("totalShopeeFood"));
            String db_totalGofood = cursor.getString(cursor.getColumnIndexOrThrow("totalGofood"));
            String db_total = cursor.getString(cursor.getColumnIndexOrThrow("total"));

            if (db_tanggal.split("-")[2].equals(tiet_filterTahun.getText().toString())) {
                list_idlaporan.add(db_id_report);
                list_tanggal.add(db_tanggal);
                list_totalGrabFood.add(db_totalGrabFood);
                list_totalShopeeFood.add(db_totalShopeeFood);
                list_totalGofood.add(db_totalGofood);
                list_total.add(db_total);
            }
        }

        cursor.close();
        db.close();

        lineChart.invalidate();
        lv_laporan_adapter.notifyDataSetChanged();
    }

    private void initChartLaporan() {
        lineChart = findViewById(R.id.lineChart);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setGridColor(Color.WHITE);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(label_bulan));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(6); // Atur jumlah label yang ditampilkan
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setGridColor(Color.WHITE);

        getLaporanBulanan();
        ArrayList<Entry> dataEntries = new ArrayList<>();
        for ( int i = 0; i < list_total_bulanan.size(); i++ ) {
            dataEntries.add(new Entry(i+1, list_total_bulanan.get(i)));
        }

        // Inisialisasi data dan dataset
        LineData lineData = new LineData();
        LineDataSet lineDataSet = new LineDataSet(dataEntries, "Label");
        lineDataSet.setLineWidth(2f); // Lebar garis
        lineData.addDataSet(lineDataSet);

        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawCircles(true); // Nonaktifkan penanda titik
        lineChart.setHardwareAccelerationEnabled(true); // Aktifkan akselerasi perangkat keras

        // Atur data ke grafik
        lineChart.setData(lineData);

        // Atur deskripsi grafik (opsional)
        Description description = new Description();
        description.setText("Grafik Total Keuntungan");
        lineChart.setDescription(description);

        // ...
        lineChart.invalidate(); // Perbarui grafik
    }
    private void changeChartLaporan() {
        getLaporanBulanan();
        ArrayList<Entry> dataEntries = new ArrayList<>();
        for ( int i = 0; i < list_total_bulanan.size(); i++ ) {
            dataEntries.add(new Entry(i+1, list_total_bulanan.get(i)));
        }
        // Inisialisasi data dan dataset
        LineData lineData = new LineData();
        LineDataSet lineDataSet = new LineDataSet(dataEntries, "Label");
        lineDataSet.setLineWidth(2f); // Lebar garis
        lineData.addDataSet(lineDataSet);

        // Atur data ke grafik
        lineChart.setData(lineData);

        lineChart.invalidate();
    }
    private void getLaporanBulanan() {
        dbHelper = new DatabaseHelper(MainActivity.this);
        db = dbHelper.getWritableDatabase();

        list_total_bulanan = new ArrayList<>();

        for(int i = 0; i < 12; i++) {
            list_total_bulanan.add(0);
        }

        cursor = db.rawQuery("SELECT * FROM reports", null);
        while (cursor.moveToNext()) {
            String db_tanggal = cursor.getString(cursor.getColumnIndexOrThrow("tanggal"));
            String db_total = cursor.getString(cursor.getColumnIndexOrThrow("total"));

            Log.d("tanggal", db_tanggal);

            if (db_tanggal.split("-")[2].equals(tiet_filterTahun.getText().toString())) {
                Integer int_idx_total_bulanan = Integer.parseInt(db_tanggal.split("-")[1]) - 1;

                int existingValue = list_total_bulanan.get(int_idx_total_bulanan);
                int newValue = existingValue + Integer.parseInt(db_total);
                list_total_bulanan.set(int_idx_total_bulanan, newValue);
            }

        }

        cursor.close();
        db.close();

        Log.d("total_bulanan", list_total_bulanan.toString());
    }
    public void initLaporanAdapter() {
        dbHelper = new DatabaseHelper(MainActivity.this);
        db = dbHelper.getWritableDatabase();

        cursor = db.rawQuery("SELECT * FROM reports ORDER BY id_report DESC", null);
        while (cursor.moveToNext()) {
            String db_id_report = cursor.getString(cursor.getColumnIndexOrThrow("id_report"));
            String db_tanggal = cursor.getString(cursor.getColumnIndexOrThrow("tanggal"));
            String db_totalGrabFood = cursor.getString(cursor.getColumnIndexOrThrow("totalGrabFood"));
            String db_totalShopeeFood = cursor.getString(cursor.getColumnIndexOrThrow("totalShopeeFood"));
            String db_totalGofood = cursor.getString(cursor.getColumnIndexOrThrow("totalGofood"));
            String db_total = cursor.getString(cursor.getColumnIndexOrThrow("total"));

            list_idlaporan.add(db_id_report);
            list_tanggal.add(db_tanggal);
            list_totalGrabFood.add(db_totalGrabFood);
            list_totalShopeeFood.add(db_totalShopeeFood);
            list_totalGofood.add(db_totalGofood);
            list_total.add(db_total);
        }

        cursor.close();
        db.close();

        Log.d("db_total", list_total.toString());

        lv_laporan_adapter = new ListViewLaporan(list_idlaporan, list_tanggal, list_totalGrabFood, list_totalShopeeFood, list_totalGofood, list_total, MainActivity.this, MainActivity.this);
        lv_laporan.setAdapter(lv_laporan_adapter);

//        setListViewHeightBasedOnChildren(lv_laporan);
    }

}