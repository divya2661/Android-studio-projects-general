package com.example.dibbi.juspay_test1;

import android.content.Context;
import android.widget.Button;

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
}
