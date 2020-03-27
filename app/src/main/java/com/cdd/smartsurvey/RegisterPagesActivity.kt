package com.cdd.smartsurvey

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Process
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cdd.smartsurvey.http.model.RegisterResponse
import com.cdd.smartsurvey.sqlite.DatabaseHelper
import com.cdd.smartsurvey.sqlite.model.*
import com.cdd.smartsurvey.utils.AllMessage
import com.cdd.smartsurvey.utils.CheckAccuracy
import com.cdd.smartsurvey.utils.GetThaiCharector
import com.cdd.smartsurvey.utils.ImageUtil
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.DataPart
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.result.Result
import com.layernet.thaidatetimepicker.date.DatePickerDialog
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class RegisterPagesActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    companion object {
        private const val TAG = "registerPagesActivity"
    }

    //    var txtPicture: String = ""
    lateinit var currentPhotoPath: String
    var jobj: JSONObject? = null
    var MyRequestQueue: RequestQueue? = null
    var returnOut: String? = null
    var allMessage: AllMessage? = null

    //    var imageView: CircleImageView? = null
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_TAKE_PHOTO = 1
    private val RECORD_REQUEST_CODE = 101

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setupPermissions()
        allMessage = AllMessage()
        MyRequestQueue = Volley.newRequestQueue(this)
//        imageView = findViewById<View>(R.id.profileimage) as CircleImageView
        btnCapture.setOnClickListener {
            dispatchTakePictureIntent()
        }
        btnSave.setOnClickListener { //                openMainMenu();
            SendData(GlobalValue.apiUrl)
        }
        txtName!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    if (!GetThaiCharector.checkThai(s)) {
                        val ab = AlertDialog.Builder(this@RegisterPagesActivity, R.style.AlertDialogTheme)
                        ab.setTitle("แจ้งเตือน")
                        ab.setMessage("ต้องใช้เป็นภาษาไทยเท่านั้น")
                        ab.setPositiveButton("ตกลง") { dialog, which ->
                            txtName!!.setText("")
                            dialog.dismiss()
                        }
                        ab.show()
                    }
                } catch (e: Exception) {
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })
        txtSurname!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    if (!GetThaiCharector.checkThai(s)) {
                        val ab = AlertDialog.Builder(this@RegisterPagesActivity, R.style.AlertDialogTheme)
                        ab.setTitle("แจ้งเตือน")
                        ab.setMessage("ต้องใช้เป็นภาษาไทยเท่านั้น")
                        ab.setPositiveButton("ตกลง") { dialog, which ->
                            txtSurname!!.setText("")
                            dialog.dismiss()
                        }
                        ab.show()
                    }
                } catch (e: Exception) {
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })
        txtAddress!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    if (!GetThaiCharector.checkThaiNumber(s)) {
                        val ab = AlertDialog.Builder(this@RegisterPagesActivity, R.style.AlertDialogTheme)
                        ab.setTitle("แจ้งเตือน")
                        ab.setMessage("ต้องใช้เป็นภาษาไทย และ ตัวเลขเท่านั้น")
                        ab.setPositiveButton("ตกลง") { dialog, which ->
                            txtAddress!!.setText("")
                            dialog.dismiss()
                        }
                        ab.show()
                    }
                } catch (e: Exception) {
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })
        txtCardID.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    if (!CheckAccuracy.CheckIDCard(s)) {
                        val ab = AlertDialog.Builder(this@RegisterPagesActivity, R.style.AlertDialogTheme)
                        ab.setTitle("แจ้งเตือน")
                        ab.setMessage("ข้อมูลบัตรประชาชนไม่ถูกต้อง")
                        ab.setPositiveButton("ตกลง") { dialog, which ->
                            txtCardID!!.setText("")
                            dialog.dismiss()
                        }
                        ab.show()
                    }
                } catch (e: Exception) {
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })
        txtPrefix!!.setOnClickListener { ShowAlertDialogWithPrefix_Listview() }
        txtGender!!.setOnClickListener { ShowAlertDialogWithGender_Listview() }
        txtProvince!!.setOnClickListener { ShowAlertDialogWithProvince_Listview() }
        txtAmphur!!.setOnClickListener { ShowAlertDialogWithAmphur_Listview() }
        txtTumbon!!.setOnClickListener { ShowAlertDialogWithTumbon_Listview() }
        txtCommunity!!.setOnClickListener { ShowAlertDialogWithCommunity_Listview() }
        btnClose.setOnClickListener { onBackPressed() }
        txtBirthDate.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                    this@RegisterPagesActivity,
                    now[Calendar.YEAR],
                    now[Calendar.MONTH],
                    now[Calendar.DAY_OF_MONTH]
            )

            dpd.show(fragmentManager, "Datepickerdialog");
        }
