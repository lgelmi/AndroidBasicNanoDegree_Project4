package com.example.android.ferdecavall;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Lorenzo on 04/04/2018.
 * <p>
 * This class will contain any useful data reguarding a song.
 * Since this app is centered on a single author, it won't be part of the class.
 */

public class Song {

    private Integer number;
    private String name;
    private String lyrics;
    private MediaPlayer player;

    public Song(Context context, int songResource) {
        /*
        Base class constructor.
         */
        player = MediaPlayer.create(context, songResource);
    }

    public Song(Context context, int songResource, Integer number, String name, String lyrics) {
        /*
        Utility constructor to set everything at once.
         */
        this(context, songResource);
        setNumber(number);
        setName(name);
        setLyrics(lyrics);
    }

    int getNumber() {
        if (number == null)
            return 0;
        else
            return number;
    }

    void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        if (name == null)
            return "";
        else
            return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLyrics() {
        if (lyrics == null)
            return "";
        else
            return lyrics;
    }

    void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    boolean isPlaying(){
        return player.isPlaying();
    }

    void pause(){
        player.pause();
    }

    void start() {
        player.start();
    }

    void stop() throws IOException {
        player.stop();
        player.prepare();
    }

    void release() {
        player.release();
    }

    int getDuration(){
        return player.getDuration();
    }

}
