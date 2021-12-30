package com.example.proj1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

public class GDadapter extends BaseAdapter {

    private Integer[] images = {
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img3,
            R.drawable.img2,
            R.drawable.img1,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3
    };
    private LayoutInflater GDinflater;
    private Context context;
    private int optWidth;

    public GDadapter(@NonNull Context context, int optWidth) {
        super();
        this.GDinflater = LayoutInflater.from(context);
        this.context = context;
        this.optWidth = optWidth;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            row = GDinflater.inflate(R.layout.grid_layout, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.imageView);
            holder.image.getLayoutParams().height = optWidth;
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Glide.with(context).load(images[position]).thumbnail(0.1f).placeholder(R.drawable.load).into(holder.image);
        return row;
    }

    static class ViewHolder {
        ImageView image;
    }

}
