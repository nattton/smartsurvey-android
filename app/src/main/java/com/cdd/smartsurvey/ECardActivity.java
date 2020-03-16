package com.cdd.smartsurvey;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cdd.smartsurvey.utils.ImageUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ECardActivity extends AppCompatActivity {

    private ImageView barcodeEcard;
    private ImageView barcodeWaterMark;
    private ImageView imgcard;
    private int size = 2000;
    private int size_width = 2000;
    private int size_height = 264;
    private Bitmap eCardBitmap;
    private Bitmap WaterMarkBitmap;
    public Button btnBackToMenu;
    public JSONObject jobj;
    public RequestQueue MyRequestQueue;

    public TextView CardID;
    public TextView txtName;
    public TextView txtAddress1;
    public TextView txtAddress2;
    public TextView txtQuestionerType;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecard);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        barcodeEcard = (ImageView) findViewById(R.id.imgBarcode);
        barcodeWaterMark = (ImageView) findViewById(R.id.imageWaterMark);
        imgcard = (ImageView) findViewById(R.id.imgCard);
        MyRequestQueue = Volley.newRequestQueue(this);

        CardID = (TextView) findViewById(R.id.txtCardID);
        txtName = (TextView) findViewById(R.id.txtName);
        txtAddress1 = (TextView) findViewById(R.id.txtAddress1);
        txtAddress2 = (TextView) findViewById(R.id.txtAddress2);
        txtQuestionerType = (TextView) findViewById(R.id.txtQuestionerType);

        btnBackToMenu = (Button) findViewById(R.id.btnBackToMenu);
        btnBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bitmap bitmap = null;
        Bitmap waterbitmap = null;
        try
        {
            bitmap = CreateBarcode("0945624335", "QR Code");
            eCardBitmap = bitmap;

            BitmapDrawable drawable = (BitmapDrawable) imgcard.getDrawable();
            Bitmap bitmapw = drawable.getBitmap();
        }
        catch (WriterException we)
        {
            we.printStackTrace();
        }
        if (bitmap != null)
        {
            barcodeEcard.setImageBitmap(bitmap);
        }

        ReceiveData(GlobalValue.dbUrl);
    }


    public Bitmap CreateBarcode(String message, String type) throws WriterException
    {
        BitMatrix bitMatrix = null;
        // BitMatrix bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);
        switch (type)
        {
            case "QR Code":bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);break;
            default: bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size);break;
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int [] pixels = new int [width * height];
        for (int i = 0 ; i < height ; i++)
        {
            for (int j = 0 ; j < width ; j++)
            {
                if (bitMatrix.get(j, i))
                {
                    pixels[i * width + j] = 0xff000000;
                }
                else
                {
                    pixels[i * width + j] = 0xffffffff;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public void ReceiveData (String aurl) {

        String subpath = "";

        if (!GlobalValue.qestionerid.equals("0")) {

            subpath = "questioner/profile/select";

        }

        StringRequest LoginAPIRequest = new StringRequest(Request.Method.POST, aurl + subpath, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jobj = new JSONObject(response);
                    JSONArray  array = jobj.getJSONArray("TransQuestionerProfile");
                    JSONObject jdata = array.getJSONObject(0);
                    CardID.setText(jdata.get("idcard").toString());
                    txtName.setText(jdata.get("tbl_prefix_name").toString() +
                                          jdata.get("name").toString()+" "+jdata.get("surname").toString());
                    txtAddress1.setText(jdata.get("address1").toString()+" "+jdata.get("tbl_community_name").toString());
                    txtAddress2.setText("ต."+jdata.get("tbl_tumbon_name").toString()+" "+"อ."+jdata.get("tbl_amphur_name").toString()+
                                        " "+"จ."+jdata.get("tbl_province_name").toString());
                    switch (jdata.get("questioner_type").toString())
                    {
                        case "1" : txtQuestionerType.setText("ตำแหน่ง หัวหน้าตำบล"); break;
                        case "2" : txtQuestionerType.setText("ตำแหน่ง รองหัวหน้าตำบล"); break;
                        case "3" : txtQuestionerType.setText("ตำแหน่ง สมาชิกตำบล"); break;
                    }
                    imgcard.setImageBitmap(ImageUtil.convert(jdata.get("picture").toString()));


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
                MyData.put("questionerid", GlobalValue.qestionerid);
                return MyData;
            }
        };

        MyRequestQueue.add(LoginAPIRequest);
    }
}
