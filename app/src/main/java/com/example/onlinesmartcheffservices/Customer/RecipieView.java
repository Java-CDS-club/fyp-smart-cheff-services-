package com.example.onlinesmartcheffservices.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.onlinesmartcheffservices.R;
import com.google.android.material.snackbar.Snackbar;

public class RecipieView extends AppCompatActivity {

    VideoView simpleVideoView;
    MediaController mediaControls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie_view);
        simpleVideoView = (VideoView) findViewById(R.id.videoViewRelative);

        if (mediaControls == null) {
            // create an object of media controller class
            mediaControls = new MediaController(RecipieView.this);
            mediaControls.setAnchorView(simpleVideoView);

        }
        simpleVideoView.setMediaController(mediaControls);
        // set the uri for the video view
        simpleVideoView.setVideoURI(Uri.parse(getIntent().getStringExtra("uri")));
        // start a video
        simpleVideoView.start();
        simpleVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //Toast.makeText(getApplicationContext(), "Thank You...!!!", Toast.LENGTH_LONG).show(); // display a toast when an video is completed
                Snackbar.make(simpleVideoView, "Thank You...!!!", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();
            }
        });
        simpleVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(getApplicationContext(), "Oops An Error Occur While Playing Video...!!!", Toast.LENGTH_LONG).show(); // display a toast when an error is occured while playing an video

                Snackbar.make(simpleVideoView, "Oops An Error Occur While Playing Video...!!!", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();
                return false;
            }
        });

    }
}