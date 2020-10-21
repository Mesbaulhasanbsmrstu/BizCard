package com.Nextechbd.bizcards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;

import com.Nextechbd.bizcards.R;

public class spashscreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=2500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent=new Intent(spashscreen.this,Login.class);
                startActivity(mainIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}