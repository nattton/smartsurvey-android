package com.cdd.smartsurvey;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class LoginActivity extends AppCompatActivity {

    public EditText uname;
    public EditText upassword;
    public String   returnOut;
    public RequestQueue MyRequestQueue;
    public JSONObject jobj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        Button btnLogin = findViewById(R.id.custom_button);
        uname = findViewById(R.id.username);
        upassword = findViewById(R.id.password);

        MyRequestQueue = Volley.newRequestQueue(this);

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                CheckLogin(GlobalValue.dbUrl);
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView txtNetWorkStatus = (TextView) findViewById(R.id.txtNetworkStatus);
                        if (isNetworkAvailable() == true) {
                            txtNetWorkStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.networkon, 0, 0, 0);
                            txtNetWorkStatus.setTextColor(getResources().getColor(R.color.colorNetworkOn));
                            txtNetWorkStatus.setText("  Online / ออนไลน์");
                        } else {
                            txtNetWorkStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.networkoff, 0, 0, 0);
                            txtNetWorkStatus.setTextColor(getResources().getColor(R.color.colorNetworkOff));
                            txtNetWorkStatus.setText("  Offline / ออฟไลน์");
                        }
                    }
                });
            }
        }, 0, 3000);
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void openMainMenu() {
        Intent intent = new Intent(this, FirstMenuActivity.class);
        startActivity(intent);
    }

    public void openRegister() {
        Intent intent = new Intent(this,RegisterPagesActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder ab = new AlertDialog.Builder(LoginActivity.this,R.style.AlertDialogTheme);
        ab.setTitle("แจ้งเตือน");
        ab.setMessage("ท่านต้องการออกจากโปรแกรมนี้หรือไม่ ?");
        ab.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        ab.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        ab.show();
    }

    @Override
    public void onDestroy()
    {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }

    public void CheckLogin (String aurl) {

            StringRequest LoginAPIRequest = new StringRequest(Request.Method.POST, aurl+"checklogin", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        jobj = new JSONObject(response);
                    if (jobj.get("status").toString().equals("0"))
                    {
                        AlertDialog.Builder ab = new AlertDialog.Builder(LoginActivity.this,R.style.AlertDialogTheme);
                        ab.setTitle("แจ้งเตือน");
                        ab.setMessage("ไม่พบ Username / Password ไม่ถูกต้อง");
                        ab.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        ab.show();
                    }
                    else
                    {
                        GlobalValue.loginid = jobj.get("status").toString();
                        CheckUser(GlobalValue.dbUrl,jobj.get("status").toString());
                    }

                    } catch (JSONException e) {
                    }
                }
            }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                @Override
                public void onErrorResponse(VolleyError error) {
                    returnOut = error.toString();
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> MyData = new HashMap<String, String>();
                    MyData.put("username",uname.getText().toString()); //Add the data you'd like to send to the server
                    MyData.put("password",upassword.getText().toString());
                    MyData.put("uid",GlobalValue.uid);
                    return MyData;
                }
            };

            MyRequestQueue.add(LoginAPIRequest);
        }

    public void CheckUser (String aurl,String uiddata) {
        final String uid = uiddata;
        StringRequest LoginAPIRequest = new StringRequest(Request.Method.POST, aurl+"checkuser", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jobj = new JSONObject(response);
                    if (jobj.get("status").toString().equals("0"))
                    {
                        openRegister();
                    }
                    else
                    {
                        GlobalValue.qestionerid = jobj.get("status").toString();
                        openMainMenu();
                    }

                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                returnOut = error.toString();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("uid",uid.toString()); //Add the data you'd like to send to the server
                return MyData;
            }
        };

        MyRequestQueue.add(LoginAPIRequest);
    }
}
