package com.cdd.smartsurvey;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.cdd.smartsurvey.utils.ImageUtil;

public class SurveySubMasterActivity extends AppCompatActivity {
    static final String EXTRA_SurveyGroupID = "EXTRA_SurveyGroupID";
    static final String EXTRA_MetricNo = "EXTRA_MetricNo";
    static final String EXTRA_MetricDisplay = "EXTRA_MetricDisplay";
    static final String EXTRA_Image = "EXTRA_Image";
    static final String EXTRA_MetricDesc = "EXTRA_MetricDesc";
    String SurveyGroupID;
    String MetricNo;
    String HeaderName;
    String HeaderImage;
    String DetailData;
    Button btnBack;
    Button btnDetail;
    LinearLayout LinearDetail;
    LinearLayout LinearHeader;
    LinearLayout LinearBody;
    ImageView imgIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_sub_master);

        Intent intent = getIntent();
        SurveyGroupID = intent.getStringExtra(EXTRA_SurveyGroupID);
        MetricNo = intent.getStringExtra(EXTRA_MetricNo);
        HeaderName  = intent.getStringExtra(EXTRA_MetricDisplay);
        HeaderImage = intent.getStringExtra(EXTRA_Image);
        DetailData  = intent.getStringExtra(EXTRA_MetricDesc);

        imgIcon = (ImageView) findViewById(R.id.imgIcon);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnDetail = (Button) findViewById(R.id.btnDetail);
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ShowDetail(HeaderName,DetailData);
            }
        });

        imgIcon.setImageBitmap(ImageUtil.convert(HeaderImage));
        LinearHeader = findViewById(R.id.linearheader);
        LinearBody = findViewById(R.id.linearmaster);
        selectQuestion();
    }

    private void selectQuestion() {
        LinearBody.addView(setHeaderFrameLayout(HeaderName));
        switch (SurveyGroupID) {
            case "1":
                if (MetricNo.equals("1")) LinearBody.addView(setSurvey1());
                else if (MetricNo.equals("2")) LinearBody.addView(setSurvey2());
                else if (MetricNo.equals("3")) LinearBody.addView(setSurvey3());
                else if (MetricNo.equals("4")) LinearBody.addView(setSurvey4());
                else if (MetricNo.equals("5")) LinearBody.addView(setSurvey5());
                else if (MetricNo.equals("6")) LinearBody.addView(setSurvey6());
                else if (MetricNo.equals("7")) LinearBody.addView(setSurvey7());
                break;
            case "2":
                if (MetricNo.equals("1")) LinearBody.addView(setSurvey8());
                else if (MetricNo.equals("2")) LinearBody.addView(setSurvey9());
                else if (MetricNo.equals("3")) LinearBody.addView(setSurvey10());
                else if (MetricNo.equals("4")) LinearBody.addView(setSurvey11());
                else if (MetricNo.equals("5")) LinearBody.addView(setSurvey12());
                else if (MetricNo.equals("6")) LinearBody.addView(setSurvey13());
                else if (MetricNo.equals("7")) LinearBody.addView(setSurvey14());
                break;
            case "3":
                if (MetricNo.equals("1")) LinearBody.addView(setSurvey15());
                else if (MetricNo.equals("2")) LinearBody.addView(setSurvey16());
                else if (MetricNo.equals("3")) LinearBody.addView(setSurvey10());
                else if (MetricNo.equals("4")) LinearBody.addView(setSurvey11());
                else if (MetricNo.equals("5")) LinearBody.addView(setSurvey12());
                else if (MetricNo.equals("6")) LinearBody.addView(setSurvey13());
                else if (MetricNo.equals("7")) LinearBody.addView(setSurvey14());
                else if (MetricNo.equals("8")) LinearBody.addView(setSurvey15());
                else if (MetricNo.equals("9")) LinearBody.addView(setSurvey16());
                else if (MetricNo.equals("10")) LinearBody.addView(setSurvey17());
                else if (MetricNo.equals("11")) LinearBody.addView(setSurvey18());
                else if (MetricNo.equals("12")) LinearBody.addView(setSurvey19());
                break;
            case "4":
                if (MetricNo.equals("1")) LinearBody.addView(setSurvey20());
                else if (MetricNo.equals("2")) LinearBody.addView(setSurvey21());
                else if (MetricNo.equals("3")) LinearBody.addView(setSurvey22());
                else if (MetricNo.equals("4")) LinearBody.addView(setSurvey23());
                break;
            case "5":
                if (MetricNo.equals("1")) LinearBody.addView(setSurvey24());
                else if (MetricNo.equals("2")) LinearBody.addView(setSurvey25());
                else if (MetricNo.equals("3")) LinearBody.addView(setSurvey26());
                else if (MetricNo.equals("4")) LinearBody.addView(setSurvey27());
                else if (MetricNo.equals("5")) LinearBody.addView(setSurvey28());
                else if (MetricNo.equals("6")) LinearBody.addView(setSurvey29());
                else if (MetricNo.equals("7")) LinearBody.addView(setSurvey30());
                else if (MetricNo.equals("8")) LinearBody.addView(setSurvey31());
                break;
        }
    }

    public View setSurvey1() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey1, null);
        RadioGroup radioGroup1 = viewSurvey.findViewById(R.id.radioGroup1);
        final View question2 = viewSurvey.findViewById(R.id.question2);
        final RadioGroup radioGroup2 = viewSurvey.findViewById(R.id.radioGroup2);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio1_1) {
                    question2.setVisibility(View.VISIBLE);
                } else {
                    question2.setVisibility(View.GONE);
                    radioGroup2.clearCheck();
                }
            }
        });
        return  viewSurvey;
    }

    public View setSurvey2() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey2, null);
        RadioGroup radioGroup1 = viewSurvey.findViewById(R.id.radioGroup1);
        final View question2 = viewSurvey.findViewById(R.id.question2);
        final RadioGroup radioGroup2 = viewSurvey.findViewById(R.id.radioGroup2);
        final View question3 = viewSurvey.findViewById(R.id.question3);
        final RadioGroup radioGroup3 = viewSurvey.findViewById(R.id.radioGroup2);
        final View question4 = viewSurvey.findViewById(R.id.question4);
        final RadioGroup radioGroup4 = viewSurvey.findViewById(R.id.radioGroup4);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio1_1) {
                    question2.setVisibility(View.VISIBLE);
                } else {
                    question2.setVisibility(View.GONE);
                    radioGroup2.clearCheck();

                    question3.setVisibility(View.VISIBLE);
                }
            }
        });

        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio3_1) {
                    question4.setVisibility(View.GONE);
                    radioGroup4.clearCheck();
                } else {
                    question4.setVisibility(View.VISIBLE);
                }
            }
        });

        return  viewSurvey;
    }

    public View setSurvey3() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey3, null);
        RadioGroup radioGroup1 = viewSurvey.findViewById(R.id.radioGroup1);
        final View question2 = viewSurvey.findViewById(R.id.question2);
        final RadioGroup radioGroup2 = viewSurvey.findViewById(R.id.radioGroup2);
        final View question3 = viewSurvey.findViewById(R.id.question3);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio1_1) {
                    question2.setVisibility(View.VISIBLE);
                } else {
                    question2.setVisibility(View.GONE);
                    radioGroup2.clearCheck();
                    question3.setVisibility(View.GONE);
                }
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio2_1) {

                } else {
                    question3.setVisibility(View.VISIBLE);
                }
            }
        });

        return  viewSurvey;
    }

    public View setSurvey4() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey4, null);

        return  viewSurvey;
    }

    public View setSurvey5() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey5, null);

        return  viewSurvey;
    }

    public View setSurvey6() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey6, null);

        return  viewSurvey;
    }

    public View setSurvey7() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey7, null);

        return  viewSurvey;
    }

    public View setSurvey8() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey8, null);

        return  viewSurvey;
    }

    public View setSurvey9() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey9, null);

        return  viewSurvey;
    }

    public View setSurvey10() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey10, null);

        return  viewSurvey;
    }

    public View setSurvey11() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey11, null);

        return  viewSurvey;
    }

    public View setSurvey12() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey12, null);

        return  viewSurvey;
    }

    public View setSurvey13() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey13, null);

        return  viewSurvey;
    }

    public View setSurvey14() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey14, null);

        return  viewSurvey;
    }

    public View setSurvey15() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey15, null);

        return  viewSurvey;
    }

    public View setSurvey16() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey16, null);

        return  viewSurvey;
    }

    public View setSurvey17() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey17, null);

        return  viewSurvey;
    }

    public View setSurvey18() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey18, null);

        return  viewSurvey;
    }

    public View setSurvey19() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey19, null);

        return  viewSurvey;
    }

    public View setSurvey20() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey20, null);

        return  viewSurvey;
    }

    public View setSurvey21() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey21, null);

        return  viewSurvey;
    }

    public View setSurvey22() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey22, null);

        return  viewSurvey;
    }

    public View setSurvey23() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey23, null);

        return  viewSurvey;
    }

    public View setSurvey24() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey24, null);

        return viewSurvey;
    }

    public View setSurvey25() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey25, null);

        return viewSurvey;
    }

    public View setSurvey26() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey26, null);

        return viewSurvey;
    }

    public View setSurvey27() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey27, null);

        return viewSurvey;
    }

    public View setSurvey28() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey28, null);

        return viewSurvey;
    }

    public View setSurvey29() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey29, null);

        return viewSurvey;
    }

    public View setSurvey30() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey30, null);

        return viewSurvey;
    }

    public View setSurvey31() {
        View viewSurvey = getLayoutInflater().inflate(R.layout.survey31, null);

        return viewSurvey;
    }

    public TextView setHeaderTextView(String Value){
        TextView TextMaster = new TextView(getApplicationContext());
        ConstraintLayout.LayoutParams paramsText = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        TextMaster.setLayoutParams(paramsText);
        TextMaster.setTypeface(ResourcesCompat.getFont(getApplicationContext(),R.font.thsarabun_bold));
        TextMaster.setGravity(Gravity.CENTER_VERTICAL);
        TextMaster.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary));
        TextMaster.setText(Value);
        TextMaster.setTextSize(22);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) TextMaster.getLayoutParams();
        params.setMargins((int) (20 * getResources().getSystem().getDisplayMetrics().density),
                (int) (20 * getResources().getSystem().getDisplayMetrics().density),
                (int) (20 * getResources().getSystem().getDisplayMetrics().density),0);

        TextMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        return TextMaster;
    }

    public FrameLayout setHeaderFrameLayout(String Value){
        FrameLayout FrameMaster = new FrameLayout(getApplicationContext());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        FrameMaster.setLayoutParams(lp);
        FrameMaster.setBackground(getResources().getDrawable(R.drawable.bordergroup));
        FrameMaster.addView(setHeaderTextView(Value));
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) FrameMaster.getLayoutParams();
        params.setMargins(10,0, 10,0);
        FrameMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        return  FrameMaster;
    }

    public FrameLayout setQuestionFrameLayout(String Value, String choic){
        FrameLayout FrameMaster = new FrameLayout(getApplicationContext());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        FrameMaster.setLayoutParams(lp);
        FrameMaster.setBackground(getResources().getDrawable(R.drawable.bordergroup));
        FrameMaster.addView(setHeaderTextView(Value));
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) FrameMaster.getLayoutParams();
        params.setMargins(10,0, 10,0);
        FrameMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        return  FrameMaster;
    }

    public void ShowDetail(String Header,String Detail)
    {
        TextView HeaderDetail;
        TextView ValueDetail;

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(SurveySubMasterActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_detail_survey_popup, null);

        HeaderDetail = (TextView) mView.findViewById(R.id.txtDetailHeader);
        HeaderDetail.setText(Header);

        ValueDetail = (TextView) mView.findViewById(R.id.txtDetailValue);
        ValueDetail.setText(Detail);

        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();

        LinearDetail = (LinearLayout) mView.findViewById(R.id.lienarDetail);
        LinearDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                show.dismiss();
            }
        });

    }
}