//        txtBirthDate.setOnClickListener {
//            val cal = Calendar.getInstance()
//            val year = cal[Calendar.YEAR]
//            val month = cal[Calendar.MONTH]
//            val day = cal[Calendar.DAY_OF_MONTH]
//            val dialog = DatePickerDialog(
//                    this@RegisterPagesActivity,
//                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                    mDateSetListener,
//                    year, month, day)
//            dialog
//            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            dialog.show()
//        }
//        mDateSetListener = OnDateSetListener { view, year, month, day ->
//            Log.d(TAG, "onDateSet: dd/mm/yyyy: $day/$month/$year")
//            val date = "$day/$month/$year"
//            txtBirthDate.text = date
//        }
        if (GlobalValue.qestionerid.toInt() > 0) {
            AssignData(GlobalValue.dbUrl)
        }
    }

    private fun setupPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission denied")
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE),
                RECORD_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val processBitmap = resizeBitmap(currentPhotoPath)
            saveImage(processBitmap, currentPhotoPath)
//            val processBitmap = BitmapFactory.decodeFile(currentPhotoPath)
            profileimage.setImageBitmap(processBitmap)
        }
//        try {
//            val bitmap = data!!.extras["data"] as Bitmap
//            if (bitmap != null) {
//                txtPicture = ImageUtil.convert(bitmap)
//                imageView!!.setImageBitmap(bitmap)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }

    fun ShowAlertDialogWithPrefix_Listview() {
        val txtSearch: EditText
        val db: DatabaseHelper
        val prefixList: MutableList<Prefix> = ArrayList()
        db = DatabaseHelper(this)
        prefixList.addAll(db.allPrefixs)
        var item: Prefix
        val itemDataList = ArrayList<Map<String, Any?>>()
        for (i in prefixList.indices) {
            item = prefixList[i]
            val listItemMap: MutableMap<String, Any?> = HashMap()
            listItemMap["code"] = item.code
            listItemMap["codename"] = item.codename
            itemDataList.add(listItemMap)
        }
        val mBuilder = AlertDialog.Builder(this@RegisterPagesActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_prefix_valuelist, null)
        val prefixAdapter = SimpleAdapter(this, itemDataList, android.R.layout.simple_list_item_2, arrayOf("codename"), intArrayOf(android.R.id.text1))
        txtSearch = mView.findViewById<View>(R.id.editText) as EditText
        txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                prefixAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable) {}
        })
        val list = mView.findViewById<View>(R.id.lstValue) as ListView
        list.adapter = prefixAdapter
        mBuilder.setView(mView)
        val show = mBuilder.show()
        list.onItemClickListener = OnItemClickListener { adapterView, view, index, l ->
            val clickItemObj = adapterView.adapter.getItem(index)
            val clickItemMap = clickItemObj as HashMap<*, *>
            txtPrefix!!.tag = clickItemMap["code"]
            txtPrefix!!.text = clickItemMap["codename"] as String?
            if (txtPrefix!!.tag == "001" || txtPrefix!!.tag == "002") {
                txtGender!!.tag = "1"
                txtGender!!.text = "ชาย (male)"
            } else {
                txtGender!!.tag = "2"
                txtGender!!.text = "หญิง (Female)"
            }
            show.dismiss()
        }
        val btnClose = mView.findViewById<View>(R.id.btnClose) as Button
        btnClose.setOnClickListener { show.dismiss() }
    }

    fun ShowAlertDialogWithGender_Listview() {
        val txtSearch: EditText
        val genderList: MutableList<Gender> = ArrayList()
        val db: DatabaseHelper
        db = DatabaseHelper(this)
        genderList.addAll(db.allGenders)
        var item: Gender
        val itemDataList = ArrayList<Map<String, Any?>>()
        for (i in genderList.indices) {
            item = genderList[i]
            val listItemMap: MutableMap<String, Any?> = HashMap()
            listItemMap["code"] = item.code
            listItemMap["codename"] = item.codename
            itemDataList.add(listItemMap)
        }
        val mBuilder = AlertDialog.Builder(this@RegisterPagesActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_gender_valuelist, null)
        val genderAdapter = SimpleAdapter(this, itemDataList, android.R.layout.simple_list_item_2, arrayOf("codename"), intArrayOf(android.R.id.text1))
        txtSearch = mView.findViewById<View>(R.id.editText) as EditText
        txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                genderAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable) {}
        })
        val list = mView.findViewById<View>(R.id.lstValue) as ListView
        list.adapter = genderAdapter
        mBuilder.setView(mView)
        val show = mBuilder.show()
        list.onItemClickListener = OnItemClickListener { adapterView, view, index, l ->
            val clickItemObj = adapterView.adapter.getItem(index)
            val clickItemMap = clickItemObj as HashMap<*, *>
            txtGender!!.tag = clickItemMap["code"]
            txtGender!!.text = clickItemMap["codename"] as String?
            show.dismiss()
        }
        val btnClose = mView.findViewById<View>(R.id.btnClose) as Button
        btnClose.setOnClickListener { show.dismiss() }
    }

    fun ShowAlertDialogWithProvince_Listview() {
        val txtSearch: EditText
        val provinceList: MutableList<Province> = ArrayList()
        val db: DatabaseHelper
        db = DatabaseHelper(this)
        provinceList.addAll(db.allProvinces)
        var item: Province
        val itemDataList = ArrayList<Map<String, Any?>>()
        for (i in provinceList.indices) {
            item = provinceList[i]
            val listItemMap: MutableMap<String, Any?> = HashMap()
            listItemMap["code"] = item.code
            listItemMap["codename"] = item.codename
            itemDataList.add(listItemMap)
        }
        val mBuilder = AlertDialog.Builder(this@RegisterPagesActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_province_valuelist, null)
        val provinceAdapter = SimpleAdapter(this, itemDataList, android.R.layout.simple_list_item_2, arrayOf("codename"), intArrayOf(android.R.id.text1))
        txtSearch = mView.findViewById<View>(R.id.editText) as EditText
        txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                provinceAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable) {}
        })
        val list = mView.findViewById<View>(R.id.lstValue) as ListView
        list.adapter = provinceAdapter
        mBuilder.setView(mView)
        val show = mBuilder.show()
        list.onItemClickListener = OnItemClickListener { adapterView, view, index, l ->
            val clickItemObj = adapterView.adapter.getItem(index)
            val clickItemMap = clickItemObj as HashMap<*, *>
            txtProvince!!.tag = clickItemMap["code"]
            txtProvince!!.text = clickItemMap["codename"] as String?
            show.dismiss()
        }
        val btnClose = mView.findViewById<View>(R.id.btnClose) as Button
        btnClose.setOnClickListener { show.dismiss() }
        txtAmphur!!.tag = ""
        txtAmphur!!.text = ""
        txtTumbon!!.tag = ""
        txtTumbon!!.text = ""
        txtCommunity!!.tag = ""
        txtCommunity!!.text = ""
    }

    fun ShowAlertDialogWithAmphur_Listview() {
        val txtSearch: EditText
        val amphurList: MutableList<Amphur> = ArrayList()
        val db: DatabaseHelper
        db = DatabaseHelper(this)
        amphurList.addAll(db.getAllAmphurs(if (txtProvince!!.tag == null) "0" else txtProvince!!.tag.toString()))
        var item: Amphur
        val itemDataList = ArrayList<Map<String, Any?>>()
        for (i in amphurList.indices) {
            item = amphurList[i]
            val listItemMap: MutableMap<String, Any?> = HashMap()
            listItemMap["code"] = item.code
            listItemMap["codename"] = item.codename
            itemDataList.add(listItemMap)
        }
        val mBuilder = AlertDialog.Builder(this@RegisterPagesActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_amphur_valuelist, null)
        val amphurAdapter = SimpleAdapter(this, itemDataList, android.R.layout.simple_list_item_2, arrayOf("codename"), intArrayOf(android.R.id.text1))
        txtSearch = mView.findViewById<View>(R.id.editText) as EditText
        txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                amphurAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable) {}
        })
        val list = mView.findViewById<View>(R.id.lstValue) as ListView
        list.adapter = amphurAdapter
        mBuilder.setView(mView)
        val show = mBuilder.show()
        list.onItemClickListener = OnItemClickListener { adapterView, view, index, l ->
            val clickItemObj = adapterView.adapter.getItem(index)
            val clickItemMap = clickItemObj as HashMap<*, *>
            txtAmphur!!.tag = clickItemMap["code"]
            txtAmphur!!.text = clickItemMap["codename"] as String?
            show.dismiss()
        }
        val btnClose = mView.findViewById<View>(R.id.btnClose) as Button
        btnClose!!.setOnClickListener { show.dismiss() }
        txtTumbon!!.tag = ""
        txtTumbon!!.text = ""
        txtCommunity!!.tag = ""
        txtCommunity!!.text = ""
    }

    fun ShowAlertDialogWithTumbon_Listview() {
        val txtSearch: EditText
        val tumbonList: MutableList<Tumbon> = ArrayList()
        val db: DatabaseHelper
        db = DatabaseHelper(this)
        tumbonList.addAll(db.getAllTumbons(if (txtAmphur!!.tag == null) "0" else txtAmphur!!.tag.toString()))
        var item: Tumbon
        val itemDataList = ArrayList<Map<String, Any?>>()
        for (i in tumbonList.indices) {
            item = tumbonList[i]
            val listItemMap: MutableMap<String, Any?> = HashMap()
            listItemMap["code"] = item.code
            listItemMap["codename"] = item.codename
            itemDataList.add(listItemMap)
        }
        val mBuilder = AlertDialog.Builder(this@RegisterPagesActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_tumbon_valuelist, null)
        val tumbonAdapter = SimpleAdapter(this, itemDataList, android.R.layout.simple_list_item_2, arrayOf("codename"), intArrayOf(android.R.id.text1))
        txtSearch = mView.findViewById<View>(R.id.editText) as EditText
        txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                tumbonAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable) {}
        })
        val list = mView.findViewById<View>(R.id.lstValue) as ListView
        list.adapter = tumbonAdapter
        mBuilder.setView(mView)
        val show = mBuilder.show()
        list.onItemClickListener = OnItemClickListener { adapterView, view, index, l ->
            val clickItemObj = adapterView.adapter.getItem(index)
            val clickItemMap = clickItemObj as HashMap<*, *>
            txtTumbon!!.tag = clickItemMap["code"]
            txtTumbon!!.text = clickItemMap["codename"] as String?
            show.dismiss()
        }
        val btnClose = mView.findViewById<View>(R.id.btnClose) as Button
        btnClose!!.setOnClickListener { show.dismiss() }
        txtCommunity!!.tag = ""
        txtCommunity!!.text = ""
    }

    fun ShowAlertDialogWithCommunity_Listview() {
        val txtSearch: EditText
        val communityList: MutableList<Community> = ArrayList()
        val db: DatabaseHelper
        db = DatabaseHelper(this)
        communityList.addAll(db.getAllCommunitys(if (txtTumbon!!.tag == null) "0" else txtTumbon!!.tag.toString()))
        var item: Community
        val itemDataList = ArrayList<Map<String, Any?>>()
        for (i in communityList.indices) {
            item = communityList[i]
            val listItemMap: MutableMap<String, Any?> = HashMap()
            listItemMap["code"] = item.code
            listItemMap["moo"] = item.moo
            listItemMap["codename"] = "หมู่ที่ " + item.moo + " " + item.codename
            itemDataList.add(listItemMap)
        }
        val mBuilder = AlertDialog.Builder(this@RegisterPagesActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_community_valuelist, null)
        val communityAdapter = SimpleAdapter(this, itemDataList, android.R.layout.simple_list_item_2, arrayOf("codename"), intArrayOf(android.R.id.text1))
        txtSearch = mView.findViewById<View>(R.id.editText) as EditText
        txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                communityAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable) {}
        })
        val list = mView.findViewById<View>(R.id.lstValue) as ListView
        list.adapter = communityAdapter
        mBuilder.setView(mView)
        val show = mBuilder.show()
        list.onItemClickListener = OnItemClickListener { adapterView, view, index, l ->
            val clickItemObj = adapterView.adapter.getItem(index)
            val clickItemMap = clickItemObj as HashMap<*, *>
            txtCommunity!!.tag = clickItemMap["code"]
            txtCommunity!!.text = clickItemMap["codename"] as String?
            show.dismiss()
        }
        val btnClose = mView.findViewById<View>(R.id.btnClose) as Button
        btnClose!!.setOnClickListener { show.dismiss() }
    }

    override fun onBackPressed() {
        val ab = AlertDialog.Builder(this@RegisterPagesActivity, R.style.AlertDialogTheme)
        ab.setTitle("แจ้งเตือน")
        ab.setMessage("ท่านต้องการออกจากโปรแกรมนี้หรือไม่ ?")
        ab.setPositiveButton("ตกลง") { dialog, which ->
            dialog.dismiss()
            finish()
            val pid = Process.myPid()
            Process.killProcess(pid)
            System.exit(0)
        }
        ab.setNegativeButton("ยกเลิก") { dialog, which -> dialog.dismiss() }
        ab.show()
    }

    fun SendData(aurl: String) {
        val sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                ?: return
        var userToken = sharedPref.getString(getString(R.string.pref_user_token), "")
        val params = listOf(Pair("t", GlobalValue.apiToken),
                Pair("task", "saveuserinfo"),
                Pair("token", userToken),
                Pair("prefix_id", txtPrefix.tag.toString()),
                Pair("name", txtName.text),
                Pair("sname", txtSurname.text),
                Pair("idcard", txtCardID.text),
                Pair("sex_id", txtGender.tag.toString()),
                Pair("birthday", txtBirthDate.text.toString()),
                Pair("addr", txtAddress.text),
                Pair("community_id", txtCommunity.tag.toString()),
                Pair("district_code", txtTumbon.tag.toString()),
                Pair("amphur_code", txtAmphur.tag.toString()),
                Pair("province_code", txtProvince.tag.toString())
        )
        Fuel.upload("mobile.php", Method.POST, params)
                .dataParts { request, url ->
                    listOf(DataPart(File(currentPhotoPath), "photo"))
                }
                .responseObject(RegisterResponse.Deserializer()) { _, _, result ->
                    when (result) {
                        is Result.Success -> {
                            val (registerResponse, _) = result
                            if (registerResponse?.status == "1") {
                                openMainMenu()
                            } else {
                                allMessage!!.ErrorMessage(this@RegisterPagesActivity)
                            }
                        }
                        is Result.Failure -> {
                            allMessage!!.ErrorMessage(this@RegisterPagesActivity)
                        }
                    }
                }
    }

    fun openMainMenu() {
        val intent = Intent(this, FirstMenuActivity::class.java)
        startActivity(intent)
    }

    fun AssignData(aurl: String) {
        var subpath = ""
        if (GlobalValue.qestionerid != "0") {
            subpath = "questioner/profile/select"
            val LoginAPIRequest: StringRequest = object : StringRequest(Method.POST, aurl + subpath, Response.Listener { response ->
                try {
                    jobj = JSONObject(response)
                    val array = jobj!!.getJSONArray("TransQuestionerProfile")
                    val jdata = array.getJSONObject(0)
                    txtCardID!!.setText(jdata["idcard"].toString())
                    txtPrefix!!.tag = jdata["tbl_prefix_code"]
                    txtPrefix!!.text = jdata["tbl_prefix_name"].toString()
                    txtName!!.setText(jdata["name"].toString())
                    txtSurname!!.setText(jdata["surname"].toString())
                    txtGender!!.tag = jdata["tbl_gender_code"]
                    txtGender!!.text = jdata["tbl_gender_name"].toString()
                    txtBirthDate!!.text = jdata["birthdate"].toString()
                    txtAddress!!.setText(jdata["address1"].toString())
                    txtProvince!!.tag = jdata["tbl_province_id"]
                    txtProvince!!.text = jdata["tbl_province_name"].toString()
                    txtAmphur!!.tag = jdata["tbl_amphur_id"]
                    txtAmphur!!.text = jdata["tbl_amphur_name"].toString()
                    txtTumbon!!.tag = jdata["tbl_tumbon_id"]
                    txtTumbon!!.text = jdata["tbl_tumbon_name"].toString()
                    txtCommunity!!.tag = jdata["tbl_community_id"]
                    txtCommunity!!.text = jdata["tbl_community_name"].toString()
//                    txtPicture = jdata["picture"].toString()
                    profileimage.setImageBitmap(ImageUtil.convert(jdata["picture"].toString()))
                } catch (e: JSONException) {
                }
            }, //Create an error listener to handle errors appropriately.
                    Response.ErrorListener { }) {
                override fun getParams(): Map<String, String> {
                    val MyData: MutableMap<String, String> = HashMap()
                    MyData["questionerid"] = GlobalValue.qestionerid
                    return MyData
                }
            }
            MyRequestQueue!!.add(LoginAPIRequest)
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File

                    null
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "com.cdd.smartsurvey.fileprovider",
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }

        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    fun resizeBitmap(imagePath: String): Bitmap {
        val scale = resources.getDisplayMetrics().density
        var b = BitmapFactory.decodeFile(imagePath)

        val origWidth = b.width
        val origHeight = b.height

        val destWidth = 2048

        if (origWidth > destWidth) {
            // picture is wider than we want it, we calculate its target height
            val destHeight = origHeight / (origWidth / destWidth);
            // we create an scaled bitmap so it reduces the image, not just trim it
            var b2 = Bitmap.createScaledBitmap(b, destWidth, destHeight, false);

            return b2
        }

        return b
    }

    private fun saveImage(finalBitmap: Bitmap, toPath: String) {
        var bos = ByteArrayOutputStream()
        finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos)
        var data = bos.toByteArray()

        val file = File(toPath)
        if (file.exists()) file.delete()
        try {
            val stream = FileOutputStream(file)
            stream.write(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        Log.d(TAG, "onDateSet: dd/mm/yyyy: $dayOfMonth/${monthOfYear + 1}/${year + 543}")
        val date = "$dayOfMonth/${monthOfYear + 1}/${year + 543}"
        txtBirthDate.text = date
    }
}