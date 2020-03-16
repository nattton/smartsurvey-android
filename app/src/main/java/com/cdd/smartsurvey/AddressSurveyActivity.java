package com.cdd.smartsurvey;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cdd.smartsurvey.sqlite.DatabaseHelper;
import com.cdd.smartsurvey.sqlite.model.Amphur;
import com.cdd.smartsurvey.sqlite.model.Community;
import com.cdd.smartsurvey.sqlite.model.Province;
import com.cdd.smartsurvey.sqlite.model.Tumbon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressSurveyActivity extends AppCompatActivity {

    public Button btnSave;
    public RadioButton rdHomeUse;
    public RadioButton rdHomeNotUse;
    public EditText txtHomeNo;
    public Button btnBack;
    public Button btnClose;
    public TextView txtProvince;
    public TextView txtAmphur;
    public TextView txtTumbon;
    public TextView txtCommunity;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_survey);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtHomeNo = (EditText) findViewById(R.id.txtHomeNo);
        rdHomeUse = (RadioButton) findViewById(R.id.rdHomeUse);
        rdHomeUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                txtHomeNo.setVisibility(View.VISIBLE);
            }
        });

        rdHomeNotUse = (RadioButton) findViewById(R.id.rdHomeNotUse);
        rdHomeNotUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                txtHomeNo.setVisibility(View.INVISIBLE);
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

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                SendData(GlobalValue.dbUrl);
            }
        });
    }

    public void SendData (String aurl) {
        Intent intent = new Intent(this,LocalGovSurveyActivity.class);
        startActivity(intent);    }

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

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddressSurveyActivity.this);
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

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddressSurveyActivity.this);
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

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddressSurveyActivity.this);
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

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddressSurveyActivity.this);
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
}
