package com.cdd.smartsurvey;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class DialogHomeNoActivity extends AppCompatActivity {

    EditText Homeno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_homeno);
        Homeno = (EditText) findViewById(R.id.txtHomeNo);
        Homeno.setRawInputType(Configuration.KEYBOARD_12KEY);
    }
}
