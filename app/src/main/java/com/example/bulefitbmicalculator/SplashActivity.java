package com.example.bulefitbmicalculator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView gerbangKiri, gerbangKanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        gerbangKiri = findViewById(R.id.gerbang_kiri);
        gerbangKanan = findViewById(R.id.gerbang_kanan);

        // JEDA 500ms: Diam sebentar sesuai Layar 1 Figma, lalu jalankan animasi gerbang terbuka
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation slideLeft = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.slide_out_left);
                Animation slideRight = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.slide_out_right);

                gerbangKiri.startAnimation(slideLeft);
                gerbangKanan.startAnimation(slideRight);

                // Sembunyikan gerbang setelah animasi selesai bergeser
                gerbangKiri.setVisibility(View.GONE);
                gerbangKanan.setVisibility(View.GONE);
            }
        }, 500);

        // JEDA TOTAL (2500ms): Berpindah ke MainActivity setelah logo selesai terlihat
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, 2500);
    }
}