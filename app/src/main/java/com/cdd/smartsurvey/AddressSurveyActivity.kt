package com.cdd.smartsurvey

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cdd.smartsurvey.data.model.Family
import com.cdd.smartsurvey.sqlite.DatabaseHelper
import com.cdd.smartsurvey.sqlite.model.Amphur
import com.cdd.smartsurvey.sqlite.model.Community
import com.cdd.smartsurvey.sqlite.model.Province
import com.cdd.smartsurvey.sqlite.model.Tumbon
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_address_survey.*
import java.util.*

class AddressSurveyActivity : AppCompatActivity() {
    var btnClose: Button? = null

    lateinit var family: Family

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_survey)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        family = intent.getParcelableExtra(GlobalValue.EXTRA_FAMILY)

        btnBack.setOnClickListener { onBackPressed() }
        rdHomeUse.setOnClickListener { txtHomeNo!!.visibility = View.VISIBLE }
        rdHomeNotUse!!.setOnClickListener { txtHomeNo.visibility = View.INVISIBLE }
        txtProvince.setOnClickListener { ShowAlertDialogWithProvince_Listview() }
        txtAmphur.setOnClickListener { ShowAlertDialogWithAmphur_Listview() }
        txtTumbon.setOnClickListener { ShowAlertDialogWithTumbon_Listview() }
        txtCommunity.setOnClickListener { ShowAlertDialogWithCommunity_Listview() }
        btnSave.setOnClickListener { StoreData() }

        val selectCommunity: com.cdd.smartsurvey.http.model.Community = intent.getParcelableExtra(GlobalValue.EXTRA_COMMUNITY)
        if (selectCommunity.community_id != "" && selectCommunity.community_id != "0") {
            txtProvince.text = selectCommunity.province_name
            txtProvince.tag = selectCommunity.province_code
            txtProvince.isEnabled = false
            txtAmphur.text = selectCommunity.amphur_name
            txtAmphur.tag = selectCommunity.amphur_code
            txtAmphur.isEnabled = false
            txtTumbon.text = selectCommunity.tumbon_name
            txtTumbon.tag = selectCommunity.tumbon_code
            txtTumbon.isEnabled = false
            txtCommunity.text = selectCommunity.community_name
            txtCommunity.tag = selectCommunity.community_id
            txtCommunity.isEnabled = false
        }
    }

    fun StoreData() {
        family.hid = txtHomeNo.text.toString()
        family.haddr = txtMoo.text.toString() + "," + txtSoi.text.toString() + "," + txtRoad.text.toString()
        family.htumbon = txtTumbon.tag.toString()
        family.hamphur = txtAmphur.tag.toString()
        family.hprovince = txtProvince.tag.toString()

        val jsonData = Gson().toJson(family)
        Log.d("JsonData", jsonData)
        val intent = Intent(this, LocalGovSurveyActivity::class.java).apply {
            putExtra(GlobalValue.EXTRA_FAMILY, family)
        }
        startActivity(intent)
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
        val mBuilder = AlertDialog.Builder(this@AddressSurveyActivity)
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
        btnClose = mView.findViewById<View>(R.id.btnClose) as Button
        btnClose!!.setOnClickListener { show.dismiss() }
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
        val mBuilder = AlertDialog.Builder(this@AddressSurveyActivity)
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
        btnClose = mView.findViewById<View>(R.id.btnClose) as Button
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
        val mBuilder = AlertDialog.Builder(this@AddressSurveyActivity)
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
        btnClose = mView.findViewById<View>(R.id.btnClose) as Button
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
        val mBuilder = AlertDialog.Builder(this@AddressSurveyActivity)
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
        btnClose = mView.findViewById<View>(R.id.btnClose) as Button
        btnClose!!.setOnClickListener { show.dismiss() }
    }
}