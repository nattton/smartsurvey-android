package com.cdd.smartsurvey;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cdd.smartsurvey.utils.CheckAccuracy;

public class FirstPagesActivity extends AppCompatActivity {

    public Button btnProfile;
    public Button btnNewfamilty;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpages);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnProfile = (Button) findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                openRegister();
            }
        });

        btnNewfamilty = (Button) findViewById(R.id.btnNewFamily);
        btnNewfamilty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewFamily();
            }
        });

    }

    public void openRegister() {
        Intent intent = new Intent(this,RegisterPagesActivity.class);
        startActivity(intent);
    }

    public void openNewFamily() {
        Intent intent = new Intent(FirstPagesActivity.this,AcceptSurveyActivity.class);
        startActivity(intent);
//      showFirstDialog(this);

    }

    private void showFirstDialog(Context c) {
        Button btnAcceptHomeNo;
        final EditText txtHomeno;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(FirstPagesActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_homeno,null);
        mBuilder.setView(mView);

        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        txtHomeno = (EditText) mView.findViewById(R.id.txtHomeNo);
        txtHomeno.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (!CheckAccuracy.CheckIDCard(s))
                    {
                        AlertDialog.Builder ab = new AlertDialog.Builder(FirstPagesActivity.this, R.style.AlertDialogTheme);
                        ab.setTitle("แจ้งเตือน");
                        ab.setMessage("ข้อมูลเลขที่บ้านไม่ถูกต้อง");
                        ab.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        ab.show();
                    }
                }
                catch (Exception e){
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnAcceptHomeNo = (Button) mView.findViewById(R.id.btnAccept);
        btnAcceptHomeNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                GlobalValue.homeno = txtHomeno.getText().toString();
                dialog.dismiss();
                Intent intent = new Intent(FirstPagesActivity.this,AcceptSurveyActivity.class);
                startActivity(intent);
            }
        });
    }

}
