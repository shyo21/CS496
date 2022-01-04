package com.example.proj1;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class btn2Activity extends AppCompatActivity {
    Button switch_btn;
    ImageView sun_image, moon_image;
    boolean isNightModeOn;
    SharedPreferences appSettingPrefs;
    SharedPreferences.Editor sharedPrefsEdit;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag3_btn2);

        switch_btn = findViewById(R.id.btn_dark_mode);
        sun_image = findViewById(R.id.sun);
        moon_image = findViewById(R.id.moon);

        appSettingPrefs = getSharedPreferences("AppSettingPrefs", 0);
        sharedPrefsEdit = appSettingPrefs.edit();
        isNightModeOn = appSettingPrefs.getBoolean("NightMode", false);

        if (isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //switch_btn.setText("Disable Dark Mode");
            moon_image.setVisibility(View.VISIBLE);
            sun_image.setVisibility(View.INVISIBLE);

        } else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            //switch_btn.setText("Enable Dark Mode");
            sun_image.setVisibility(View.VISIBLE);
            moon_image.setVisibility(View.INVISIBLE);
        }

        switch_btn.setOnClickListener(view -> {
            if (isNightModeOn){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                sharedPrefsEdit.putBoolean("NightMode", false);
                sharedPrefsEdit.apply();
                //switch_btn.setText("Enable Dark Mode");
                sun_image.setVisibility(View.VISIBLE);
                moon_image.setVisibility(View.INVISIBLE);
            } else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                sharedPrefsEdit.putBoolean("NightMode", true);
                sharedPrefsEdit.apply();
                //switch_btn.setText("Disable Dark Mode");
                moon_image.setVisibility(View.VISIBLE);
                sun_image.setVisibility(View.INVISIBLE);
            }
        });
    }
}
