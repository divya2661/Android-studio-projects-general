package com.example.dibbi.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {

    private EditText namefield,rolefield,emailfield,passwdfield;
    private TextView Error_box_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        namefield = (EditText) findViewById(R.id.name);
        rolefield = (EditText) findViewById(R.id.role);
        emailfield = (EditText) findViewById(R.id.email);
        passwdfield = (EditText) findViewById(R.id.password);

        Error_box_field = (TextView) findViewById(R.id.Error_box);

    }

    public void register_submit(View view){
        String Name = namefield.getText().toString();
        String Role = rolefield.getText().toString();
        String Email = emailfield.getText().toString();
        String Password = passwdfield.getText().toString();

        new registerBackground(this,Error_box_field).execute(Name,Role,Email,Password);
    }

    public void backtologin(View view){

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
