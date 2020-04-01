package com.cdd.smartsurvey;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.cdd.smartsurvey.sqlite.DatabaseHelper;
import com.cdd.smartsurvey.sqlite.model.SurveyMetric;
import com.cdd.smartsurvey.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class HeaderSurveyMasterActivity extends AppCompatActivity {

    public String taggroup;
    public String imgicon;
    public String headervalue;
    public String metricvalue;
    public SurveyMetric item;

    public ImageView imgIcon;
    public TextView HeaderValue;
    public TextView MetricValue;
    public Button btnBack;
    public LinearLayout LinearMaster;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_metric_master);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        taggroup = intent.getStringExtra("taggroup");
        imgicon = intent.getStringExtra("imgicon");
        headervalue = intent.getStringExtra("headervalue");
        metricvalue = intent.getStringExtra("metricvalue");

        imgIcon = (ImageView) findViewById(R.id.imgIcon);
        HeaderValue = (TextView) findViewById(R.id.txtHeaderValue);
        MetricValue = (TextView) findViewById(R.id.txtMetricValue);
        LinearMaster = (LinearLayout) findViewById(R.id.linearBody);

        imgIcon.setImageBitmap(ImageUtil.convert(imgicon));
        HeaderValue.setText(headervalue);
        MetricValue.setText("ประกอบด้วย " + metricvalue);

        DatabaseHelper db;

        final List<SurveyMetric> surveyMetricsList = new ArrayList<>();
        db = new DatabaseHelper(this);
        surveyMetricsList.addAll(db.getAllSurveyMetrics(taggroup));

        for (int i = 0; i < surveyMetricsList.size(); i++) {
            item = surveyMetricsList.get(i);
            LinearMaster.addView(setFrameLayout(item.getTbl_survey_group_masterid(), item.getMetric_no(), item.getMetric_display(), imgicon, item.getMetric_description()));
        }
    }

    public TextView setTextView(final String surveyGroupID, final String metricNo, final String metricDisplay, final String intValue, final String metricDesc) {

        TextView TextMaster = new TextView(getApplicationContext());
        ConstraintLayout.LayoutParams paramsText = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        TextMaster.setLayoutParams(paramsText);
        TextMaster.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.thsarabun_bold));
        TextMaster.setGravity(Gravity.CENTER_VERTICAL);
        TextMaster.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        TextMaster.setText(metricDisplay);
        TextMaster.setTextSize(20);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) TextMaster.getLayoutParams();
        params.setMargins((int) (20 * getResources().getSystem().getDisplayMetrics().density),
                (int) (20 * getResources().getSystem().getDisplayMetrics().density),
                (int) (20 * getResources().getSystem().getDisplayMetrics().density), 0);

        TextMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SurveySubMasterActivity.class);
                intent.putExtra(SurveySubMasterActivity.EXTRA_SurveyGroupID, surveyGroupID);
                intent.putExtra(SurveySubMasterActivity.EXTRA_MetricNo, metricNo);
                intent.putExtra(SurveySubMasterActivity.EXTRA_MetricDisplay, metricDisplay);
                intent.putExtra(SurveySubMasterActivity.EXTRA_Image, intValue);
                intent.putExtra(SurveySubMasterActivity.EXTRA_MetricDesc, metricDesc);
                startActivity(intent);
            }
        });
        return TextMaster;
    }

    public FrameLayout setFrameLayout(final String surveyGroupID, final String metricNo, final String metricDisplay, final String intValue, final String metricDesc) {

        FrameLayout FrameMaster = new FrameLayout(getApplicationContext());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        FrameMaster.setLayoutParams(lp);
        FrameMaster.setBackground(getResources().getDrawable(R.drawable.bordergroup));
        FrameMaster.addView(setTextView(surveyGroupID, metricNo, metricDisplay, intValue, metricDesc));
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) FrameMaster.getLayoutParams();
        params.setMargins((int) (10 * getResources().getSystem().getDisplayMetrics().density), 0,
                (int) (10 * getResources().getSystem().getDisplayMetrics().density), 0);
        FrameMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SurveySubMasterActivity.class);
                intent.putExtra(SurveySubMasterActivity.EXTRA_SurveyGroupID, surveyGroupID);
                intent.putExtra(SurveySubMasterActivity.EXTRA_MetricNo, metricNo);
                intent.putExtra(SurveySubMasterActivity.EXTRA_MetricDisplay, metricDisplay);
                intent.putExtra(SurveySubMasterActivity.EXTRA_Image, intValue);
                intent.putExtra(SurveySubMasterActivity.EXTRA_MetricDesc, metricDesc);
                startActivity(intent);
            }
        });
        return FrameMaster;
    }
}
