package com.example.android.flyballscorecounter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    Context context;
    int backgrounds[];
    String[] strings;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, int[] backgrounds, String strings[]) {
        this.context = applicationContext;
        this.backgrounds = backgrounds;
        this.strings = strings;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return backgrounds.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_layout, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        icon.setImageResource(backgrounds[i]);
        names.setText(strings[i]);
        return view;
    }

}