package com.cdd.smartsurvey;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class MemberSurveyActivity extends AppCompatActivity {

    public GridLayout grdLayout;
    public LinearLayout LinearDetial;
    public Button btnSave;
    public Button btnAdd;
    public Button btnDetail;
    public Button btnClose;
    public Button btnDetailMember1;
    public Button btnDetailMember2;
    public Button btnDetailMember3;
    public Button btnDetailMember4;
    public Button btnDetailMember5;
    public RadioButton rdPeopleUse;
    public CheckBox checkBox6;

    @SuppressLint({"SourceLockedOrientationActivity", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_survey);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnDetail = (Button) findViewById(R.id.btnDetail);
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowDetailMenu();
            }
        });

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                SendData(GlobalValue.dbUrl);
            }
        });

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AddMember();
            }
        });


        grdLayout = (GridLayout) findViewById(R.id.grdLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins((int) (20 * getResources().getSystem().getDisplayMetrics().density),0,0,0);

       /* for (int i=1;i <= 50;i++){
            TextView valueItemData = new TextView(this);
            valueItemData.setTypeface(ResourcesCompat.getFont(getApplicationContext(),R.font.thsarabun_bold));
            valueItemData.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
            valueItemData.setId(1 + i);
            valueItemData.setText("2");
            valueItemData.setLayoutParams(params);

            TextView valueItemData1 = new TextView(this);
            valueItemData1.setTypeface(ResourcesCompat.getFont(getApplicationContext(),R.font.thsarabun_bold));
            valueItemData1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
            valueItemData1.setId(2 + i);
            valueItemData1.setText("นายธัชชัย  พรเนรมิต");
            valueItemData1.setLayoutParams(params);

            TextView valueItemData2 = new TextView(this);
            valueItemData2.setTypeface(ResourcesCompat.getFont(getApplicationContext(),R.font.thsarabun_bold));
            valueItemData2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
            valueItemData2.setId(3 + i);
            valueItemData2.setText("3101200029694");
            valueItemData2.setLayoutParams(params);

            grdLayout.addView(valueItemData);
            grdLayout.addView(valueItemData1);
            grdLayout.addView(valueItemData2);
        }*/
    }

    public void SendData (String aurl) {
            Intent intent = new Intent(this,StartSurveyAcitivity.class);
            startActivity(intent);
        }

    public void AddMember()
    {
        TextView HeaderView;

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MemberSurveyActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_aemember_survey, null);

        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();

        rdPeopleUse = (RadioButton) mView.findViewById(R.id.rdPeopleUse);
        rdPeopleUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowPeopleCard();
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

    public void ShowDetailMenu()
    {

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MemberSurveyActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_detail_member_menu_survey, null);


        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();

        btnDetailMember1 = (Button) mView.findViewById(R.id.btnDetailMember1);
        btnDetailMember1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowDetail(btnDetailMember1.getText().toString(),GlobalValue.DetailMember1);
            }
        });

        btnDetailMember2 = (Button) mView.findViewById(R.id.btnDetailMember2);
        btnDetailMember2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowDetail(btnDetailMember2.getText().toString(),GlobalValue.DetailMember2);
            }
        });
        btnDetailMember3 = (Button) mView.findViewById(R.id.btnDetailMember3);
        btnDetailMember3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowDetail(btnDetailMember3.getText().toString(),GlobalValue.DetailMember3);
            }
        });
        btnDetailMember4 = (Button) mView.findViewById(R.id.btnDetailMember4);
        btnDetailMember4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowDetail(btnDetailMember4.getText().toString(),GlobalValue.DetailMember4);
            }
        });
        btnDetailMember5 = (Button) mView.findViewById(R.id.btnDetailMember5);
        btnDetailMember5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowDetail(btnDetailMember5.getText().toString(),GlobalValue.DetailMember5);
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

    public void ShowDetail(String Header,String Detail)
    {
        TextView HeaderDetail;
        TextView ValueDetail;

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MemberSurveyActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_detail_survey_popup, null);

        HeaderDetail = (TextView) mView.findViewById(R.id.txtDetailHeader);
        HeaderDetail.setText(Header);

        ValueDetail = (TextView) mView.findViewById(R.id.txtDetailValue);
        ValueDetail.setText(Detail);

        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();

        LinearDetial = (LinearLayout) mView.findViewById(R.id.lienarDetail);
        LinearDetial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                show.dismiss();
            }
        });
    }

    public void ShowPeopleCard()
    {

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MemberSurveyActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_people_card_survey, null);


        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();

        checkBox6 = (CheckBox) mView.findViewById(R.id.checkBox6);
        checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ShowPeopleCardDetail();
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

    public void ShowPeopleCardDetail()
    {

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MemberSurveyActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_people_card_detail_survey, null);

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