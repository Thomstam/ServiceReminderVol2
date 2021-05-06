package com.example.serviceReminder.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.serviceReminder.R;

public class CustomAdapter extends BaseAdapter {
    final int[] flags;
    final LayoutInflater inflater;

    public CustomAdapter(Context applicationContext, int[] flags) {
        this.flags = flags;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return flags.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.row, null);
        ImageView icon = view.findViewById(R.id.adapterIcon);
        icon.setImageResource(flags[i]);
        return view;
    }
}