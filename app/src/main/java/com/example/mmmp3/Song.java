package com.example.mmmp3;

public class Song {
    private String title;
    private int file;

    public Song(){};

    public Song(String title, int file){
        this.title = title;
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }
}
