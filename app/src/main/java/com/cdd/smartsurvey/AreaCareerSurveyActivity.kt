package com.cdd.smartsurvey

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cdd.smartsurvey.http.model.Family
import com.cdd.smartsurvey.http.model.WaitingList
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_area_career_survey.*
import java.util.ArrayList

class AreaCareerSurveyActivity : AppCompatActivity() {
    lateinit var family: Family

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_career_survey)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        family = intent.getParcelableExtra(GlobalValue.EXTRA_FAMILY)

        btnBack.setOnClickListener { onBackPressed() }
        btnSave.setOnClickListener { SaveData() }
        radioButton1.setOnClickListener {
            family.hjob = "1"
            ShowRadioMyself()
        }
        radioButton2.setOnClickListener {
            family.hjob = "2"
            ShowRadioAreaData("เช่า")
        }
        radioButton3.setOnClickListener {
            family.hjob = "3"
            ShowRadioAreaData("ที่สาธารณะ")
        }
        radioButton4.setOnClickListener {
            family.hjob = "4"
            ShowRadioAreaOther()
        }
        radioButton5.setOnClickListener {
            family.hjob = "5"
        }
    }

    fun SaveData() {
        var waitingList = WaitingList(ArrayList())
        val sharedPref = applicationContext.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        var waitingJson = sharedPref.getString(getString(R.string.pref_waiting_list), "")
        if (waitingJson != "") {
            waitingList = Gson().fromJson(waitingJson, WaitingList::class.java)
        }

        waitingList.familyList.add(family)

        waitingJson = Gson().toJson(waitingList)
        sharedPref.edit().apply {
            putString(getString(R.string.pref_waiting_list), waitingJson)
            apply()
        }

        val intent = Intent(this, MemberSurveyActivity::class.java).apply {
//            putExtra(GlobalValue.EXTRA_FAMILY, family)
            putExtra(GlobalValue.EXTRA_FAMILY_INDEX, waitingList.familyList.lastIndex)
        }
        startActivity(intent)
    }

    fun ShowRadioMyself() {
        val mBuilder = AlertDialog.Builder(this@AreaCareerSurveyActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_area_myself_survey, null)
        mBuilder.setView(mView)
        val show = mBuilder.show()
        mView.findViewById<Button>(R.id.btnClose).setOnClickListener { show.dismiss() }
        mView.findViewById<Button>(R.id.btnSave).setOnClickListener {
            val farm = mView.findViewById<EditText>(R.id.txtAreaFarm).text
            val wah = mView.findViewById<EditText>(R.id.txtAreaWah).text
            val type = mView.findViewById<EditText>(R.id.txtOwnerShip).text
            family.hjob = "${family.hjob},$farm,$wah,$type"
            show.dismiss()
            SaveData()
        }
    }

    fun ShowRadioAreaData(HeaderData: String?) {
        val mBuilder = AlertDialog.Builder(this@AreaCareerSurveyActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_area_data_survey, null)
        mBuilder.setView(mView)
        val show = mBuilder.show()
        val HeaderView: TextView = mView.findViewById(R.id.txtDialogVies)
        HeaderView.text = HeaderData
        mView.findViewById<Button>(R.id.btnClose).setOnClickListener { show.dismiss() }
        mView.findViewById<Button>(R.id.btnSave).setOnClickListener {
            val farm = mView.findViewById<EditText>(R.id.txtAreaFarm).text
            val wah = mView.findViewById<EditText>(R.id.txtAreaWah).text
            family.hjob = "${family.hjob},$farm,$wah"
            show.dismiss()
            SaveData()
        }
    }

    fun ShowRadioAreaOther() {
        val mBuilder = AlertDialog.Builder(this@AreaCareerSurveyActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_area_other_survey, null)
        mBuilder.setView(mView)
        val show = mBuilder.show()
        mView.findViewById<Button>(R.id.btnClose).setOnClickListener { show.dismiss() }
        mView.findViewById<Button>(R.id.btnSave).setOnClickListener {
            val farm = mView.findViewById<EditText>(R.id.txtAreaFarm).text
            val wah = mView.findViewById<EditText>(R.id.txtAreaWah).text
            val type = mView.findViewById<EditText>(R.id.txtOwnerShip).text
            family.hjob = "${family.hjob},$farm,$wah,${type}"
            show.dismiss()
            SaveData()
        }
    }
}