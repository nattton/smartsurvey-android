package com.cdd.smartsurvey;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.cdd.smartsurvey.sqlite.DatabaseHelper;
import com.cdd.smartsurvey.sqlite.model.SurveyGroup;
import com.cdd.smartsurvey.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

public class StartSurveyAcitivity extends AppCompatActivity {

    public LinearLayout linearMain;
    public Button btnHealth;
    public Button btnEnvironment;
    public Button btnEducation;
    public Button btnPayment;
    public Button btnPopular;
    public Button btnOther;
    public Button btnBack;
    public FrameLayout  frameLayout;
    public ImageView    imageViewIcon;
    public TextView     txtdisplay;
    public Button       buttonData;
    public ImageView    imageViewProgress;
    public TextView     txtProgress;
    public SurveyGroup  item;
    public CharSequence sc;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_survey);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        linearMain = (LinearLayout) findViewById(R.id.LinearMain);

        DatabaseHelper db;

        final List<SurveyGroup> surveyGroupsList = new ArrayList<>();
        db = new DatabaseHelper(this);
        surveyGroupsList.addAll(db.getAllSurveyGroups());

        for(int i=0;i<surveyGroupsList.size();i++) {
            item = surveyGroupsList.get(i);
            frameLayout = new FrameLayout(this);
            FrameLayout.LayoutParams frame_params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            frameLayout.setLayoutParams(frame_params);
            frameLayout.setBackgroundResource(R.drawable.bordergroup);

            imageViewIcon = new ImageView(this);
            FrameLayout.LayoutParams imageicon_params = new FrameLayout.LayoutParams(200,
                    200);
            imageicon_params.gravity =  Gravity.CENTER_VERTICAL|Gravity.TOP;
            imageicon_params.setMargins((int) (21 * getResources().getSystem().getDisplayMetrics().density),
                    (int) (21 * getResources().getSystem().getDisplayMetrics().density),0,0);
            imageViewIcon.setLayoutParams(imageicon_params);
            imageViewIcon.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewIcon.setImageBitmap(ImageUtil.convert(item.getGroupImage()));

            txtdisplay = new TextView(this);
            FrameLayout.LayoutParams txtdisplay_params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            txtdisplay_params.gravity =  Gravity.LEFT|Gravity.TOP;
            txtdisplay_params.setMargins((int) (100 * getResources().getSystem().getDisplayMetrics().density),
                    (int) (10 * getResources().getSystem().getDisplayMetrics().density),0,0);
            txtdisplay.setLayoutParams(txtdisplay_params);
            txtdisplay.setTypeface(ResourcesCompat.getFont(getApplicationContext(),R.font.thsarabun_bold));
            txtdisplay.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
            txtdisplay.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtdisplay.setText(item.getGroupDisplay());


            buttonData = new Button(this);
            FrameLayout.LayoutParams buttondata_params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            buttondata_params.gravity =  Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
            buttondata_params.setMargins(0, 0,0,(int) (20 * getResources().getSystem().getDisplayMetrics().density));
            buttonData.setLayoutParams(buttondata_params);
            buttonData.setTypeface(ResourcesCompat.getFont(getApplicationContext(),R.font.thsarabun_bold));
            buttonData.setText("เริ่มสำรวจ");
            buttonData.setTextColor(Color.parseColor("#FFFFFF"));
            buttonData.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
            buttonData.setBackgroundResource(R.drawable.custom_menubutton);
            buttonData.setId(item.getId());
            sc = String.valueOf(item.getId()+ "SMARTSURVEY" + item.getGroupImage() + "SMARTSURVEY" + item.getGroupName() + "SMARTSURVEY" + item.getGroupMetric());
            buttonData.setContentDescription(sc);
            buttonData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openHeaderSurvey(v.getContentDescription());
                }
            });

            imageViewProgress = new ImageView(this);
            FrameLayout.LayoutParams imageViewProgress_params = new FrameLayout.LayoutParams
                    ((int) (40 * getResources().getSystem().getDisplayMetrics().density),
                    FrameLayout.LayoutParams.MATCH_PARENT);
            imageViewProgress_params.gravity =  Gravity.END;
            imageViewProgress_params.setMargins(0,0,(int) (20 * getResources().getSystem().getDisplayMetrics().density),
                    (int) (6 * getResources().getSystem().getDisplayMetrics().density));
            imageViewProgress.setLayoutParams(imageViewProgress_params);
            imageViewProgress.setAdjustViewBounds(true);
            imageViewProgress.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageViewProgress.setImageResource(R.drawable.custom_cicle_progress);

            txtProgress = new TextView(this);
            FrameLayout.LayoutParams txtProgress_params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            txtProgress_params.gravity =  Gravity.RIGHT;
            txtProgress_params.setMargins(0,(int) (40 * getResources().getSystem().getDisplayMetrics().density),
                    (int) (32 * getResources().getSystem().getDisplayMetrics().density),0);
            txtProgress.setLayoutParams(txtProgress_params);
            txtProgress.setTypeface(ResourcesCompat.getFont(getApplicationContext(),R.font.thsarabun_bold));
            txtProgress.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            txtProgress.setTextColor(Color.parseColor("#FFFFFF"));
            txtProgress.setText("0%");

            frameLayout.addView(imageViewIcon);
            frameLayout.addView(txtdisplay);
            frameLayout.addView(buttonData);
            frameLayout.addView(imageViewProgress);
            frameLayout.addView(txtProgress);
            linearMain.addView(frameLayout);
        }

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void openHeaderSurvey(CharSequence sc) {
        String scdata = sc.toString();
        String[] scarray = scdata.split("SMARTSURVEY");
        Intent intent = new Intent(this, HeaderSurveyMasterActivity.class);
        intent.putExtra("taggroup",scarray[0]);
        intent.putExtra("imgicon", scarray[1]);
        intent.putExtra("headervalue", scarray[2]);
        intent.putExtra("metricvalue", scarray[3]);
        startActivity(intent);
    }
}
