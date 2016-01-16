package com.example.dibbi.aaditya_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class StudentProfile extends AppCompatActivity {

    private Button facebook,twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        facebook = (Button)findViewById(R.id.facebook_link);
        twitter = (Button)findViewById(R.id.twitter_link);

        facebook.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.facebook.com/"));
                startActivity(intent);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.twitter.com/"));
                startActivity(intent);
            }
        });

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.update_profile){

        }


        //noinspection SimplifiableIfStatement
        if (id == R.id.Student_profile_action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public void goToFb(){
        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.facebook.com/"));
        startActivity(viewIntent);
    }

    public void goToTwt(){
        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.twitter.com/"));
        startActivity(viewIntent);
    }


}
