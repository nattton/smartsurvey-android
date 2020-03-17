package com.cdd.smartsurvey;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cdd.smartsurvey.sqlite.DatabaseHelper;
import com.cdd.smartsurvey.sqlite.model.Amphur;
import com.cdd.smartsurvey.sqlite.model.Community;
import com.cdd.smartsurvey.sqlite.model.Gender;
import com.cdd.smartsurvey.sqlite.model.Prefix;
import com.cdd.smartsurvey.sqlite.model.Province;
import com.cdd.smartsurvey.sqlite.model.Tumbon;
import com.cdd.smartsurvey.utils.AllMessage;
import com.cdd.smartsurvey.utils.CheckAccuracy;
import com.cdd.smartsurvey.utils.GetThaiCharector;
import com.cdd.smartsurvey.utils.ImageUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterPagesActivity extends AppCompatActivity {

    private static final String TAG = "registerPagesActivity";
    public static final int MY_BLINKID_REQUEST_CODE = 123;

    public Button btnCamera;
    public Button btnCaptureID;
    public Button btnClose;
    public Button btnCloseMain;
    public Button btnSave;
    public EditText txtName;
    public EditText txtSurname;
    public EditText txtAddress;
    public EditText txtCardID;
    public TextView txtBirthDate;
    public TextView txtPrefix;
    public TextView txtGender;
    public TextView txtProvince;
    public TextView txtAmphur;
    public TextView txtTumbon;
    public TextView txtCommunity;
    public String   txtPicture;
    public JSONObject jobj;
    public RequestQueue MyRequestQueue;
    public String   returnOut;
    public AllMessage allMessage;

    CircleImageView imageView;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},0);
        allMessage = new AllMessage();
        MyRequestQueue = Volley.newRequestQueue(this);

        btnCamera = (Button)findViewById(R.id.btnCapture);
        imageView = (CircleImageView)findViewById(R.id.profileimage);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);
            }
        });

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openMainMenu();
//                SendData(GlobalValue.dbUrl);
            }
        });

        txtName = (EditText) findViewById(R.id.txtName);
        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (!GetThaiCharector.checkThai(s))
                    {
                        AlertDialog.Builder ab = new AlertDialog.Builder(RegisterPagesActivity.this, R.style.AlertDialogTheme);
                        ab.setTitle("แจ้งเตือน");
                        ab.setMessage("ต้องใช้เป็นภาษาไทยเท่านั้น");
                        ab.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                txtName.setText("");
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
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        txtSurname = (EditText)findViewById(R.id.txtSurname);
        txtSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (!GetThaiCharector.checkThai(s))
                    {
                        AlertDialog.Builder ab = new AlertDialog.Builder(RegisterPagesActivity.this, R.style.AlertDialogTheme);
                        ab.setTitle("แจ้งเตือน");
                        ab.setMessage("ต้องใช้เป็นภาษาไทยเท่านั้น");
                        ab.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                txtSurname.setText("");
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
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        txtAddress = (EditText)findViewById(R.id.txtAddress);
        txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (!GetThaiCharector.checkThaiNumber(s))
                    {
                        AlertDialog.Builder ab = new AlertDialog.Builder(RegisterPagesActivity.this, R.style.AlertDialogTheme);
                        ab.setTitle("แจ้งเตือน");
                        ab.setMessage("ต้องใช้เป็นภาษาไทย และ ตัวเลขเท่านั้น");
                        ab.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                txtAddress.setText("");
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
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        txtCardID  = (EditText) findViewById(R.id.txtCardID);
        txtCardID.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (!CheckAccuracy.CheckIDCard(s))
                    {
                        AlertDialog.Builder ab = new AlertDialog.Builder(RegisterPagesActivity.this, R.style.AlertDialogTheme);
                        ab.setTitle("แจ้งเตือน");
                        ab.setMessage("ข้อมูลบัตรประชาชนไม่ถูกต้อง");
                        ab.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                txtCardID.setText("");
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

        txtBirthDate = (TextView) findViewById(R.id.txtBirthDate);

        txtPrefix = (TextView) findViewById(R.id.txtPrefix);
        txtPrefix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAlertDialogWithPrefix_Listview();
            }
        });

        txtGender = (TextView) findViewById(R.id.txtGender);
        txtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAlertDialogWithGender_Listview();
            }
        });

        txtProvince = (TextView) findViewById(R.id.txtProvince);
        txtProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAlertDialogWithProvince_Listview();
            }
        });

        txtAmphur = (TextView) findViewById(R.id.txtAmphur);
        txtAmphur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAlertDialogWithAmphur_Listview();
            }
        });

        txtTumbon = (TextView) findViewById(R.id.txtTumbon);
        txtTumbon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAlertDialogWithTumbon_Listview();
            }
        });


        txtCommunity = (TextView) findViewById(R.id.txtCommunity);
        txtCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAlertDialogWithCommunity_Listview();
            }
        });

        btnCloseMain = (Button) findViewById(R.id.btnClose);
        btnCloseMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                onBackPressed();
            }
        });

        mDisplayDate = (TextView) findViewById(R.id.txtBirthDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year  = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day   = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                    RegisterPagesActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Log.d(TAG, "onDateSet: dd/mm/yyyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }

        };

        if (Integer.parseInt(GlobalValue.qestionerid) > 0) {AssignData(GlobalValue.dbUrl);}

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        try {

            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            if (bitmap != null) {
                txtPicture = ImageUtil.convert(bitmap);
                imageView.setImageBitmap(bitmap);
            }

            if (requestCode != MY_BLINKID_REQUEST_CODE) {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onScanCanceled() {
        Toast.makeText(this, "Scan cancelled!", Toast.LENGTH_SHORT).show();
    }

    public void ShowAlertDialogWithPrefix_Listview()
    {
        EditText txtSearch;
        DatabaseHelper db;

        final List<Prefix> prefixList = new ArrayList<>();


        db = new DatabaseHelper(this);

        prefixList.addAll(db.getAllPrefixs());
        Prefix item;

        final ArrayList<Map<String,Object>> itemDataList = new ArrayList<Map<String,Object>>();;

        for(int i=0;i<prefixList.size();i++) {
            item = prefixList.get(i);
            Map<String,Object> listItemMap = new HashMap<String,Object>();
            listItemMap.put("code", item.getCode());
            listItemMap.put("codename", item.getCodename());
            itemDataList.add(listItemMap);
        }

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(RegisterPagesActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_prefix_valuelist, null);

        final SimpleAdapter prefixAdapter = new SimpleAdapter(this,itemDataList,android.R.layout.simple_list_item_2,
                new String[]{"codename"},new int[]{android.R.id.text1});

        txtSearch = (EditText) mView.findViewById(R.id.editText);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                prefixAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ListView list = (ListView) mView.findViewById(R.id.lstValue);
        list.setAdapter(prefixAdapter);

        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Object clickItemObj = adapterView.getAdapter().getItem(index);
                HashMap clickItemMap = (HashMap)clickItemObj;
                txtPrefix.setTag(clickItemMap.get("code"));
                txtPrefix.setText((String)clickItemMap.get("codename"));
                if (txtPrefix.getTag().equals("001") || txtPrefix.getTag().equals("002")) {
                    txtGender.setTag("1");
                    txtGender.setText("ชาย (male)");
                }else{
                    txtGender.setTag("2");
                    txtGender.setText("หญิง (Female)");
                }
                show.dismiss();
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

    public void ShowAlertDialogWithGender_Listview()
    {
        EditText txtSearch;
        List<Gender> genderList = new ArrayList<>();

        DatabaseHelper db;

        db = new DatabaseHelper(this);

        genderList.addAll(db.getAllGenders());
        Gender item;

        ArrayList<Map<String,Object>> itemDataList = new ArrayList<Map<String,Object>>();;

        for(int i=0;i<genderList.size();i++) {
            item = genderList.get(i);
            Map<String,Object> listItemMap = new HashMap<String,Object>();
            listItemMap.put("code", item.getCode());
            listItemMap.put("codename", item.getCodename());
            itemDataList.add(listItemMap);
        }

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(RegisterPagesActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_gender_valuelist, null);

        final SimpleAdapter genderAdapter = new SimpleAdapter(this,itemDataList,android.R.layout.simple_list_item_2,
                new String[]{"codename"},new int[]{android.R.id.text1});

        txtSearch = (EditText) mView.findViewById(R.id.editText);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                genderAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ListView list = (ListView) mView.findViewById(R.id.lstValue);
        list.setAdapter(genderAdapter);

        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Object clickItemObj = adapterView.getAdapter().getItem(index);
                HashMap clickItemMap = (HashMap)clickItemObj;
                txtGender.setTag(clickItemMap.get("code"));
                txtGender.setText((String)clickItemMap.get("codename"));
                show.dismiss();
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

    public void ShowAlertDialogWithProvince_Listview()
    {
        EditText txtSearch;
        List<Province> provinceList = new ArrayList<>();

        DatabaseHelper db;

        db = new DatabaseHelper(this);

        provinceList.addAll(db.getAllProvinces());
        Province item;

        ArrayList<Map<String,Object>> itemDataList = new ArrayList<Map<String,Object>>();;

        for(int i=0;i<provinceList.size();i++) {
            item = provinceList.get(i);
            Map<String,Object> listItemMap = new HashMap<String,Object>();
            listItemMap.put("code", item.getCode());
            listItemMap.put("codename", item.getCodename());
            itemDataList.add(listItemMap);
        }

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(RegisterPagesActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_province_valuelist, null);

        final SimpleAdapter provinceAdapter = new SimpleAdapter(this,itemDataList,android.R.layout.simple_list_item_2,
                new String[]{"codename"},new int[]{android.R.id.text1});

        txtSearch = (EditText) mView.findViewById(R.id.editText);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                provinceAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ListView list = (ListView) mView.findViewById(R.id.lstValue);
        list.setAdapter(provinceAdapter);

        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Object clickItemObj = adapterView.getAdapter().getItem(index);
                HashMap clickItemMap = (HashMap)clickItemObj;
                txtProvince.setTag(clickItemMap.get("code"));
                txtProvince.setText((String)clickItemMap.get("codename"));
                show.dismiss();
            }
        });

        btnClose = (Button) mView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                show.dismiss();
            }
        });
        txtAmphur.setTag("");
        txtAmphur.setText("");
        txtTumbon.setTag("");
        txtTumbon.setText("");
        txtCommunity.setTag("");
        txtCommunity.setText("");
    }

    public void ShowAlertDialogWithAmphur_Listview()
    {
        EditText txtSearch;
        List<Amphur> amphurList = new ArrayList<>();

        DatabaseHelper db;

        db = new DatabaseHelper(this);

        amphurList.addAll(db.getAllAmphurs(txtProvince.getTag() == null ? "0" : txtProvince.getTag().toString()));
        Amphur item;


        ArrayList<Map<String,Object>> itemDataList = new ArrayList<Map<String,Object>>();;

        for(int i=0;i<amphurList.size();i++) {
            item = amphurList.get(i);
            Map<String,Object> listItemMap = new HashMap<String,Object>();
            listItemMap.put("code", item.getCode());
            listItemMap.put("codename", item.getCodename());
            itemDataList.add(listItemMap);
        }

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(RegisterPagesActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_amphur_valuelist, null);

        final SimpleAdapter amphurAdapter = new SimpleAdapter(this,itemDataList,android.R.layout.simple_list_item_2,
                new String[]{"codename"},new int[]{android.R.id.text1});

        txtSearch = (EditText) mView.findViewById(R.id.editText);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amphurAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ListView list = (ListView) mView.findViewById(R.id.lstValue);
        list.setAdapter(amphurAdapter);

        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Object clickItemObj = adapterView.getAdapter().getItem(index);
                HashMap clickItemMap = (HashMap)clickItemObj;
                txtAmphur.setTag(clickItemMap.get("code"));
                txtAmphur.setText((String)clickItemMap.get("codename"));
                show.dismiss();
            }
        });

        btnClose = (Button) mView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                show.dismiss();
            }
        });
        txtTumbon.setTag("");
        txtTumbon.setText("");
        txtCommunity.setTag("");
        txtCommunity.setText("");
    }

    public void ShowAlertDialogWithTumbon_Listview()
    {
        EditText txtSearch;
        List<Tumbon> tumbonList = new ArrayList<>();

        DatabaseHelper db;

        db = new DatabaseHelper(this);

        tumbonList.addAll(db.getAllTumbons(txtAmphur.getTag() == null ? "0" : txtAmphur.getTag().toString()));
        Tumbon item;

        ArrayList<Map<String,Object>> itemDataList = new ArrayList<Map<String,Object>>();;

        for(int i=0;i<tumbonList.size();i++) {
            item = tumbonList.get(i);
            Map<String,Object> listItemMap = new HashMap<String,Object>();
            listItemMap.put("code", item.getCode());
            listItemMap.put("codename", item.getCodename());
            itemDataList.add(listItemMap);
        }

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(RegisterPagesActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_tumbon_valuelist, null);

        final SimpleAdapter tumbonAdapter = new SimpleAdapter(this,itemDataList,android.R.layout.simple_list_item_2,
                new String[]{"codename"},new int[]{android.R.id.text1});

        txtSearch = (EditText) mView.findViewById(R.id.editText);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tumbonAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ListView list = (ListView) mView.findViewById(R.id.lstValue);
        list.setAdapter(tumbonAdapter);

        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Object clickItemObj = adapterView.getAdapter().getItem(index);
                HashMap clickItemMap = (HashMap)clickItemObj;
                txtTumbon.setTag(clickItemMap.get("code"));
                txtTumbon.setText((String)clickItemMap.get("codename"));
                show.dismiss();
            }
        });

        btnClose = (Button) mView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                show.dismiss();
            }
        });
        txtCommunity.setTag("");
        txtCommunity.setText("");
    }

    public void ShowAlertDialogWithCommunity_Listview()
    {
        EditText txtSearch;
        List<Community> communityList = new ArrayList<>();

        DatabaseHelper db;

        db = new DatabaseHelper(this);

        communityList.addAll(db.getAllCommunitys(txtTumbon.getTag() == null ? "0" : txtTumbon.getTag().toString()));
        Community item;

        ArrayList<Map<String,Object>> itemDataList = new ArrayList<Map<String,Object>>();;

        for(int i=0;i<communityList.size();i++) {
            item = communityList.get(i);
            Map<String,Object> listItemMap = new HashMap<String,Object>();
            listItemMap.put("code", item.getCode());
            listItemMap.put("moo" , item.getMoo());
            listItemMap.put("codename", "หมู่ที่ "+ item.getMoo()+" "+item.getCodename());
            itemDataList.add(listItemMap);
        }

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(RegisterPagesActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_community_valuelist, null);

        final SimpleAdapter communityAdapter = new SimpleAdapter(this,itemDataList,android.R.layout.simple_list_item_2,
                new String[]{"codename"},new int[]{android.R.id.text1});

        txtSearch = (EditText) mView.findViewById(R.id.editText);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                communityAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ListView list = (ListView) mView.findViewById(R.id.lstValue);
        list.setAdapter(communityAdapter);

        mBuilder.setView(mView);
        final AlertDialog show = mBuilder.show();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Object clickItemObj = adapterView.getAdapter().getItem(index);
                HashMap clickItemMap = (HashMap)clickItemObj;
                txtCommunity.setTag(clickItemMap.get("code"));
                txtCommunity.setText((String)clickItemMap.get("codename"));
                show.dismiss();
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ab = new AlertDialog.Builder(RegisterPagesActivity.this,R.style.AlertDialogTheme);
        ab.setTitle("แจ้งเตือน");
        ab.setMessage("ท่านต้องการออกจากโปรแกรมนี้หรือไม่ ?");
        ab.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                System.exit(0);
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

    public void SendData (String aurl) {

        String subpath = "";

        if (GlobalValue.qestionerid.equals("0")) {

            subpath = "questioner/profile/insert";

        } else {

            subpath = "questioner/profile/update";
        }


        StringRequest LoginAPIRequest = new StringRequest(Request.Method.POST, aurl + subpath, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jobj = new JSONObject(response);
                    if (Integer.parseInt(jobj.get("status").toString()) > 0) {
                        GlobalValue.qestionerid = jobj.get("status").toString();
                        openMainMenu();
                    } else {
                        allMessage.ErrorMessage(RegisterPagesActivity.this);
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
                try {
                    MyData.put("tbl_user_masterid", GlobalValue.loginid);
                    MyData.put("year", "2563");
                    MyData.put("questioner_type", "1");
                    MyData.put("idcard", txtCardID.getText().toString());
                    MyData.put("tbl_prefix_masterid", txtPrefix.getTag().toString());
                    MyData.put("name", txtName.getText().toString());
                    MyData.put("surname", txtSurname.getText().toString());
                    MyData.put("tbl_gender_masterid", txtGender.getTag().toString());
                    MyData.put("birthdate", txtBirthDate.getText().toString());
                    MyData.put("address1", txtAddress.getText().toString());
                    MyData.put("tbl_tumbon_masterid", txtTumbon.getTag().toString());
                    MyData.put("tbl_amphur_masterid", txtAmphur.getTag().toString());
                    MyData.put("tbl_province_masterid", txtProvince.getTag().toString());
                    MyData.put("tbl_community_masterid", txtCommunity.getTag().toString());
                    MyData.put("picture", txtPicture.toString());
                    MyData.put("create_by", GlobalValue.uid);
                    MyData.put("modify_by", GlobalValue.uid);
                } catch (Exception e) {
                    allMessage.ErrorMessage(RegisterPagesActivity.this);
                }
                return MyData;
            }
        };

        MyRequestQueue.add(LoginAPIRequest);
    }

    public void openMainMenu() {
        Intent intent = new Intent(this, FirstMenuActivity.class);
        startActivity(intent);
    }

    public void AssignData(String aurl){

            String subpath = "";

            if (!GlobalValue.qestionerid.equals("0")) {

                subpath = "questioner/profile/select";
                StringRequest LoginAPIRequest = new StringRequest(Request.Method.POST, aurl + subpath, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        jobj = new JSONObject(response);
                        JSONArray array = jobj.getJSONArray("TransQuestionerProfile");
                        JSONObject jdata = array.getJSONObject(0);
                        txtCardID.setText(jdata.get("idcard").toString());
                        txtPrefix.setTag(jdata.get("tbl_prefix_code"));
                        txtPrefix.setText(jdata.get("tbl_prefix_name").toString());
                        txtName.setText(jdata.get("name").toString());
                        txtSurname.setText(jdata.get("surname").toString());
                        txtGender.setTag(jdata.get("tbl_gender_code"));
                        txtGender.setText(jdata.get("tbl_gender_name").toString());
                        txtBirthDate.setText(jdata.get("birthdate").toString());
                        txtAddress.setText(jdata.get("address1").toString());
                        txtProvince.setTag(jdata.get("tbl_province_id"));
                        txtProvince.setText(jdata.get("tbl_province_name").toString());
                        txtAmphur.setTag(jdata.get("tbl_amphur_id"));
                        txtAmphur.setText(jdata.get("tbl_amphur_name").toString());
                        txtTumbon.setTag(jdata.get("tbl_tumbon_id"));
                        txtTumbon.setText(jdata.get("tbl_tumbon_name").toString());
                        txtCommunity.setTag(jdata.get("tbl_community_id"));
                        txtCommunity.setText(jdata.get("tbl_community_name").toString());
                        txtPicture = jdata.get("picture").toString();
                        imageView.setImageBitmap(ImageUtil.convert(jdata.get("picture").toString()));


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

}
