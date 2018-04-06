package com.example.android.ferdecavall;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Lorenzo on 04/04/2018.
 * <p>
 * An array adapter allowing to list an interact with a Song List.
 */

public class SongAdapter extends ArrayAdapter<Song> {

    Song playingSong;
    ImageButton playingButton;

    SongAdapter(Activity activity, ArrayList<Song> songList) {
        super(activity, R.layout.song_list_item, songList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.song_list_item, parent, false);
        }
        final Song currentSong = getItem(position);
        if (currentSong != null) {
            int durationMs = currentSong.getDuration();
            int durationM = durationMs / 60000;
            int durationS = durationMs / 1000 - durationM * 60;

            TextView nameView = listItemView.findViewById(R.id.songName);
            nameView.setText(getContext().getString(R.string.SongName, currentSong.getNumber(),
                    currentSong.getName()));
            TextView durationView = listItemView.findViewById(R.id.songDuration);
            durationView.setText(getContext().getString(R.string.SongDuration, durationM,
                    durationS));
            final ImageButton playButton = listItemView.findViewById(R.id.PlayButton);
            if (currentSong.isPlaying())
                playButton.setImageResource(android.R.drawable.ic_media_pause);
            else
                playButton.setImageResource(android.R.drawable.ic_media_play);
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                /*
                Start the song
                 */
                    ImageButton button = (ImageButton) view;
                    if (currentSong.isPlaying()) {
                        currentSong.pause();
                        button.setImageResource(android.R.drawable.ic_media_play);
                    } else {
                        if (playingSong != null && playingSong != currentSong) {
                            try {
                                playingSong.stop();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (playingButton != null) {
                            playingButton.setImageResource(android.R.drawable.ic_media_play);
                        }
                        currentSong.start();
                        button.setImageResource(android.R.drawable.ic_media_pause);
                        playingSong = currentSong;
                        playingButton = button;
                    }
                }
            });
        }
        return listItemView;
    }
}
