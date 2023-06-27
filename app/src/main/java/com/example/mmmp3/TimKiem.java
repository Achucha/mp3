package com.example.mmmp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;
import java.util.stream.Collectors;

public class TimKiem extends AppCompatActivity implements SearchView.OnQueryTextListener {

     ListView lvSongs;
     SongAdapter adapter;
     List<Song> allSongs;
     List<Song> filteredSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }

    public TimKiem(ListView lvSongs, Adapter adapter, List<Song> allSongs) {
        this.lvSongs = lvSongs;
        this.adapter = adapter;
        this.allSongs = allSongs;
        this.filteredSongs = allSongs; /
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filteredSongs = allSongs.stream()
                .filter(song -> song.getTitle().toLowerCase().contains(newText.toLowerCase()))
                .collect(Collectors.toList());
        Adapter.setItems(filteredSongs);
        lvSongs.setAdapter(adapter);
        return true;
    }




}