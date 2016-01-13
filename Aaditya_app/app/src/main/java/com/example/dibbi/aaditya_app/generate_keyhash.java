package com.example.dibbi.aaditya_app;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by dibbi on 10/1/16.
 */
public class generate_keyhash extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("okay lets see: ", "Dibbiii generatekeyhash");
        printHashkey();
    }

    public void printHashkey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.dibbi.aaditya_app",
                    PackageManager.GET_SIGNATURES);
            Log.e("KeyHash:", "Dibbiii");
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", "KeyHash: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }
}
