package com.example.dibbi.juspay_test1;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.dibbi.juspay_test1.AppController;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static String url = "http://divya2661.netau.net/test.json";
    createComponent comp = new createComponent(this);
    private ProgressDialog pDialog;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout)findViewById(R.id.content_Main);
        Log.d("okay", "Coming to update11");
        //progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        Log.d("okay", "Coming to update22");
        getJson();

        Log.d("okay", "Coming to update555");

    }

    private void getJson() {

        pDialog.setMessage("getting json data");
        showDialog();
        Log.d("okay", "Coming to update666");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    Log.d("okay","Coming to update333");
                    JSONObject jObj = new JSONObject(response);

                    updateUI(jObj);
                    hideDialog();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error","there is some problem in getting response");

            }
        });

        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    private void updateUI(JSONObject jObj) {

        try {

            Log.d("okay","Coming to update44");
            JSONArray components = jObj.getJSONArray("Rlayout");
            JSONObject obj1 = components.getJSONObject(0);
            Log.d("okay","Coming: "+obj1.getString("Object"));

            if(obj1.getString("Object").equals("textView")){

                Log.d("okay","Coming in");
                int layout_width = obj1.getInt("layout_width");
                int layout_height = obj1.getInt("layout_height");
                String layout_text = obj1.getString("text");
                int textSize = obj1.getInt("textSize");
                String id = obj1.getString("id");

                Log.d("okay","Coming text: " + layout_text + "  "+layout_width + " " + layout_height + "  "+textSize);

                TextView textView = new TextView(this);

                textView.setWidth(layout_width);
                textView.setHeight(layout_height);
                textView.setText(layout_text);
                textView.setTextSize(textSize);
                textView.setTag(id);

                relativeLayout.addView(textView);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void hideDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }
}