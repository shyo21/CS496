package com.example.proj1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Frag2 extends Fragment {

    private GridView gridView;
    private GDadapter gridAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_frag2, container, false);
        gridView = (GridView) myView.findViewById(R.id.gridView);

        // check display size, calculate optimized column width
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        int optWidth = 250 + ((width%250) / (width/250));
        gridView.setColumnWidth(optWidth);

        gridAdapter = new GDadapter(getActivity(), optWidth);
        gridView.setAdapter(gridAdapter);
        return myView;
    }
}