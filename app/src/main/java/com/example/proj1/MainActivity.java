package com.example.proj1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vp = findViewById(R.id.viewpager);
        vp.setOffscreenPageLimit(2);
        VPadapter ad = new VPadapter(getSupportFragmentManager());
        vp.setAdapter(ad);


        TabLayout tab = findViewById(R.id.tab);
        tab.setupWithViewPager(vp);
    }

}