package com.cdd.smartsurvey;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.cdd.smartsurvey.sqlite.DatabaseHelper;
import com.cdd.smartsurvey.sqlite.model.Gender;
import com.cdd.smartsurvey.sqlite.model.Prefix;
import com.cdd.smartsurvey.utils.AllMessage;
import com.cdd.smartsurvey.utils.GetThaiCharector;
import com.cdd.smartsurvey.utils.ImageUtil;
import com.microblink.MicroblinkSDK;
import com.microblink.entities.recognizers.RecognizerBundle;
import com.microblink.entities.recognizers.blinkid.generic.BlinkIdCombinedRecognizer;
import com.microblink.intent.IntentDataTransferMode;
import com.microblink.uisettings.ActivityRunner;
import com.microblink.uisettings.BlinkIdUISettings;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterPagesNewFamilyActivity extends AppCompatActivity {

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
    public String   txtPicture;
    public JSONObject jobj;
    public RequestQueue MyRequestQueue;
    public String   returnOut;
    public AllMessage allMessage;

    CircleImageView imageView;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private BlinkIdCombinedRecognizer recognizer;
    private RecognizerBundle recognizerBundle;
    public Button btnStartSurvey;
    public Button btnBack;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MicroblinkSDK.setLicenseFile(GlobalValue.LicenseFile,this);
        MicroblinkSDK.setIntentDataTransferMode(IntentDataTransferMode.PERSISTED_OPTIMISED);
        setContentView(R.layout.activity_register_newfamily);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},0);
        recognizer = new BlinkIdCombinedRecognizer();
        recognizerBundle = new RecognizerBundle(recognizer);
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

        mDisplayDate = (TextView) findViewById(R.id.txtBirthDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year  = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day   = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegisterPagesNewFamilyActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        btnCaptureID = (Button)findViewById(R.id.btnCaptureID);
        btnCaptureID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openCaptureID();
            }
        });

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                SendData(GlobalValue.dbUrl);
            }
        });

        txtName = (EditText) findViewById(R.id.txtName);
        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (!GetThaiCharector.checkThai(s))
                    {
                        AlertDialog.Builder ab = new AlertDialog.Builder(RegisterPagesNewFamilyActivity.this, R.style.AlertDialogTheme);
                        ab.setTitle("แจ้งเตือน");
                        ab.setMessage("ต้องใช้เป็นภาษาไทยเท่านั้น");
                        ab.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                txtName.setText("");
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
                        AlertDialog.Builder ab = new AlertDialog.Builder(RegisterPagesNewFamilyActivity.this, R.style.AlertDialogTheme);
                        ab.setTitle("แจ้งเตือน");
                        ab.setMessage("ต้องใช้เป็นภาษาไทยเท่านั้น");
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
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
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


        btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                onBackPressed();
            }
        });
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

            if (resultCode == Activity.RESULT_OK) {
                onScanSuccess(data);
            } else {
                onScanCanceled();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onScanSuccess(Intent data) {
        recognizerBundle.loadFromIntent(data);

        BlinkIdCombinedRecognizer.Result result = recognizer.getResult();
        String birthDate = result.getDateOfBirth().getDate().toString().substring(0,10).replace('.','/');
        String cardID = result.getDocumentNumber();
        if (cardID.isEmpty()) {
            cardID = result.getDocumentNumber();
        }
        txtCardID = (EditText) findViewById(R.id.txtCardID);
        txtCardID.setText(cardID);

        txtBirthDate = (TextView) findViewById(R.id.txtBirthDate);
        txtBirthDate.setText(birthDate);
    }

    private void onScanCanceled() {
        Toast.makeText(this, "Scan cancelled!", Toast.LENGTH_SHORT).show();
    }

    public void openMenuDetailSurvey() {
        Intent intent = new Intent(this,MenuSurveyActivity.class);
        startActivity(intent);
    }

    public void openCaptureID() {
        BlinkIdUISettings uiSettings = new BlinkIdUISettings(recognizerBundle);
        ActivityRunner.startActivityForResult(this, MY_BLINKID_REQUEST_CODE, uiSettings);
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

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(RegisterPagesNewFamilyActivity.this);
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

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(RegisterPagesNewFamilyActivity.this);
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

    public void SendData (String aurl) {
        Intent intent = new Intent(this,AddressSurveyActivity.class);
        startActivity(intent);
    }

}
