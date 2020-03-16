package com.cdd.smartsurvey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cdd.smartsurvey.utils.ImageUtil;
import com.kyanogen.signatureview.SignatureView;

public class AcceptSurveyActivity extends AppCompatActivity {

    SignatureView signatureView;
    Button btnBack;
    Button btnClear;
    Button btnStartSurvey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_survey);

        signatureView =  (SignatureView) findViewById(R.id.signature_view);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnClear = (Button) findViewById(R.id.btnClear);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });

        btnStartSurvey = (Button)findViewById(R.id.btnStartSurvey);
        btnStartSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                GlobalValue.signature = ImageUtil.convert(signatureView.getSignatureBitmap());
                openSmartSurvey();
            }
        });
    }

    public void openSmartSurvey() {
        Intent intent = new Intent(this,RegisterPagesNewFamilyActivity.class);
        startActivity(intent);
    }
}
