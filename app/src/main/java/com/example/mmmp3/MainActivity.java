package com.example.mmmp3;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

    public class MainActivity extends AppCompatActivity {

        TextView txtTitle, txtTimeSong, txtTimeTotal;
        SeekBar skSong;
        ImageButton btnPrev, btnPlay, btnNext, btnShuff, btnLoop;
        ImageView img;
        List<Song> arrSong;
        MediaPlayer mediaPlayer;
        int position = 0;
        Animation animation;
        boolean checkRandom = false;
        boolean next = true;
        TimKiem timkiem;
        SongsAdapter adapter;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            anhXa();
            addSong();
            khoiTaoBaiHat();
            setTxtTimeTotal();

            ListView lvSongs = findViewById(R.id.lvSongs);
            List<Song> arrSong = getSongList(); // Hàm lấy danh sách bài hát
            SongsAdapter adapter = new SongsAdapter(this, arrSong);
            lvSongs.setAdapter(adapter);

            TimKiem timkiem = new TimKiem(lvSongs, adapter, arrSong);
            SearchView searchView = findViewById(R.id.txtsearch);
            SearchView.setOnQueryTextListener(TimKiem);



            animation = AnimationUtils.loadAnimation(this, R.anim.dia_xoay);

            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        btnPlay.setImageResource(R.drawable.play);
                    }
                    else{
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause);
                    }
                    updateTimeSong();
                    img.startAnimation(animation);
                }
            });

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer.isPlaying())
                        mediaPlayer.stop();
                    if (checkRandom == false)
                        position++;
                    else
                        position = (int)(Math.random() * (arrSong.size()));
                    if(position >= arrSong.size())
                        position = 0;
                    khoiTaoBaiHat();
                    btnPlay.setImageResource(R.drawable.pause);
                    setTxtTimeTotal();
                    updateTimeSong();
                    img.startAnimation(animation);
                    mediaPlayer.start();
                }
            });

            btnPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer.isPlaying())
                        mediaPlayer.stop();
                    if ( checkRandom == false)
                        position--;
                    else
                        position = (int)(Math.random() * (arrSong.size()));
                    if(position < 0)
                        position = arrSong.size()-1;
                    khoiTaoBaiHat();
                    setTxtTimeTotal();
                    updateTimeSong();
                    img.startAnimation(animation);
                    mediaPlayer.start();
                }
            });

            btnShuff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkRandom == true) {
                        checkRandom = false;
                        btnShuff.setImageResource(R.drawable.noshuffle);
                    }
                    else {
                        checkRandom = true;
                        btnShuff.setImageResource(R.drawable.shuffle);
                    }
                }
            });

            btnLoop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (next == true){
                        next = false;
                        btnLoop.setImageResource(R.drawable.repeatonce);
                    }
                    else{
                        next = true;
                        btnLoop.setImageResource(R.drawable.repeat);
                    }
                }
            });

            skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mediaPlayer.seekTo(skSong.getProgress());
                }
            });
        }


        private void addSong(){
            arrSong = new ArrayList<>();
            SongDB db = new SongDB(MainActivity.this);
            db.initData();
            arrSong = db.getAllSong();
        }

        private void anhXa(){
            txtTitle = (TextView) findViewById(R.id.textviewTitle);
            txtTimeSong = (TextView) findViewById(R.id.textviewTimeSong);
            txtTimeTotal = (TextView) findViewById(R.id.textviewTimeTotal);
            skSong = (SeekBar) findViewById(R.id.seekBar);
            btnPrev = (ImageButton) findViewById(R.id.ibPre);
            btnPlay = (ImageButton) findViewById(R.id.ibPlay);
            btnNext = (ImageButton) findViewById(R.id.ibNext);
            img = (ImageView) findViewById(R.id.imageViewDVD);
            btnShuff = (ImageButton) findViewById(R.id.ibShuff);
            btnLoop = (ImageButton) findViewById(R.id.ibLoop);
        }

        private void khoiTaoBaiHat(){
            mediaPlayer = MediaPlayer.create(MainActivity.this, arrSong.get(position).getFile());
            txtTitle.setText(arrSong.get(position).getTitle());
        }

        private void setTxtTimeTotal(){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
            txtTimeTotal.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
            skSong.setMax(mediaPlayer.getDuration());
        }

        private void updateTimeSong(){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    skSong.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 500);
                    if (mediaPlayer.getCurrentPosition() >= mediaPlayer.getDuration() ){
                        if (checkRandom == false && next == true)
                            position++;
                        else if (checkRandom == true && next == false)
                            position = (int)(Math.random() * (arrSong.size()));
                        if (position >= arrSong.size())
                            position = 0;
                        khoiTaoBaiHat();
                        setTxtTimeTotal();
                        mediaPlayer.start();
                    }
                }
            }, 100);

        }
    }
}