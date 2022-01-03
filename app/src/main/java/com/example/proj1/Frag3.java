package com.example.proj1;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class Frag3 extends Fragment {

    private ConstraintLayout btn1;
    private ImageButton btn2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_frag3, container, false);

        // check display size, calculate optimized column width
        DisplayMetrics displaymetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int optWidth = (int) (0.2 * width);

        // Set button 1 and click handler for it
        btn1 = (ConstraintLayout) myView.findViewById(R.id.imageFrame1);
        ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(optWidth, (int) (1.15*optWidth));
        params1.setMargins(100,10,0,0);
        btn1.setLayoutParams(params1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), btn1Activity.class);
                startActivity(intent);
            }
        });

        return myView;
    }
}