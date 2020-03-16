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

public class AreaCareerSurveyActivity extends AppCompatActivity {

    public Button btnClose;
    public Button btnSave;
    public Button btnBack;
    public RadioButton rdMyself;
    public RadioButton rdRent;
    public RadioButton rdPublic;
    public RadioButton rdOther;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_career_survey);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                SendData(GlobalValue.dbUrl);
            }
        });

        rdMyself = (RadioButton) findViewById(R.id.radioButton1);
        rdMyself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowRadioMyself();
            }
        });

        rdRent = (RadioButton) findViewById(R.id.radioButton2);
        rdRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowRadioAreaData("เช่า");
            }
        });

        rdPublic = (RadioButton) findViewById(R.id.radioButton3);
        rdPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowRadioAreaData("ที่สาธารณะ");
            }
        });

        rdOther = (RadioButton) findViewById(R.id.radioButton4);
        rdOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowRadioAreaOther();
            }
        });

    }

    public void SendData (String aurl) {
        Intent intent = new Intent(this, MemberSurveyActivity.class);
        startActivity(intent);    }


    public void ShowRadioAreaOther()
    {
        TextView HeaderView;

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(AreaCareerSurveyActivity.this);
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

    public void ShowRadioAreaData(String HeaderData)
    {
        TextView HeaderView;

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(AreaCareerSurveyActivity.this);
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

    public void ShowRadioMyself()
    {

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(AreaCareerSurveyActivity.this);
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
}
