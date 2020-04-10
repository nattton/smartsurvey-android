package com.cdd.smartsurvey

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cdd.smartsurvey.adapter.MemberListAdapter
import com.cdd.smartsurvey.data.model.Family
import com.cdd.smartsurvey.data.model.Member
import com.cdd.smartsurvey.data.model.WaitingList
import com.cdd.smartsurvey.utils.FormUtils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_member_survey.*
import kotlinx.android.synthetic.main.activity_member_survey.btnSave
import java.util.ArrayList

class MemberSurveyActivity : AppCompatActivity() {
    lateinit var family: Family
    var familyIndex = 0
    lateinit var adapter: MemberListAdapter
    var LinearDetial: LinearLayout? = null
    var btnDetailMember1: Button? = null
    var btnDetailMember2: Button? = null
    var btnDetailMember3: Button? = null
    var btnDetailMember4: Button? = null
    var btnDetailMember5: Button? = null
    var checkBox6: CheckBox? = null

    @SuppressLint("SourceLockedOrientationActivity", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_survey)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        familyIndex = intent.getIntExtra(GlobalValue.EXTRA_FAMILY_INDEX, 0)
        loadData()

        btnBack.setOnClickListener { onBackPressed() }
        btnDetail.setOnClickListener { ShowDetailMenu() }
        btnSave.setOnClickListener { SendData() }
        btnAdd.setOnClickListener { AddMember() }

        adapter = MemberListAdapter(applicationContext, family.hmember)
        listViewMember.adapter = adapter
    }

    fun SendData() {
        val intent = Intent(this, StartSurveyAcitivity::class.java).apply {
            putExtra(GlobalValue.EXTRA_FAMILY_INDEX, familyIndex)
        }
        startActivity(intent)
    }

    fun AddMember() {
        val formUltis = FormUtils(this@MemberSurveyActivity, layoutInflater)
        var HeaderView: TextView
        val mBuilder = AlertDialog.Builder(this@MemberSurveyActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_aemember_survey, null)
        mBuilder.setView(mView)
        val show = mBuilder.show()
        val rdPeopleUse = mView.findViewById<RadioButton>(R.id.rdPeopleUse)
        rdPeopleUse.setOnClickListener { ShowPeopleCard() }
        val txtPrefix = mView.findViewById<TextView>(R.id.txtPrefix)
        val txtName = mView.findViewById<TextView>(R.id.txtName)
        val txtSurname = mView.findViewById<TextView>(R.id.txtSurname)
        val txtGender = mView.findViewById<TextView>(R.id.txtGender)
        val txtCardID = mView.findViewById<TextView>(R.id.txtCardID)
        val txtBirthDate = mView.findViewById<TextView>(R.id.txtBirthDate)

        txtPrefix.setOnClickListener {
            formUltis.showAlertDialogWithPrefix(txtPrefix, txtGender)
        }
        txtGender.setOnClickListener {
            formUltis.showAlertDialogWithGender(txtGender)
        }


        mView.findViewById<Button>(R.id.btnClose).setOnClickListener { show.dismiss() }
        mView.findViewById<Button>(R.id.btnSave).setOnClickListener {
            var member = Member()
            member.prefix = txtPrefix.tag.toString()
            member.firstname = txtName.text.toString()
            member.lastname = txtSurname.text.toString()
            member.gender = txtGender.tag.toString()
            member.idcard = txtCardID.text.toString()

            family.hmember.add(member)
            show.dismiss()
            adapter.notifyDataSetChanged()
            saveData()
        }
    }

    private fun loadData() {
        var waitingList = WaitingList(ArrayList())
        val sharedPref = applicationContext.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        var waitingJson = sharedPref.getString(getString(R.string.pref_waiting_list), "")
        if (waitingJson != "") {
            waitingList = Gson().fromJson(waitingJson, WaitingList::class.java)
        }
        family = waitingList.familyList[familyIndex]
    }

    private fun saveData() {
        var waitingList = WaitingList()
        val sharedPref = applicationContext.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        var waitingJson = sharedPref.getString(getString(R.string.pref_waiting_list), "")
        if (waitingJson != "") {
            waitingList = Gson().fromJson(waitingJson, WaitingList::class.java)
        }

        waitingList.familyList[familyIndex] = family

        waitingJson = Gson().toJson(waitingList)
        sharedPref.edit().apply {
            putString(getString(R.string.pref_waiting_list), waitingJson)
            apply()
        }
    }

    fun ShowDetailMenu() {
        val mBuilder = AlertDialog.Builder(this@MemberSurveyActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_detail_member_menu_survey, null)
        mBuilder.setView(mView)
        val show = mBuilder.show()
        btnDetailMember1 = mView.findViewById<View>(R.id.btnDetailMember1) as Button
        btnDetailMember1!!.setOnClickListener { ShowDetail(btnDetailMember1!!.text.toString(), GlobalValue.DetailMember1) }
        btnDetailMember2 = mView.findViewById<View>(R.id.btnDetailMember2) as Button
        btnDetailMember2!!.setOnClickListener { ShowDetail(btnDetailMember2!!.text.toString(), GlobalValue.DetailMember2) }
        btnDetailMember3 = mView.findViewById<View>(R.id.btnDetailMember3) as Button
        btnDetailMember3!!.setOnClickListener { ShowDetail(btnDetailMember3!!.text.toString(), GlobalValue.DetailMember3) }
        btnDetailMember4 = mView.findViewById<View>(R.id.btnDetailMember4) as Button
        btnDetailMember4!!.setOnClickListener { ShowDetail(btnDetailMember4!!.text.toString(), GlobalValue.DetailMember4) }
        btnDetailMember5 = mView.findViewById<View>(R.id.btnDetailMember5) as Button
        btnDetailMember5!!.setOnClickListener { ShowDetail(btnDetailMember5!!.text.toString(), GlobalValue.DetailMember5) }
        mView.findViewById<Button>(R.id.btnClose).setOnClickListener { show.dismiss() }
    }

    fun ShowDetail(Header: String?, Detail: String?) {
        val HeaderDetail: TextView
        val ValueDetail: TextView
        val mBuilder = AlertDialog.Builder(this@MemberSurveyActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_detail_survey_popup, null)
        HeaderDetail = mView.findViewById<View>(R.id.txtDetailHeader) as TextView
        HeaderDetail.text = Header
        ValueDetail = mView.findViewById<View>(R.id.txtDetailValue) as TextView
        ValueDetail.text = Detail
        mBuilder.setView(mView)
        val show = mBuilder.show()
        LinearDetial = mView.findViewById<View>(R.id.lienarDetail) as LinearLayout
        LinearDetial!!.setOnClickListener { show.dismiss() }
    }

    fun ShowPeopleCard() {
        val mBuilder = AlertDialog.Builder(this@MemberSurveyActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_people_card_survey, null)
        mBuilder.setView(mView)
        val show = mBuilder.show()
        checkBox6 = mView.findViewById<View>(R.id.checkBox6) as CheckBox
        checkBox6!!.setOnClickListener { ShowPeopleCardDetail() }
        mView.findViewById<Button>(R.id.btnClose).setOnClickListener { show.dismiss() }
    }

    fun ShowPeopleCardDetail() {
        val mBuilder = AlertDialog.Builder(this@MemberSurveyActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_people_card_detail_survey, null)
        mBuilder.setView(mView)
        val show = mBuilder.show()
        mView.findViewById<Button>(R.id.btnClose).setOnClickListener { show.dismiss() }
    }
}