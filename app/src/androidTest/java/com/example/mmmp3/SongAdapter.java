package com.example.mmmp3;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends BaseAdapter {
    List<Song> arrSong = new ArrayList<>();
    @Override
    public int getCount() {
        int cout= arrSong.size();
        return cout;
    }

    @Override
    public Object getItem(int i) {
        return arrSong.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = View.inflate(R.layout.activity_tim_kiem2, null);
        ImageView icon = (ImageView) view.findViewById(R.id.txtsearch);
        icon.setImageResource(i);
        return view;
    }
}
