package com.cdd.smartsurvey.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.cdd.smartsurvey.R
import com.cdd.smartsurvey.sqlite.DatabaseHelper
import com.cdd.smartsurvey.sqlite.model.*
import java.util.*
import kotlin.collections.ArrayList

class FormUtils(var context: Context, var layoutInflater: LayoutInflater) {

    fun showDialogInputNumber(question: String, hint: String, inputListener: (String) -> Unit) {
        val mBuilder = AlertDialog.Builder(context)
        val mView = layoutInflater.inflate(R.layout.dialog_input_number, null)
        mView.findViewById<TextView>(R.id.textQuestion).text = question
        val editTextInput = mView.findViewById<EditText>(R.id.editTextInput)
        editTextInput.hint = hint
        val btnOK = mView.findViewById<Button>(R.id.btnOK)
        mBuilder.setView(mView)
        val show = mBuilder.show()
        btnOK.setOnClickListener {
            show.dismiss()
            inputListener(editTextInput.text.toString())
        }
    }

    fun showDialogInput2(layoutId: Int, question: String, hint: String, hint2: String, inputListener: (String, String) -> Unit) {
        val mBuilder = AlertDialog.Builder(context)
        val mView = layoutInflater.inflate(layoutId, null)
        mView.findViewById<TextView>(R.id.textQuestion).text = question
        val editTextInput = mView.findViewById<EditText>(R.id.editTextInput)
        val editTextInput2 = mView.findViewById<EditText>(R.id.editTextInput2)
        editTextInput.hint = hint
        editTextInput2.hint = hint2
        val btnOK = mView.findViewById<Button>(R.id.btnOK)
        mBuilder.setView(mView)
        val show = mBuilder.show()
        btnOK.setOnClickListener {
            show.dismiss()
            inputListener(editTextInput.text.toString(), editTextInput2.text.toString())
        }
    }

    fun showDialogInput2Number(question: String, hint: String, hint2: String, inputListener: (String, String) -> Unit) {
        showDialogInput2(R.layout.dialog_input_2number, question, hint, hint2, inputListener)
    }

    fun showDialogInputNumberText(question: String, hint: String, hint2: String, inputListener: (String, String) -> Unit) {
        showDialogInput2(R.layout.dialog_input_number_text, question, hint, hint2, inputListener)
    }

    fun showDialogInputTextNumber(question: String, hint: String, hint2: String, inputListener: (String, String) -> Unit) {
        showDialogInput2(R.layout.dialog_input_text_number, question, hint, hint2, inputListener)
    }


    fun showDialogInput(question: String, hint: String, inputListener: (String) -> Unit) {
        val mBuilder = AlertDialog.Builder(context)
        val mView = layoutInflater.inflate(R.layout.dialog_input, null)
        mView.findViewById<TextView>(R.id.textQuestion).text = question
        val editTextInput = mView.findViewById<EditText>(R.id.editTextInput)
        editTextInput.hint = hint
        val btnOK = mView.findViewById<Button>(R.id.btnOK)
        mBuilder.setView(mView)
        val show = mBuilder.show()
        btnOK.setOnClickListener {
            show.dismiss()
            inputListener(editTextInput.text.toString())
        }
    }

