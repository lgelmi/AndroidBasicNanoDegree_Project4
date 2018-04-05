package com.example.android.ferdecavall;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View album = findViewById(R.id.AlbumImage);
        View site = findViewById(R.id.Site);
        View facebook = findViewById(R.id.Facebook);
        View twitter = findViewById(R.id.Twitter);
        View youtube = findViewById(R.id.Youtube);
        View instagram = findViewById(R.id.Instagram);
        View spotify = findViewById(R.id.Spotify);

        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Opens the Song List
                 */
                Intent songIntent = new Intent(MainActivity.this, SongActivity.class);
                startActivity(songIntent);
            }
        });

        site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Redirects to the Menagrama's site
                 */
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www" +
                        ".menagrama.it/"));
                startActivity(browserIntent);
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Redirects to the Menagrama's facebook page.

                Credits to http://stackoverflow.com/a/24547437/1048340
                 */
                String url = "https://www.facebook.com/menagrama";
                Uri uri = Uri.parse(url);
                if (isAppInstalled("com.facebook.katana"))
                    uri = Uri.parse("fb://facewebmodal/f?href=" + url);
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(facebookIntent);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                /*
                Redirects to the Menagrama's twitter page.
                 */
                Uri uri = Uri.parse("https://twitter.com/#!/menagrama");
                if (isAppInstalled("com.twitter.android"))
                    uri = Uri.parse("twitter://user?screen_name=menagrama");
                Intent twitterIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(twitterIntent);
            }
        });
        youtube.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                /*
                Redirects to the Menagrama's youtube channel page.
                 */
                Uri uri = Uri.parse("https://www.youtube.com/channel/UCGq2_7el65Mm4m3ZC_8dbGg");
                Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(youtubeIntent);
            }
        });
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 /*
                Redirects to the Menagrama's Instagram page.
                 */
                Uri uri = Uri.parse("https://www.instagram.com/menagrama_band");
                if (isAppInstalled("com.instagram.android"))
                    uri = Uri.parse("http://instagram.com/_u/menagrama_band");
                Intent instagramIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(instagramIntent);
            }
        });
        spotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Redirects to Fer De Cavall spotify album.
                 */
                Uri uri = Uri.parse("https://open.spotify" +
                        ".com/album/41aRLbOHk8jkOEKs8nZMZw?si=L_OkAd8ISjGnvcwaxeCSiw");
                if (isAppInstalled("com.instagram.android"))
                    uri = Uri.parse
                            ("spotify:album:41aRLbOHk8jkOEKs8nZMZw?si=L_OkAd8ISjGnvcwaxeCSiw");
                Intent instagramIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(instagramIntent);
            }
        });
        try {
            loadJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean isAppInstalled(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void loadJSON() throws IOException {
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
                    case "credits":
                        TextView creditsView = findViewById(R.id.Credits);
                        creditsView.setText(reader.nextString());
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            reader.endObject();
        }
    }
}
