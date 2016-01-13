package com.example.dibbi.fb_login;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by dibbi on 11/1/16.
 */
public class AbstractGetName extends AsyncTask<Void,Void,Void> {

    protected MainActivity mActivity;
    public static String GOOGLE_USER_DATA = "No data";
    protected String mscope;
    protected String mEmail;
    protected int mRequest;

    public AbstractGetName(MainActivity mActivity, String mscope, String mEmail) {
        this.mActivity = mActivity;
        this.mscope = mscope;
        this.mEmail = mEmail;
    }

    @Override
    protected Void doInBackground(Void... params) {


        return null;
    }



}
