package com.example.proj1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class VPadapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> items;
    private final ArrayList<String> texts;
    public VPadapter(@NonNull FragmentManager fm) {
        super(fm);
        items = new ArrayList<>();
        items.add(new Frag1());
        items.add(new Frag2());
        items.add(new Frag3());

        texts = new ArrayList<>();
        texts.add("Contacts");
        texts.add("Gallery");
        texts.add("Something");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return texts.get(position);
    }
}
