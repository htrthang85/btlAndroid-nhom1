package com.example.musicplayer;

public class Song {
    private String title;
    private String file;
    private String lyric;

    public Song(String title, String file) {
        this.title = title;
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public String getFile() {
        return file;
    }

    public String getLyric() {
        return lyric;
    }
}
