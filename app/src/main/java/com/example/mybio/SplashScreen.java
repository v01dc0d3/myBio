package com.example.mybio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    ImageView iv_logo;
    TextView tv_nunu, tv_twins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        iv_logo = findViewById(R.id.iv_logo);
        tv_nunu = findViewById(R.id.tv_nunu);
        tv_twins = findViewById(R.id.tv_twins);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.down_from_top);
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.up_from_bottom);

        iv_logo.startAnimation(anim);
        tv_nunu.startAnimation(anim2);
        tv_twins.startAnimation(anim2);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent login = new Intent(SplashScreen.this, Login.class);
            startActivity(login);
        }, 5000L);
    }
}