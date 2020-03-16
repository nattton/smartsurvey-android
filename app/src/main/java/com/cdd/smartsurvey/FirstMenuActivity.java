package com.cdd.smartsurvey;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FirstMenuActivity extends AppCompatActivity {

    public Button btnMenuSurvey;
    public Button btnMenuTumbon;
    public Button btnEcard;
    public Button btnProfile;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstmenu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnProfile = (Button) findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                openRegister();
            }
        });

        btnMenuSurvey = (Button) findViewById(R.id.btnSurveyMenu);
        btnMenuSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSurvey();
            }
        });

        btnMenuTumbon = (Button) findViewById(R.id.btnTumbonMenu);
        btnMenuTumbon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTumbon();
            }
        });

        btnEcard = (Button) findViewById(R.id.btnECard);
        btnEcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEcard();
            }
        });

    }

    public void openRegister() {
        Intent intent = new Intent(this,RegisterPagesActivity.class);
        startActivity(intent);
    }

    public void openSurvey() {
        Intent intent = new Intent(this,FirstPagesActivity.class);
        startActivity(intent);
    }

    public void openTumbon() {
        Intent intent = new Intent(this,RegisterPagesNewFamilyActivity.class);
        startActivity(intent);
    }

    public void openEcard() {
        Intent intent = new Intent(this,ECardActivity.class);
        startActivity(intent);
    }

}
