package com.cdd.smartsurvey;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MenuSurveyActivity extends AppCompatActivity {

    public Button btnAddress;
    public Button btnLocalGov;
    public Button btnAreaLeave;
    public Button btnAreaCareer;
    public Button btnPictureHome;
    public Button btnClose;
    public Button btnBack;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_survey);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnAddress = (Button) findViewById(R.id.btnAddress);
        btnAddress.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                ShowAddress();
            }
        });

        btnLocalGov = (Button) findViewById(R.id.btnLocalGov);
        btnLocalGov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowLocalGov();
            }
        });

        btnAreaLeave = (Button) findViewById(R.id.btnAreaLeave);
        btnAreaLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAreaLeave();
            }
        });

        btnAreaCareer = (Button) findViewById(R.id.btnAreaCareer);
        btnAreaCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAreaCareer();
            }
        });

        btnPictureHome = (Button) findViewById(R.id.btnPictureHome);
        btnPictureHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPictureHome();
            }
        });

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void ShowAddress()
    {

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MenuSurveyActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_address_survey, null);

        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();



        btnClose = (Button) mView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                show.dismiss();
            }
        });

    }

    public void ShowLocalGov()
    {

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MenuSurveyActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_localgov_survey, null);

        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();

        btnClose = (Button) mView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                show.dismiss();
            }
        });

    }

    public void ShowAreaLeave()
    {
        RadioButton rdMyself;
        RadioButton rdRent;
        RadioButton rdPublic;
        RadioButton rdOther;

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MenuSurveyActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_area_leave_survey, null);

        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();

        rdMyself = (RadioButton) mView.findViewById(R.id.radioButton1);
        rdMyself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowRadioMyself();
            }
        });

        rdRent = (RadioButton) mView.findViewById(R.id.radioButton2);
        rdRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowRadioAreaData("เช่า");
            }
        });

        rdPublic = (RadioButton) mView.findViewById(R.id.radioButton3);
        rdPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowRadioAreaData("ที่สาธารณะ");
            }
        });

        rdOther = (RadioButton) mView.findViewById(R.id.radioButton4);
        rdOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowRadioAreaOther();
            }
        });

        btnClose = (Button) mView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                show.dismiss();
            }
        });

    }

    public void ShowAreaCareer()
    {

        RadioButton rdMyself;
        RadioButton rdRent;
        RadioButton rdPublic;
        RadioButton rdOther;

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MenuSurveyActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_area_career_survey, null);

        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();

        rdMyself = (RadioButton) mView.findViewById(R.id.radioButton1);
        rdMyself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowRadioMyself();
            }
        });

        rdRent = (RadioButton) mView.findViewById(R.id.radioButton2);
        rdRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowRadioAreaData("เช่า");
            }
        });

        rdPublic = (RadioButton) mView.findViewById(R.id.radioButton3);
        rdPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowRadioAreaData("ที่สาธารณะ");
            }
        });

        rdOther = (RadioButton) mView.findViewById(R.id.radioButton4);
        rdOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowRadioAreaOther();
            }
        });

        btnClose = (Button) mView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                show.dismiss();
            }
        });
    }

    public void ShowRadioMyself()
    {

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MenuSurveyActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_area_myself_survey, null);

        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();


        btnClose = (Button) mView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                show.dismiss();
            }
        });
    }

    public void ShowRadioAreaData(String HeaderData)
    {
        TextView HeaderView;

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MenuSurveyActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_area_data_survey, null);

        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();

        HeaderView = (TextView) mView.findViewById(R.id.txtDialogVies);
        HeaderView.setText(HeaderData);


        btnClose = (Button) mView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                show.dismiss();
            }
        });
    }

    public void ShowRadioAreaOther()
    {
        TextView HeaderView;

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MenuSurveyActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_area_other_survey, null);

        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();


        btnClose = (Button) mView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                show.dismiss();
            }
        });
    }

    public void ShowPictureHome()
    {
        Intent intent = new Intent(this,CaptureHomeSurveyActivity.class);
        startActivity(intent);
    }
}
