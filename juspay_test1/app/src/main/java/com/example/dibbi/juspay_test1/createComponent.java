package com.example.dibbi.juspay_test1;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by dibbi on 14/1/16.
 */
public class createComponent {

    Context context;

    public createComponent(Context context) {

        this.context = context;
    }

    public Button createButotn(){
        Button button = new Button(context);
        return button;
    }

    public TextView createTextView(){

        TextView textView = new TextView(context);
        return textView;
    }

    public EditText createEditText(){

        EditText editText = new EditText(context);
        return editText;
    }

}
