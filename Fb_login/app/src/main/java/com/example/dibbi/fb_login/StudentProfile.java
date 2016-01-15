package com.example.dibbi.fb_login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class StudentProfile extends AppCompatActivity {

    private static final String TAG = "StudentProfile";

    TextView name,email,bday;
    String glink,imagelink;
    ImageView profile_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        TextView name = (TextView)findViewById(R.id.name);
        TextView email = (TextView)findViewById(R.id.email);
        TextView bday = (TextView)findViewById(R.id.dob);
        profile_photo = (ImageView)findViewById(R.id.profile_pic);

        Intent intent = getIntent();

        name.setText(intent.getStringExtra("Name"));
        email.setText(intent.getStringExtra("Email"));
        bday.setText(intent.getStringExtra("Bday"));
        glink = intent.getStringExtra("Link");
        imagelink = intent.getStringExtra("ImageLink");

        try {

            URL newurl = new URL(imagelink);
            Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            profile_photo.setImageBitmap(mIcon_val);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG,name + " " + email + " " + bday + " " + glink + " " + imagelink);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



}
