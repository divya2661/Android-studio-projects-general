package com.example.dibbi.login;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by dibbi on 27/12/15.
 */
public class registerBackground extends AsyncTask<String,Void,String> {

    private TextView error_box;
    private Context context;

    public registerBackground(Context context,TextView error_box){
        this.context = context;
        this.error_box =error_box;
    }

    @Override
    protected String doInBackground(String... params) {


        String Name = params[0];
        String Role = params[1];
        String Email = params[2];
        String Password = params[3];

        String link = "http://divya2661.netau.net/register.php";
        try {

            String data = URLEncoder.encode("name","UTF-8") + "=" + URLEncoder.encode(Name,"UTF-8");
            data += "&" + URLEncoder.encode("role","UTF-8") + "=" + URLEncoder.encode(Role,"UTF-8");
            data += "&" + URLEncoder.encode("email","UTF-8") + "=" + URLEncoder.encode(Email,"UTF-8");
            data += "&" + URLEncoder.encode("password","UTF-8") + "=" + URLEncoder.encode(Password,"UTF-8");

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



        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        error_box.setText(result);
    }
}
