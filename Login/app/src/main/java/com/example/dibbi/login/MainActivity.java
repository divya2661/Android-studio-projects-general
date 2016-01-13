package com.example.dibbi.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText emailField,passwdField;
    private TextView status,role,name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailField =(EditText)findViewById(R.id.email);
        passwdField=(EditText)findViewById(R.id.passwd);

        status = (TextView)findViewById(R.id.status);
        role = (TextView)findViewById(R.id.role);
        name = (TextView)findViewById(R.id.name);
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
    }

    //simple menu options not actually required for this application
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    // function for login button click
    public void login(View view){
        String email = emailField.getText().toString();
        String password = passwdField.getText().toString();
        new SigninActivity(this,status,role,0).execute(email, password);
    }

    //register redirect button funtion to redirect to register page
    public void onclickregister_re(View view){
        Intent intent = new Intent( this,RegisterActivity.class);
        startActivity(intent);
    }

}
