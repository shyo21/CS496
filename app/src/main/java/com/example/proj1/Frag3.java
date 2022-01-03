package com.example.proj1;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class Frag3 extends Fragment {

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
        int optWidth = (int) (0.22 * width);
        int optWMargin = (int) (0.25 * width);
        int optDis = (int) (0.05 * width);
        int optHMargin = (int) (1.2 * optWMargin);

        // Set button 1 and click handler for it
        ConstraintLayout btnFrame1 = myView.findViewById(R.id.imageFrame1);
        ImageView btn1 = myView.findViewById(R.id.imageView1);
        ConstraintLayout.LayoutParams params1 = (ConstraintLayout.LayoutParams) btnFrame1.getLayoutParams();
        params1.setMargins(optWMargin,optHMargin,0,0);
        params1.width = optWidth;
        params1.height = optWidth;
        btnFrame1.setLayoutParams(params1);
        btn1.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), btn1Activity.class);
            startActivity(intent);
        });

        // Set button 2 and click handler for it
        ConstraintLayout btnFrame2 = myView.findViewById(R.id.imageFrame2);
        ImageView btn2 = myView.findViewById(R.id.imageView2);
        ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) btnFrame2.getLayoutParams();
        params2.setMargins(0,optHMargin,optWMargin,0);
        params2.width = optWidth;
        params2.height = optWidth;
        btnFrame2.setLayoutParams(params2);
        btn2.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), btn2Activity.class);
            startActivity(intent);
        });

        // Set button 3 and click handler for it
        ConstraintLayout btnFrame3 = myView.findViewById(R.id.imageFrame3);
        ImageView btn3 = myView.findViewById(R.id.imageView3);
        ConstraintLayout.LayoutParams params3 = (ConstraintLayout.LayoutParams) btnFrame3.getLayoutParams();
        params3.setMargins(optWMargin,optDis,0,0);
        params3.width = optWidth;
        params3.height = optWidth;
        btnFrame3.setLayoutParams(params3);
        btn3.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), btn3Activity.class);
            startActivity(intent);
        });

        // Set button 4 and click handler for it
        ConstraintLayout btnFrame4 = myView.findViewById(R.id.imageFrame4);
        ImageView btn4 = myView.findViewById(R.id.imageView4);
        ConstraintLayout.LayoutParams params4 = (ConstraintLayout.LayoutParams) btnFrame4.getLayoutParams();
        params4.setMargins(0,optDis,optWMargin,0);
        params4.width = optWidth;
        params4.height = optWidth;
        btnFrame4.setLayoutParams(params4);
        btn4.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), btn4Activity.class);
            startActivity(intent);
        });

        // Set button 5 and click handler for it
        ConstraintLayout btnFrame5 = myView.findViewById(R.id.imageFrame5);
        ImageView btn5 = myView.findViewById(R.id.imageView5);
        ConstraintLayout.LayoutParams params5 = (ConstraintLayout.LayoutParams) btnFrame5.getLayoutParams();
        params5.setMargins(optWMargin,optDis,0,0);
        params5.width = optWidth;
        params5.height = optWidth;
        btnFrame5.setLayoutParams(params5);
        btn5.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), btn5Activity.class);
            startActivity(intent);
        });

        // Set button 6 and click handler for it
        ConstraintLayout btnFrame6 = myView.findViewById(R.id.imageFrame6);
        ImageView btn6 = myView.findViewById(R.id.imageView6);
        ConstraintLayout.LayoutParams params6 = (ConstraintLayout.LayoutParams) btnFrame6.getLayoutParams();
        params6.setMargins(0,optDis,optWMargin,0);
        params6.width = optWidth;
        params6.height = optWidth;
        btnFrame6.setLayoutParams(params6);
        btn6.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), btn6Activity.class);
            startActivity(intent);
        });

        return myView;
    }
}