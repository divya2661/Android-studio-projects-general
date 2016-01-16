package com.example.dibbi.fb_login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class StudentProfile extends AppCompatActivity {

    private static final String TAG = "StudentProfile";

    TextView name,email,bday;
    String glink,imagelink;
    ImageView profile_photo;
    ProgressDialog pDialog;
    Bitmap bitmap;
    Button google,facebook;

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

        new LoadImage().execute(imagelink);

       google = (Button)findViewById(R.id.google);
       facebook = (Button)findViewById(R.id.facebook_link);

       google.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(glink));
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.signOut){
            SignOut();
        }


        return super.onOptionsItemSelected(item);
    }

    private void SignOut() {

    }


    private class LoadImage extends AsyncTask<String,String,Bitmap>{


        @Override
        protected void onPreExecute() {

            pDialog = new ProgressDialog(StudentProfile.this);
            pDialog.setMessage("Loading image...");
            ShowDialog();
            super.onPreExecute();
        }
        @Override
        protected Bitmap doInBackground(String... params) {

            try {

                URL newurl = new URL(imagelink);
                bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            if(bitmap!=null){
                profile_photo.setImageBitmap(bitmap);
                pDialog.dismiss();
            }else{
                pDialog.dismiss();
                Toast.makeText(StudentProfile.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(bitmap);
        }
    }

    public void ShowDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    public void HideDialog(){
        if(pDialog.isShowing()) {
            pDialog.hide();
        }
    }



}
