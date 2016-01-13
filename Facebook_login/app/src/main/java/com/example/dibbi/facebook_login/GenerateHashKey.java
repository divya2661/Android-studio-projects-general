package com.example.dibbi.facebook_login;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.content.pm.Signature;

/**
 * Created by dibbi on 1/1/16.
 */
public class GenerateHashKey extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        printHashkey();

    }

    public void printHashkey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.dibbi.facebook_login",
                    PackageManager.GET_SIGNATURES);
            //Log.e("KeyHash:", "Dibbiii");
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", "KeyHash: "+Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

}

