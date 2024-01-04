package com.example.mybio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    TextInputEditText tieUsername, tiePassword;
    MaterialButton btnLogin;
    String username, password;
    private List<String> listusername = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tieUsername = findViewById(R.id.tieUsername);
        tiePassword = findViewById(R.id.tiePassword);
        btnLogin = findViewById(R.id.btnLogin);

        tieUsername.setText(getIntent().getStringExtra("username"));
        tiePassword.setText(getIntent().getStringExtra("password"));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(Login.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                username = tieUsername.getText().toString();

                Cursor cursor = db.rawQuery("SELECT * FROM users", null);
                while (cursor.moveToNext()) {
                    String db_username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                    String db_email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                    Log.d("", db_username + " || " + db_email + " == " + username);
                    Log.d("check",(username.equals(db_username)) ? "true" : "false");

                    if (username.equals(db_username) || username.equals(db_email)) {
                        Log.d("db_username", db_username);
                        Log.d("db_email", db_email);

                        password = tiePassword.getText().toString();
                        String db_password = cursor.getString(cursor.getColumnIndexOrThrow ("password"));
                        if (password.equals(db_password)) {
                            Log.d("db_password", db_password);
                            String db_role_id = cursor.getString(cursor.getColumnIndexOrThrow ("role_id"));
                            if ( db_role_id.toString().equals("1") ) {
                                Intent main;
                                main = new Intent(Login.this, MainActivity.class);
                                main.putExtra("email", db_email);
                                main.putExtra("username", username);
                                main.putExtra("role", "admin");
                                main.putExtra("isLogin", true);

                                Toast.makeText(Login.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                startActivity(main);
                            } else {
                                Toast.makeText(Login.this, "Anda Bukan Admin", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Login.this, "Username atau Password Tidak Sesuai", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Login.this, "Username atau Password Tidak Sesuai", Toast.LENGTH_SHORT).show();
                    }
                }
                cursor.close();
                db.close();
            }
        });
    }
}