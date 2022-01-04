package com.example.proj1;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;

public class Frag2 extends Fragment {

    private GridView gridView;
    private ImageAdapter gridAdapter;
    private ArrayList<String> images;
    private SwipeRefreshLayout swipeView;
    private ImageView imageView;
    private int shortAnimationDuration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_frag2, container, false);
        gridView = myView.findViewById(R.id.gridView);
        swipeView = myView.findViewById(R.id.swipeLayout);
        imageView = myView.findViewById(R.id.fullImage);
        imageView.setVisibility(View.GONE);
        shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        // check display size, calculate optimized column width
        DisplayMetrics displaymetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int optWidth = 250 + ((width%250) / (width/250));
        gridView.setColumnWidth(optWidth);

        // set image adapter
        gridAdapter = new ImageAdapter(requireActivity(), optWidth);
        gridView.setAdapter(gridAdapter);

        // handle click event
        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            Glide.with(requireContext()).load(images.get(i)).placeholder(R.drawable.load).into(imageView);
            crossFade();
        });

        // handle refresh event
        swipeView.setOnRefreshListener(() -> {
            gridAdapter = new ImageAdapter(requireActivity(), optWidth);
            gridView.setAdapter(gridAdapter);
            swipeView.setRefreshing(false);
        });

        return myView;
    }

    // helper function for click event handler
    private void crossFade() {
        // when 1st touch, fade in imageView, fade out gridView
        imageView.setAlpha(0f);
        imageView.setVisibility(View.VISIBLE);
        imageView.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(null);
        gridView.animate().alpha(0f).setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        gridView.setVisibility(View.GONE);
                    }
                });

        // when 2nd touch, reverse to original
        imageView.setOnClickListener(view -> {
            gridView.setAlpha(0f);
            gridView.setVisibility(View.VISIBLE);
            gridView.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(null);
            imageView.animate().alpha(0f).setDuration(shortAnimationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) { imageView.setVisibility(View.GONE); }
                    });
        });
    }

    // adapter for showing images in gridView
    private class ImageAdapter extends BaseAdapter {

        private final LayoutInflater imageInflater;
        private final Context context;
        private final int optWidth;

        public ImageAdapter(@NonNull Context context, int optWidth) {
            super();
            this.imageInflater = LayoutInflater.from(context);
            this.context = context;
            this.optWidth = optWidth;
            images = getAllImages();
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder;

            if (row == null) {
                row = imageInflater.inflate(R.layout.grid_layout, parent, false);
                holder = new ViewHolder();
                holder.image = row.findViewById(R.id.imageView);
                holder.image.getLayoutParams().height = optWidth;
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            Glide.with(context).load(images.get(position)).placeholder(R.drawable.load).thumbnail(0.1f).into(holder.image);
            return row;
        }

    }

        static class ViewHolder {
            ImageView image;
        }

        // helper function collecting all image files in storage
        @SuppressLint("Recycle")
        private ArrayList<String> getAllImages() {
            Uri uri;
            Cursor cursor;
            int column_index_data;
            ArrayList<String> listImages = new ArrayList<>();
            String absPath;
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            String[] proj = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

            cursor = requireActivity().getContentResolver().query(uri, proj, null, null, null);

            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

            while (cursor.moveToNext()) {
                absPath = cursor.getString(column_index_data);
                listImages.add(absPath);
            }

            Collections.reverse(listImages);
            return listImages;
        }
}