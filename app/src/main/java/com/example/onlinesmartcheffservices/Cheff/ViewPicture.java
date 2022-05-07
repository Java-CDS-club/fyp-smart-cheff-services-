package com.example.onlinesmartcheffservices.Cheff;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.onlinesmartcheffservices.R;
import com.squareup.picasso.Picasso;

public class ViewPicture extends AppCompatActivity {

    ImageView imageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_picture);
     imageView = findViewById(R.id.setpic);
        Picasso.with(getApplicationContext() ).load(getIntent().getStringExtra("uri")).fit().into(imageView);
    }
}