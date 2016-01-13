package com.example.dibbi.login;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.PrivateKey;


public class SigninActivity extends AsyncTask<String,Void,String>{

    private TextView statusField,roleField,NameField;
    private Context context;
    private int getorpost = 0;

    public SigninActivity(Context context, TextView statusField, TextView roleField, int flag) {
        this.context = context;
        this.statusField = statusField;
        this.roleField = roleField;
        getorpost = flag;

    }

    @Override
    protected String doInBackground(String... arg0) {
        if(getorpost==0){
            try{

                String Email = (String)arg0[0];
                String Passwd = (String)arg0[1];


                String link = "http://divya2661.netau.net/login.php";
                String data = URLEncoder.encode("email","UTF-8") + "=" + URLEncoder.encode(Email,"UTF-8");
                data += "&" + URLEncoder.encode("password","UTF-8") + "=" + URLEncoder.encode(Passwd,"UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine())!=null){

                    sb.append(line);
                    break;

                }
                return sb.toString();
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }
        return null;
    }
    protected void onPostExecute(String result){
        this.statusField.setText("Login Successful");
        this.roleField.setText(result);
    }
}
