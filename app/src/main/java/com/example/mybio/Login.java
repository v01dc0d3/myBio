package com.example.mybio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class Login extends AppCompatActivity {
    TextInputEditText tieUsername, tiePassword;
    MaterialButton btnLogin;
    MaterialTextView mtv_toregister;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        boolean isRegister = getIntent().getBooleanExtra("isRegister", false);
////        Toast.makeText(Login.this, String.valueOf(isRegister), Toast.LENGTH_SHORT).show();
//        if (!isRegister) {
//            startActivity(new Intent(Login.this, Register.class));
//        }

        tieUsername = findViewById(R.id.tieUsername);
        tiePassword = findViewById(R.id.tiePassword);
        btnLogin = findViewById(R.id.btnLogin);
        mtv_toregister = findViewById(R.id.mtv_toregister);

        tieUsername.setText(getIntent().getStringExtra("username"));
        tiePassword.setText(getIntent().getStringExtra("password"));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = tieUsername.getText().toString();
                password = tiePassword.getText().toString();

                // Toast.makeText(Login.this, "username: " + username + ", password: " + password, Toast.LENGTH_SHORT).show();
                Intent main;
                main = new Intent(Login.this, MainActivity.class);
                main.putExtra("email", getIntent().getStringExtra("email"));
                main.putExtra("username", username);
                main.putExtra("password", password);
                main.putExtra("isLogin", true);

                startActivity(main);
            }
        });

        mtv_toregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(Login.this, Register.class);
                startActivity(register);
            }
        });
    }
}