package com.example.mybio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "db_mybio";
    // database version
    static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE users (id_user INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, email TEXT NOT NULL, password TEXT NOT NULL, role_id INTEGER NOT NULL);";
        db.execSQL(sql);
        sql = "INSERT INTO users VALUES(NULL, 'Nunu Twins', 'nunutwins@gmail.com', '12345678', '1');";
        db.execSQL(sql);

        sql = "CREATE TABLE roles (id_role INTEGER PRIMARY KEY AUTOINCREMENT, rolename TEXT NOT NULL);";
        db.execSQL(sql);
        sql = "INSERT INTO roles VALUES(NULL, 'admin');";
        db.execSQL(sql);

        sql = "CREATE TABLE reports (id_report INTEGER PRIMARY KEY AUTOINCREMENT, tanggal TEXT NOT NULL, totalGrabFood TEXT NOT NULL, totalShopeeFood TEXT NOT NULL, totalGofood TEXT NOT NULL, total TEXT NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS roles");
        db.execSQL("DROP TABLE IF EXISTS reports");
        onCreate(db);
    }


}

