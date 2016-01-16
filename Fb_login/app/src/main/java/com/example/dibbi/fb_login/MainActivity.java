package com.example.dibbi.fb_login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.internal.LocationRequestUpdateData;
import com.example.dibbi.fb_login.AppController;
import com.google.android.gms.plus.Plus;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 9001;
    private String Gname,Gemail,Gid,Ggender,Glink,Flink,Gbday,Gimagelink;
    protected GoogleApiClient mGoogleApiClient;
    private TextView mstatusTextView;
    private ProgressDialog pDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        pDialog = new ProgressDialog(this);
        mstatusTextView = (TextView)findViewById(R.id.login_text);
        findViewById(R.id.sign_in_button).setOnClickListener(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        SignInButton signInButton = (SignInButton)findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.signOut){
            SignOut();
        }

        return super.onOptionsItemSelected(item);
    }

    private void SignOut() {

        if(mGoogleApiClient.isConnected()){
           
        }

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

       // Log.d(TAG,"onstart");

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);


        if(opr.isDone()){
            //Log.d(TAG,"Got Cached Sign in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }
        else{
            //Log.d("error","else me come opr: " +opr );
            //showProgressdialogue();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    //hideProgressDialogue();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
      //  Log.d(TAG, "handleSignInResult: " + result.isSuccess());

        if(result.isSuccess()){
            GoogleSignInAccount acct = result.getSignInAccount();

            Gname = acct.getDisplayName();
            Gemail = acct.getEmail();

            //mstatusTextView.setText(Gname + " " + Gemail);
            Gid = acct.getId();
            String fields = "?fields=birthday%2CcurrentLocation%2CdisplayName%2Cgender%2Cid%2Cimage%2Curl&key=";
            String apiKey = getResources().getString(R.string.google_api_key);
            String url = " https://www.googleapis.com/plus/v1/people/" + Gid + fields + apiKey;

            Log.d(TAG, "url: " + url);

            pDialog.setMessage("Setting Profile....");
            showDialog();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d(TAG, "LoginResponse: " + response.toString());
                    hideDialog();

                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject image = jsonObject.getJSONObject("image");

                        Gbday = jsonObject.getString("birthday");
                        Ggender = jsonObject.getString("gender");
                        Glink = jsonObject.getString("url");
                        Gimagelink = image.getString("url");

                        Log.d(TAG, Gbday + " " + Ggender + " " + Glink + " " + Gimagelink);

                        Intent intent = new Intent(MainActivity.this,StudentProfile.class);
                        intent.putExtra("Name",Gname);
                        intent.putExtra("Email",Gemail);
                        intent.putExtra("Bday", Gbday);
                        intent.putExtra("Gender",Ggender);
                        intent.putExtra("Link",Glink);
                        intent.putExtra("ImageLink",Gimagelink);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {


                }
            });

            AppController.getInstance().addToRequestQueue(stringRequest);


        }



    }

    private void signIn(){
        Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signIntent, RC_SIGN_IN);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
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

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
