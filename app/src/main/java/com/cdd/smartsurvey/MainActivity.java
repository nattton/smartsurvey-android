package com.cdd.smartsurvey;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    String dbURL = "http://smartsurvey.ddns.me/SmartSurveyAPI_war_exploded/downloaddb/db";
    URL url;
    HttpURLConnection urlconnection;
    int FileSize;
    InputStream inputstream;
    OutputStream outputstream;
    byte dataArray[] = new byte[1024];
    long totalSize = 0;
    public RequestQueue MyRequestQueue;
    public String android_id;
    public String uid;
    public JSONObject jobj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        MyRequestQueue = Volley.newRequestQueue(this);
//        SendHistory(GlobalValue.dbUrl);
        CopyDBFromAssets();
        openLogin();
    }

    private synchronized void stopThread(Thread theThread) {
        if (theThread != null) {
            theThread = null;
        }
    }

    public void openLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void CopyDBFromAssets() {
        int count;
        try {
            File dataFolder = new File("/data/data/com.cdd.smartsurvey/databases");
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            inputstream = getApplicationContext().getAssets().open("smartsurvey_master.db");
            outputstream = new FileOutputStream("/data/data/com.cdd.smartsurvey/databases/smartsurvey_master.db");
            while ((count = inputstream.read(dataArray)) != -1) {

                outputstream.write(dataArray, 0, count);
            }

            outputstream.flush();
            outputstream.close();
            inputstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SendHistory(String aurl) {

        StringRequest LoginAPIRequest = new StringRequest(Request.Method.POST, aurl + "insertinfomobile", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jobj = new JSONObject(response);
                    uid = jobj.get("status").toString();
                    GlobalValue.uid = jobj.get("status").toString();
                    CheckDownloadDB(GlobalValue.dbUrl);
                } catch (JSONException e) {
                }

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("deviceid", android_id.toString()); //Add the data you'd like to send to the server
                return MyData;
            }
        };

        MyRequestQueue.add(LoginAPIRequest);
    }

    public void CheckDownloadDB(String aurl) {

        StringRequest LoginAPIRequest = new StringRequest(Request.Method.POST, aurl + "checkdownloaddb", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    jobj = new JSONObject(response);
                    if (jobj.get("status").toString().equals("0")) {
                        final Thread thread = new Thread() {

                            @Override
                            public void run() {
                                try {
                                    sleep(3000);
                                    stopThread(this);

                                    new DownloadDBWithProgressDialog().execute(dbURL);


                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        thread.start();
                    } else {
                        openLogin();
                    }

                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("deviceid", android_id.toString()); //Add the data you'd like to send to the server
                MyData.put("uid", uid.toString());
                return MyData;
            }
        };

        MyRequestQueue.add(LoginAPIRequest);

    }

    public class DownloadDBWithProgressDialog extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... aurl) {

            int count;

            try {

                File dataFolder = new File("/data/data/com.cdd.smartsurvey/databases");
                if (!dataFolder.exists()) {
                    dataFolder.mkdirs();
                }

                url = new URL(aurl[0]);
                urlconnection = (HttpURLConnection) url.openConnection();
                urlconnection.getResponseCode();


                FileSize = urlconnection.getContentLength();

                inputstream = urlconnection.getInputStream();
                outputstream = new FileOutputStream("/data/data/com.cdd.smartsurvey/databases/smartsurvey_master.db");


                while ((count = inputstream.read(dataArray)) != -1) {

                    totalSize += count;

                    publishProgress("" + (int) ((totalSize * 100) / FileSize));

                    outputstream.write(dataArray, 0, count);
                }

                outputstream.flush();
                outputstream.close();
                inputstream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String unused) {
            Toast.makeText(MainActivity.this, "DB Downloaded Successfully", Toast.LENGTH_LONG).show();
            openLogin();
        }
    }
}

