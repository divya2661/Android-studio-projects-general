package com.example.dibbi.aaditya_app;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dibbi.aaditya_app.AppConfig;
import com.example.dibbi.aaditya_app.SqliteHandler;
import com.example.dibbi.aaditya_app.AppController;
import com.example.dibbi.aaditya_app.SessionManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SqliteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText)findViewById(R.id.iemail);
        inputPassword = (EditText)findViewById(R.id.ipassword);
        btnLogin = (Button)findViewById(R.id.login);
        btnLinkToRegister = (Button)findViewById(R.id.register_activity);

        //progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db = new SqliteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        if(session.isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }

        //Login Button click event
        btnLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if(!email.isEmpty() && !password.isEmpty()){
                    Log.d("okay","in if loop");
                    checkLogin(email,password);
                }else{
                    Toast.makeText(getApplicationContext(),"Please enter credentials",Toast.LENGTH_LONG).show();
                }
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

    private void checkLogin(final String email,final String password) {

        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in...");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        Log.d("okay","connection done");
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");
                        JSONObject user = jObj.getJSONObject("user");
                        String First_name = user.getString("First_name");
                        String Last_name = user.getString("Last_name");
                        String Contact = user.getString("Contact");
                        String Email = user.getString("email");
                        String Facebook = user.getString("Facebook_link");
                        String Twitter_link =user.getString("Twitter_link");
                        String created_at = user.getString("created_at");
                        String Dob = "";
                        String Address = "";
                        Log.d(TAG, "check data" + First_name + "   " + Last_name + "   " + Email + "   " + Contact + "   " + Facebook + "   "
                                + Twitter_link + "   " + Dob + "   " + Address + "   " + uid + "   " + created_at);
                        // Inserting row in users table

                        db.addUser(First_name, Last_name, Email, Contact, Facebook, Twitter_link, Dob, Address, uid, created_at);
                         //Launch main activity
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){
            protected Map<String,String> getParams(){

                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }



    private void hideDialog() {
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    private void showDialog() {
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

}
