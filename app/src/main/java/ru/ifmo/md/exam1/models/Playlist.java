package ru.ifmo.md.exam1.models;

import java.util.ArrayList;

/**
 * Created by creed on 23.01.15.
 */
public class Playlist {
    private ArrayList<Song> songs;

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "songs=" + songs +
                '}';
    }
}
