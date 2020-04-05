package com.cdd.smartsurvey

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cdd.smartsurvey.data.model.Family
import com.cdd.smartsurvey.data.model.WaitingList
import com.cdd.smartsurvey.sqlite.DatabaseHelper
import com.cdd.smartsurvey.sqlite.model.SurveyGroup
import com.cdd.smartsurvey.sqlite.model.SurveyMetric
import com.cdd.smartsurvey.utils.FormUtils
import com.cdd.smartsurvey.utils.ImageUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_survey_sub_master.*

class SurveySubMasterActivity : AppCompatActivity() {
    lateinit var surveyGroup: SurveyGroup
    lateinit var surveyMetricList: List<SurveyMetric>
    lateinit var surveyMetric: SurveyMetric
    var surveyMetricIndex: Int = 0
    lateinit var family: Family
    var familyIndex = 0
    var LinearDetail: LinearLayout? = null
    lateinit var formUtil: FormUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_sub_master)
        val surveyGroupID = intent.getIntExtra(GlobalValue.EXTRA_SURVEY_GROUP_ID, 0)
        surveyMetricIndex = intent.getIntExtra(GlobalValue.EXTRA_SURVEY_METRIC_INDEX, 0)
        familyIndex = intent.getIntExtra(GlobalValue.EXTRA_FAMILY_INDEX, 0)
        loadFamily(familyIndex)
        initView(surveyGroupID)
        formUtil = FormUtils(this@SurveySubMasterActivity, layoutInflater)
        btnBack.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra(GlobalValue.EXTRA_SURVEY_GROUP_ID, surveyGroupID)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        selectQuestion()
    }

    fun initView(surveyGroupID: Int) {
        val db = DatabaseHelper(this)
        surveyGroup = db.getSurveyGroup(surveyGroupID)!!
        surveyMetricList = db.getAllSurveyMetrics(surveyGroup.id.toString())
        btnDetail.setOnClickListener { ShowDetail(surveyGroup.groupName, surveyMetric.metric_description) }
        txtHeaderValue.text = surveyGroup.groupName
        imgIcon.setImageBitmap(ImageUtil.convert(surveyGroup.groupImage))
    }

    fun loadFamily(index: Int) {
        var waitingList = WaitingList()
        val sharedPref = applicationContext.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val waitingJson = sharedPref.getString(getString(R.string.pref_waiting_list), "")
        if (waitingJson != "") {
            waitingList = Gson().fromJson(waitingJson, WaitingList::class.java)
        }

        family = waitingList.familyList[index]
    }

    fun SaveData() {
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

    private fun selectQuestion() {
        surveyMetric = surveyMetricList[surveyMetricIndex]
        val MetricNo = surveyMetric.metric_no
        when (surveyGroup.id.toString()) {
            "1" -> if (MetricNo == "1") setSurvey1()
            else if (MetricNo == "2") setSurvey2()
            else if (MetricNo == "3") setSurvey3()
            else if (MetricNo == "4") setSurvey4()
            else if (MetricNo == "5") setSurvey5()
            else if (MetricNo == "6") setSurvey6()
            else if (MetricNo == "7") setSurvey7()
            "2" -> if (MetricNo == "1") setSurvey8()
            else if (MetricNo == "2") setSurvey9()
            else if (MetricNo == "3") setSurvey10()
            else if (MetricNo == "4") setSurvey11()
            else if (MetricNo == "5") setSurvey12()
            else if (MetricNo == "6") setSurvey13()
            else if (MetricNo == "7") setSurvey14()
            "3" -> if (MetricNo == "1") setSurvey15()
            else if (MetricNo == "2") setSurvey16()
            else if (MetricNo == "3") setSurvey10()
            else if (MetricNo == "4") setSurvey11()
            else if (MetricNo == "5") setSurvey12()
            else if (MetricNo == "6") setSurvey13()
            else if (MetricNo == "7") setSurvey14()
            else if (MetricNo == "8") setSurvey15()
            else if (MetricNo == "9") setSurvey16()
            else if (MetricNo == "10") setSurvey17()
            else if (MetricNo == "11") setSurvey18()
            else if (MetricNo == "12") setSurvey19()
            "4" -> if (MetricNo == "1") setSurvey20()
            else if (MetricNo == "2") setSurvey21()
            else if (MetricNo == "3") setSurvey22()
            else if (MetricNo == "4") setSurvey23()
            "5" -> if (MetricNo == "1") setSurvey24()
            else if (MetricNo == "2") setSurvey25()
            else if (MetricNo == "3") setSurvey26()
            else if (MetricNo == "4") setSurvey27()
            else if (MetricNo == "5") setSurvey28()
            else if (MetricNo == "6") setSurvey29()
            else if (MetricNo == "7") setSurvey30()
            else if (MetricNo == "8") setSurvey31()
        }
    }

    fun findViewInBody(id: Int): View {
        return linearBody.findViewById(id)
    }

    fun setSurvey1() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey1, null)
        linearBody.addView(viewSurvey)
        scrollViewBody.pageScroll(0)
        val txtHeader = viewSurvey.findViewById<TextView>(R.id.txtHeader)
        txtHeader.text = surveyMetric.metric_display
        val radioGroup1 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup1)
        val question2 = viewSurvey.findViewById<View>(R.id.question2)
        val radioGroup2 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup2)
        val btnNext = viewSurvey.findViewById<Button>(R.id.btnNext)

        radioGroup1.setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "1111"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio1_1 -> {
                    formUtil.showDialogInputNumber("มีจำนวน", " ... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "มี $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        radioGroup2.setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "1112"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio2_2 -> {
                    formUtil.showDialogInputNumber("น้อยกว่า", " ... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "น้อยกว่า $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 1
            selectQuestion()
        }
    }

    fun setSurvey2() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey2, null)
        linearBody.addView(viewSurvey)
        scrollViewBody.pageScroll(0)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display
        val radioGroup1 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup1)
        val radioGroup2 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup2)
        val radioGroup3 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup3)
        val radioGroup4 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup4)

        radioGroup1.setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "1121"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio1_1 -> {
                    formUtil.showDialogInputNumber("มีจำนวน", " ... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "มี $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        radioGroup2.setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "1122"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio2_2 -> {
                    formUtil.showDialogInputNumber("ไม่ได้กิน", " ... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "ไม่ได้กิน $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        radioGroup3.setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "1123"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio3_1 -> {
                    formUtil.showDialogInputNumber("มีจำนวน", " ... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "มี $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        radioGroup4.setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "1124"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio4_2 -> {
                    formUtil.showDialogInputNumber("ไม่ได้กิน", " ... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "ไม่ได้กิน $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 0
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 2
            selectQuestion()
        }
    }

    fun setSurvey3() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey3, null)
        linearBody.addView(viewSurvey)
        scrollViewBody.pageScroll(0)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display
        val radioGroup1 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup1)
        val radioGroup2 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup2)

        radioGroup1.setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "1131"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio1_1 -> {
                    formUtil.showDialogInputNumber("จำนวน", "... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "มี $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        radioGroup2.setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "1132"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio2_2 -> {
                    formUtil.showDialogInputNumber("จำนวน", "... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "มี $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 1
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 3
            selectQuestion()
        }
    }

    fun setSurvey4() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey4, null)
        linearBody.addView(viewSurvey)
        scrollViewBody.pageScroll(0)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display
        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 2
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 4
            selectQuestion()
        }
    }

    fun setSurvey5() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey5, null)
        linearBody.addView(viewSurvey)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display
        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 3
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 5
            selectQuestion()
        }
    }

    fun setSurvey6() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey6, null)
        linearBody.addView(viewSurvey)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display
        val checkBox1_1 = findViewInBody(R.id.check1_1) as CheckBox
        val checkBox1_2 = findViewInBody(R.id.check1_2) as CheckBox
        val checkBox1_3 = findViewInBody(R.id.check1_3) as CheckBox
        val checkBox1_4 = findViewInBody(R.id.check1_4) as CheckBox
        val checkBox1_5 = findViewInBody(R.id.check1_5) as CheckBox
        val checkBox2_1 = findViewInBody(R.id.check1_1) as CheckBox
        val checkBox2_2 = findViewInBody(R.id.check1_2) as CheckBox
        val checkBox2_3 = findViewInBody(R.id.check1_3) as CheckBox
        val checkBox2_4 = findViewInBody(R.id.check1_4) as CheckBox
        val checkBox2_5 = findViewInBody(R.id.check2_5) as CheckBox

        val radioGroup4 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup4)
        val radioGroup5 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup5)

        checkBox1_1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                formUtil.showDialogInputNumber("จำนวน", " ... คน") {
                    checkBox1_1.text = "1. ประกันสุขภาพเอกชน $it คน"
                    checkBox1_1.tag = "1,$it"
                }
            }
        }
        checkBox1_2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                formUtil.showDialogInputNumber("จำนวน", " ... คน") {
                    checkBox1_2.text = "2. สิทธิข้าราชการ $it คน"
                    checkBox1_2.tag = "2,$it"
                }
            }
        }
        checkBox1_3.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                formUtil.showDialogInputNumber("จำนวน", " ... คน") {
                    checkBox1_3.text = "3. สิทธิประกันสังคม $it คน"
                    checkBox1_3.tag = "3,$it"
                }
            }
        }
        checkBox1_4.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                formUtil.showDialogInputNumber("จำนวน", " ... คน") {
                    checkBox1_4.text = "4. สิทธิสำนักงานหลักประกันสุขภาพแห่งชาติ $it คน"
                    checkBox1_4.tag = "4,$it"
                }
            }
        }
        checkBox1_5.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                formUtil.showDialogInput("อื่นๆ ... คน", ".......... , ... คน") {
                    checkBox1_5.text = it
                }
            }
        }

        checkBox2_1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                formUtil.showDialogInputNumber("จำนวน", " ... คน") {
                    checkBox2_1.text = "1. สถานีอนามัย/โรงพยาบาลส่งเสริมสุขภาพตำบล $it คน"
                    checkBox2_1.tag = "1,$it"
                }
            }
        }
        checkBox2_2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                formUtil.showDialogInputNumber("จำนวน", " ... คน") {
                    checkBox2_2.text = "2. โรงพยาบาลของรัฐ $it คน"
                    checkBox2_2.tag = "2,$it"
                }
            }
        }
        checkBox2_3.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                formUtil.showDialogInputNumber("จำนวน", " ... คน") {
                    checkBox2_3.text = "3. โรงพยาบาลเอกชน $it คน"
                    checkBox2_3.tag = "3,$it"
                }
            }
        }
        checkBox2_4.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                formUtil.showDialogInputNumber("จำนวน", " ... คน") {
                    checkBox2_4.text = "4. คลินิก $it คน"
                    checkBox2_4.tag = "4,$it"
                }
            }
        }
        checkBox2_5.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                formUtil.showDialogInput("อื่นๆ", "..........") {
                    checkBox1_5.text = "5,$it"
                }
            }
        }

        radioGroup4.setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "64"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio4_2 -> {
                    formUtil.showDialogInputNumber("ไม่ได้รับการตรวจ", "... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "ไม่ได้รับการตรวจ $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        radioGroup5.setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "65"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio5_2 -> {
                    formUtil.showDialogInputNumber("ไม่ได้รับการตรวจ", "... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "ไม่ได้รับการตรวจ $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 4
            selectQuestion()
        }

        findViewInBody(R.id.btnNext).setOnClickListener {
            val listCheck1 = listOf(R.id.check1_1, R.id.check1_2, R.id.check1_3, R.id.check1_4, R.id.check1_5)
            var ans1: ArrayList<String> = ArrayList()
            for (checkItem in listCheck1) {
                val checkBox = findViewInBody(checkItem) as CheckBox
                if (checkBox.isChecked) ans1.add(checkBox.tag.toString())
            }

            family.answer["1161"] = ans1.joinToString("|")

            if (checkBox1_5.isChecked) {
                family.answer["1161"] = family.answer["1161"] + "," + checkBox1_5.text.toString()
            }

            val listCheck2 = listOf(R.id.check2_1, R.id.check2_2, R.id.check2_3, R.id.check2_4, R.id.check2_5)
            var ans2: ArrayList<String> = ArrayList()
            for (checkItem in listCheck2) {
                val checkBox = findViewInBody(checkItem) as CheckBox
                if (checkBox.isChecked) ans2.add(checkBox.tag.toString())
            }
            family.answer["1162"] = ans2.joinToString("#")
            if (checkBox2_5.isChecked) {
                family.answer["1162"] = family.answer["1162"] + "," + checkBox2_5.text.toString()
            }

            surveyMetricIndex = 6
            selectQuestion()
        }
    }

    fun setSurvey7() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey7, null)
        linearBody.addView(viewSurvey)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display
        val checkBox1_1 = findViewInBody(R.id.check1_1) as CheckBox
        val checkBox1_2 = findViewInBody(R.id.check1_2) as CheckBox
        val checkBox1_3 = findViewInBody(R.id.check1_3) as CheckBox
        val checkBox1_4 = findViewInBody(R.id.check1_4) as CheckBox

        checkBox1_2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                formUtil.showDialogInputNumber("จำนวน", " ... คน") {
                    checkBox1_2.text = "ได้ออกกำลังกาย $it คน"
                    checkBox1_2.tag = "2,$it"
                }
            }
        }
        checkBox1_3.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                formUtil.showDialogInputNumber("จำนวน", " ... คน") {
                    checkBox1_3.text = "ได้ออกแรง/ออกกำลัง $it คน"
                    checkBox1_3.tag = "3,$it"
                }
            }
        }
        checkBox1_4.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                formUtil.showDialogInputNumber("จำนวน", " ... คน") {
                    checkBox1_4.text = "ไม่ได้ออกกำลังกายหรือไม่ได้ออกแรง/ไม่ได้ออกกำลัง $it คน"
                    checkBox1_4.tag = "4,$it"
                }
            }
        }

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 5
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            initView(2)
            surveyMetricIndex = 0
            selectQuestion()
        }
    }

    fun setSurvey8() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey8, null)
        linearBody.addView(viewSurvey)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display
        findViewInBody(R.id.btnPrevious).setOnClickListener {
            initView(1)
            surveyMetricIndex = 6
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 1
            selectQuestion()
        }
    }

    fun setSurvey9() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey9, null)
        linearBody.addView(viewSurvey)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display
        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 0
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 2
            selectQuestion()
        }
    }

    fun setSurvey10() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey10, null)
        linearBody.addView(viewSurvey)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display
        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 1
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 3
            selectQuestion()
        }
    }

    fun setSurvey11() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey11, null)
        linearBody.addView(viewSurvey)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display
        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 2
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 4
            selectQuestion()
        }
    }

    fun setSurvey12() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey12, null)
        linearBody.addView(viewSurvey)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display
        val radioGroup7 = findViewInBody(R.id.radioGroup7) as RadioGroup

        radioGroup7.setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "127"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio7_3 -> {
                    formUtil.showDialogInput("อื่นๆ", "..........") {
                        (findViewInBody(checkedId) as RadioButton).text = "$it"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 3
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 5
            selectQuestion()
        }
    }

    fun setSurvey13() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey13, null)
        linearBody.addView(viewSurvey)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display
        val checkBox1_6 = findViewInBody(R.id.check1_6) as CheckBox
        val radioGroup3 = findViewInBody(R.id.radioGroup3) as RadioGroup

        checkBox1_6.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                formUtil.showDialogInput("อื่นๆ", " ..........") {
                    checkBox1_6.text = it
                }
            }
        }

        radioGroup3.setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "133"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio3_2 -> {
                    formUtil.showDialogInputNumber("จำนวน", "... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "มี $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 4
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 6
            selectQuestion()
        }
    }

    fun setSurvey14() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey14, null)
        linearBody.addView(viewSurvey)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display
        val radioGroup3 = findViewInBody(R.id.radioGroup3) as RadioGroup

        radioGroup3.setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "141"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio3_2 -> {
                    formUtil.showDialogInputNumber("มีมูลค่าทรัพย์สิน ที่เสียไป", "... บาท") {
                        (findViewInBody(checkedId) as RadioButton).text = "มีมูลค่าทรัพย์สิน ที่เสียไป $it บาท"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }
        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 5
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            initView(3)
            surveyMetricIndex = 0
            selectQuestion()
        }
    }

    fun setSurvey15() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey15, null)
        linearBody.addView(viewSurvey)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display
        val radioGroup1 = findViewInBody(R.id.radioGroup1) as RadioGroup
        val radioGroup2 = findViewInBody(R.id.radioGroup2) as RadioGroup

        radioGroup1.setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "151"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio1_1 -> {
                    formUtil.showDialogInputNumber("จำนวน", "... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "มี $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        radioGroup2.setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "152"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio2_2 -> {
                    formUtil.showDialogInputNumber("ไม่ได้รับบริการ จำนวน", "... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "ไม่ได้รับบริการ $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            initView(2)
            surveyMetricIndex = 6
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 1
            selectQuestion()
        }
    }

    fun setSurvey16() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey16, null)
        linearBody.addView(viewSurvey)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            initView(2)
            surveyMetricIndex = 6
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 1
            selectQuestion()
        }
    }

    fun setSurvey17() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey17, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey18() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey18, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey19() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey19, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey20() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey20, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey21() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey21, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey22() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey22, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey23() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey23, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey24() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey24, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey25() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey25, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey26() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey26, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey27() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey27, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey28() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey28, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey29() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey29, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey30() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey30, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey31() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey31, null)
        linearBody.addView(viewSurvey)
    }


    fun ShowDetail(Header: String?, Detail: String?) {
        val HeaderDetail: TextView
        val ValueDetail: TextView
        val mBuilder = AlertDialog.Builder(this@SurveySubMasterActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_detail_survey_popup, null)
        HeaderDetail = mView.findViewById<View>(R.id.txtDetailHeader) as TextView
        HeaderDetail.text = Header
        ValueDetail = mView.findViewById<View>(R.id.txtDetailValue) as TextView
        ValueDetail.text = Detail
        mBuilder.setView(mView)
        val show = mBuilder.show()
        LinearDetail = mView.findViewById<View>(R.id.lienarDetail) as LinearLayout
        LinearDetail!!.setOnClickListener { show.dismiss() }
    }
}