package com.example.android.ferdecavall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SongActivity extends AppCompatActivity {

    ArrayList<Song> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        songList = null;
        try {
            songList = loadJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SongAdapter adapter = new SongAdapter(this, songList);
        ListView listView = findViewById(R.id.songList);
        listView.setAdapter(adapter);
        try {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    private ArrayList<Song> loadJSON() throws IOException {
        /*
        Reads my JSON file.

        This should be heavily improved for robustness, but I don't think it is worth it in this
        case.
         */
        try (JsonReader reader = new JsonReader(new InputStreamReader(getAssets().open
                ("FerDeCavall.json"), "UTF-8"))) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "songs":
                        return loadJSONQuestions(reader);
                    default:
                        reader.skipValue();
                        break;
                }
            }
            return null;
        }

    }

    private ArrayList<Song> loadJSONQuestions(JsonReader reader) throws IOException {
        /*
        Specialized method to parse the question array.
         */
        int number;
        String name;
        String details;
        String file;
        String lyrics;
        ArrayList<Song> songList = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            name = "";
            details = "";
            file = "";
            lyrics = "";
            number = 0;
            reader.beginObject();
            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case "file":
                        file = reader.nextString();
                        break;
                    case "lyrics":
                        lyrics = reader.nextString();
                        break;
                    case "name":
                        name = reader.nextString();
                        break;
                    case "number":
                        number = reader.nextInt();
                        break;
                    case "details":
                        details = reader.nextString();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
            if (!Objects.equals(file, "")) {
                int songId = getResources().getIdentifier(file, "raw", getPackageName());
                songList.add(new Song(getApplicationContext(), songId, number, name, lyrics));
            }
        }
        reader.endArray();
        return songList;
    }

    @Override
    protected void onDestroy() {
        for (int i = 0; i < songList.size(); i++)
            songList.get(i).release();
        super.onDestroy();
    }
}
