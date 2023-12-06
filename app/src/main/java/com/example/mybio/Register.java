package com.example.mybio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class Register extends AppCompatActivity {

    TextInputEditText tietEmail, tietUsername, tietPassword;
    MaterialButton mbRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        boolean isSplash = getIntent().getBooleanExtra("isSplash", false);
//        if (isSplash == false) {
//            startActivity(new Intent(Register.this, SplashScreen.class));
//        }

        tietEmail = findViewById(R.id.tietEmail);
        tietUsername = findViewById(R.id.tietUsername);
        tietPassword = findViewById(R.id.tietPassword);

        mbRegister = findViewById(R.id.mbRegister);
        mbRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(Register.this, Login.class);
                login.putExtra("isRegister", true);
                login.putExtra("email", tietEmail.getText().toString());
                login.putExtra("username", tietUsername.getText().toString());
                login.putExtra("password", tietPassword.getText().toString());
                startActivity(login);
            }
        });

    }
}