    fun showAlertDialogWithPrefix(txtPrefix: TextView, txtGender: TextView) {
        val txtSearch: EditText
        val db: DatabaseHelper
        val prefixList: MutableList<Prefix> = ArrayList()
        db = DatabaseHelper(context)
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
        val mBuilder = AlertDialog.Builder(context)
        val mView = layoutInflater.inflate(R.layout.dialog_prefix_valuelist, null)
        val prefixAdapter = SimpleAdapter(context, itemDataList, android.R.layout.simple_list_item_2, arrayOf("codename"), intArrayOf(android.R.id.text1))
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
        list.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, index, l ->
            val clickItemObj = adapterView.adapter.getItem(index)
            val clickItemMap = clickItemObj as HashMap<*, *>
            txtPrefix.tag = clickItemMap["code"]
            txtPrefix.text = clickItemMap["codename"] as String?
            if (txtPrefix.tag == "001" || txtPrefix!!.tag == "002") {
                txtGender.tag = "1"
                txtGender.text = "ชาย (male)"
            } else {
                txtGender.tag = "2"
                txtGender.text = "หญิง (Female)"
            }
            show.dismiss()
        }
        val btnClose = mView.findViewById<Button>(R.id.btnClose)
        btnClose.setOnClickListener { show.dismiss() }
    }

    fun showAlertDialogWithGender(txtGender: TextView) {
        val txtSearch: EditText
        val genderList: MutableList<Gender> = ArrayList()
        val db: DatabaseHelper
        db = DatabaseHelper(context)
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
        val mBuilder = AlertDialog.Builder(context)
        val mView = layoutInflater.inflate(R.layout.dialog_gender_valuelist, null)
        val genderAdapter = SimpleAdapter(context, itemDataList, android.R.layout.simple_list_item_2, arrayOf("codename"), intArrayOf(android.R.id.text1))
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
        list.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, index, l ->
            val clickItemObj = adapterView.adapter.getItem(index)
            val clickItemMap = clickItemObj as HashMap<*, *>
            txtGender.tag = clickItemMap["code"]
            txtGender.text = clickItemMap["codename"] as String?
            show.dismiss()
        }
        var btnClose = mView.findViewById<View>(R.id.btnClose)
        btnClose!!.setOnClickListener { show.dismiss() }
    }

    fun showAlertDialogWithProvince(txtProvince: TextView, txtAmphur: TextView, txtTumbon: TextView, txtCommunity: TextView) {
        val txtSearch: EditText
        val provinceList: MutableList<Province> = java.util.ArrayList()
        val db: DatabaseHelper
        db = DatabaseHelper(context)
        provinceList.addAll(db.allProvinces)
        var item: Province
        val itemDataList = java.util.ArrayList<Map<String, Any?>>()
        for (i in provinceList.indices) {
            item = provinceList[i]
            val listItemMap: MutableMap<String, Any?> = HashMap()
            listItemMap["code"] = item.code
            listItemMap["codename"] = item.codename
            itemDataList.add(listItemMap)
        }
        val mBuilder = AlertDialog.Builder(context)
        val mView = layoutInflater.inflate(R.layout.dialog_province_valuelist, null)
        val provinceAdapter = SimpleAdapter(context, itemDataList, android.R.layout.simple_list_item_2, arrayOf("codename"), intArrayOf(android.R.id.text1))
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
        list.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, index, l ->
            val clickItemObj = adapterView.adapter.getItem(index)
            val clickItemMap = clickItemObj as HashMap<*, *>
            txtProvince.tag = clickItemMap["code"]
            txtProvince.text = clickItemMap["codename"] as String?
            show.dismiss()
        }
        val btnClose = mView.findViewById<View>(R.id.btnClose) as Button
        btnClose.setOnClickListener { show.dismiss() }
        txtAmphur.tag = ""
        txtAmphur.text = ""
        txtTumbon.tag = ""
        txtTumbon.text = ""
        txtCommunity.tag = ""
        txtCommunity.text = ""
    }

    fun showAlertDialogWithAmphur(txtProvince: TextView, txtAmphur: TextView, txtTumbon: TextView, txtCommunity: TextView) {
        val txtSearch: EditText
        val amphurList: MutableList<Amphur> = java.util.ArrayList()
        val db: DatabaseHelper
        db = DatabaseHelper(context)
        amphurList.addAll(db.getAllAmphurs(if (txtProvince.tag == null) "0" else txtProvince.tag.toString()))
        var item: Amphur
        val itemDataList = java.util.ArrayList<Map<String, Any?>>()
        for (i in amphurList.indices) {
            item = amphurList[i]
            val listItemMap: MutableMap<String, Any?> = HashMap()
            listItemMap["code"] = item.code
            listItemMap["codename"] = item.codename
            itemDataList.add(listItemMap)
        }
        val mBuilder = AlertDialog.Builder(context)
        val mView = layoutInflater.inflate(R.layout.dialog_amphur_valuelist, null)
        val amphurAdapter = SimpleAdapter(context, itemDataList, android.R.layout.simple_list_item_2, arrayOf("codename"), intArrayOf(android.R.id.text1))
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
        list.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, index, l ->
            val clickItemObj = adapterView.adapter.getItem(index)
            val clickItemMap = clickItemObj as HashMap<*, *>
            txtAmphur.tag = clickItemMap["code"]
            txtAmphur.text = clickItemMap["codename"] as String?
            show.dismiss()
        }
        val btnClose = mView.findViewById<View>(R.id.btnClose) as Button
        btnClose.setOnClickListener { show.dismiss() }
        txtTumbon.tag = ""
        txtTumbon.text = ""
        txtCommunity.tag = ""
        txtCommunity.text = ""
    }

    fun showAlertDialogWithTumbon(txtProvince: TextView, txtAmphur: TextView, txtTumbon: TextView, txtCommunity: TextView) {
        val txtSearch: EditText
        val tumbonList: MutableList<Tumbon> = java.util.ArrayList()
        val db: DatabaseHelper
        db = DatabaseHelper(context)
        tumbonList.addAll(db.getAllTumbons(if (txtAmphur.tag == null) "0" else txtAmphur.tag.toString()))
        var item: Tumbon
        val itemDataList = java.util.ArrayList<Map<String, Any?>>()
        for (i in tumbonList.indices) {
            item = tumbonList[i]
            val listItemMap: MutableMap<String, Any?> = HashMap()
            listItemMap["code"] = item.code
            listItemMap["codename"] = item.codename
            itemDataList.add(listItemMap)
        }
        val mBuilder = AlertDialog.Builder(context)
        val mView = layoutInflater.inflate(R.layout.dialog_tumbon_valuelist, null)
        val tumbonAdapter = SimpleAdapter(context, itemDataList, android.R.layout.simple_list_item_2, arrayOf("codename"), intArrayOf(android.R.id.text1))
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
        list.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, index, l ->
            val clickItemObj = adapterView.adapter.getItem(index)
            val clickItemMap = clickItemObj as HashMap<*, *>
            txtTumbon.tag = clickItemMap["code"]
            txtTumbon.text = clickItemMap["codename"] as String?
            show.dismiss()
        }
        val btnClose = mView.findViewById<View>(R.id.btnClose) as Button
        btnClose.setOnClickListener { show.dismiss() }
        txtCommunity.tag = ""
        txtCommunity.text = ""
    }

    fun showAlertDialogWithCommunity(txtTumbon: TextView, txtCommunity: TextView) {
        val txtSearch: EditText
        val communityList: MutableList<Community> = java.util.ArrayList()
        val db: DatabaseHelper
        db = DatabaseHelper(context)
        communityList.addAll(db.getAllCommunitys(if (txtTumbon!!.tag == null) "0" else txtTumbon!!.tag.toString()))
        var item: Community
        val itemDataList = java.util.ArrayList<Map<String, Any?>>()
        for (i in communityList.indices) {
            item = communityList[i]
            val listItemMap: MutableMap<String, Any?> = HashMap()
            listItemMap["code"] = item.code
            listItemMap["moo"] = item.moo
            listItemMap["codename"] = "หมู่ที่ " + item.moo + " " + item.codename
            itemDataList.add(listItemMap)
        }
        val mBuilder = AlertDialog.Builder(context)
        val mView = layoutInflater.inflate(R.layout.dialog_community_valuelist, null)
        val communityAdapter = SimpleAdapter(context, itemDataList, android.R.layout.simple_list_item_2, arrayOf("codename"), intArrayOf(android.R.id.text1))
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
        list.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, index, l ->
            val clickItemObj = adapterView.adapter.getItem(index)
            val clickItemMap = clickItemObj as HashMap<*, *>
            txtCommunity.tag = clickItemMap["code"]
            txtCommunity.text = clickItemMap["codename"] as String?
            show.dismiss()
        }
        val btnClose = mView.findViewById<View>(R.id.btnClose) as Button
        btnClose.setOnClickListener { show.dismiss() }
    }
}