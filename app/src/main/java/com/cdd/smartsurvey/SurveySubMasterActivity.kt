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
        btnDetail.setOnClickListener { showDetail(surveyGroup.groupName, surveyMetric.metric_description) }
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
        scrollViewBody.smoothScrollTo(0, 0)
        linearBody.removeAllViews()
        surveyMetric = surveyMetricList[surveyMetricIndex]
        val metricNo = surveyMetric.metric_no.toInt()
        when (surveyGroup.id) {
            1 -> when (metricNo) {
                1 -> setSurvey1()
                2 -> setSurvey2()
                3 -> setSurvey3()
                4 -> setSurvey4()
                5 -> setSurvey5()
                6 -> setSurvey6()
                7 -> setSurvey7()
            }
            2 -> when (metricNo) {
                1 -> setSurvey8()
                2 -> setSurvey9()
                3 -> setSurvey10()
                4 -> setSurvey11()
                5 -> setSurvey12()
                6 -> setSurvey13()
                7 -> setSurvey14()
            }
            3 -> when (metricNo) {
                1 -> setSurvey15()
                2 -> setSurvey16()
                3 -> setSurvey17()
                4 -> setSurvey18()
                5 -> setSurvey19()
            }
            4 -> when (metricNo) {
                1 -> setSurvey20()
                2 -> setSurvey21()
                3 -> setSurvey22()
                4 -> setSurvey23()
            }
            5 -> when (metricNo) {
                1 -> setSurvey24()
                2 -> setSurvey25()
                3 -> setSurvey26()
                4 -> setSurvey27()
                5 -> setSurvey28()
                6 -> setSurvey29()
                7 -> setSurvey30()
                8 -> setSurvey31()
            }
        }

        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display


    }

    fun findViewInBody(id: Int): View {
        return linearBody.findViewById(id)
    }

    private fun setCheckBox(questionNo: String, checkBoxID: Int) {
        val checkBox = findViewInBody(checkBoxID) as CheckBox
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) family.answer[questionNo] = "1"
            else family.answer.remove(questionNo)

        }
    }

    private fun setCheckBoxInput(questionNo: String, checkBoxID: Int, question: String, hint: String, formatTemplate: String) {
        val checkBox = findViewInBody(checkBoxID) as CheckBox
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (question != "")
                    formUtil.showDialogInputNumber(question, hint) {
                        checkBox.text = formatTemplate.format(it)
                        family.answer[questionNo] = "1,$it"
                    }
                else family.answer[questionNo] = "1"
            } else {
                family.answer.remove(questionNo)
            }
        }
    }

    private fun setCheckBoxInputNumber(questionNo: String, checkBoxID: Int, question: String, hint: String, formatTemplate: String) {
        val checkBox = findViewInBody(checkBoxID) as CheckBox
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (question != "")
                    formUtil.showDialogInputNumber(question, hint) {
                        checkBox.text = formatTemplate.format(it)
                        family.answer[questionNo] = "1,$it"
                    }
                else family.answer[questionNo] = "1"
            } else {
                family.answer.remove(questionNo)
            }
        }
    }


    private fun setRadioGroupTrueFalse(questionNo: String, radioGroupId: Int, trueRadioId: Int) {
        (findViewInBody(radioGroupId) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            family.answer[questionNo] = if (checkedId == trueRadioId) "1" else "0"
            SaveData()
        }
    }


    private fun setSurvey1() {
        layoutInflater.inflate(R.layout.survey1, linearBody, true)

        (findViewInBody(R.id.radioGroup1) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "1111"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio1_1 -> {
                    formUtil.showDialogInputNumber("มีจำนวน", " ... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "มี $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                R.id.radio1_2 -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        (findViewInBody(R.id.radioGroup2) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "1121"
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

    private fun setSurvey2() {
        layoutInflater.inflate(R.layout.survey2, linearBody, true)

        (findViewInBody(R.id.radioGroup1) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "1211"
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

        (findViewInBody(R.id.radioGroup2) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "1221"
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

        (findViewInBody(R.id.radioGroup3) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "1231"
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

        (findViewInBody(R.id.radioGroup4) as RadioGroup).setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "1241"
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

    private fun setSurvey3() {
        layoutInflater.inflate(R.layout.survey3, linearBody, true)

        (findViewInBody(R.id.radioGroup1) as RadioGroup).setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "1311"
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

        (findViewInBody(R.id.radioGroup2) as RadioGroup).setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "1321"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio2_2 -> {
                    formUtil.showDialogInputNumber("จำนวน", "... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "มี $it คน"
                        family.answer[questionNo] = "1,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }
        setRadioGroupTrueFalse("1331", R.id.radioGroup3_1, R.id.radio3_1_1)
        setRadioGroupTrueFalse("1332", R.id.radioGroup3_2, R.id.radio3_2_1)
        setRadioGroupTrueFalse("1333", R.id.radioGroup3_3, R.id.radio3_3_1)
        setRadioGroupTrueFalse("1334", R.id.radioGroup3_4, R.id.radio3_4_1)
        setRadioGroupTrueFalse("1335", R.id.radioGroup3_5, R.id.radio3_5_1)
        setRadioGroupTrueFalse("1336", R.id.radioGroup3_6, R.id.radio3_6_1)
        setRadioGroupTrueFalse("1337", R.id.radioGroup3_7, R.id.radio3_7_1)
        setRadioGroupTrueFalse("1338", R.id.radioGroup3_8, R.id.radio3_8_1)
        setRadioGroupTrueFalse("1339", R.id.radioGroup3_9, R.id.radio3_9_1)
        setRadioGroupTrueFalse("13310", R.id.radioGroup3_10, R.id.radio3_10_1)

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 1
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 3
            selectQuestion()
        }
    }

    private fun setSurvey4() {
        layoutInflater.inflate(R.layout.survey4, linearBody, true)

        setRadioGroupTrueFalse("1411", R.id.radioGroup1, R.id.radio1_1)
        setRadioGroupTrueFalse("1421", R.id.radioGroup2, R.id.radio2_1)
        setRadioGroupTrueFalse("1431", R.id.radioGroup3, R.id.radio3_1)
        setRadioGroupTrueFalse("1441", R.id.radioGroup4, R.id.radio4_1)

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 2
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 4
            selectQuestion()
        }
    }

    private fun setSurvey5() {
        layoutInflater.inflate(R.layout.survey5, linearBody, true)

        setRadioGroupTrueFalse("1511", R.id.radioGroup1, R.id.radio1_1)
        setRadioGroupTrueFalse("1521", R.id.radioGroup2, R.id.radio2_1)
        setRadioGroupTrueFalse("1531", R.id.radioGroup3, R.id.radio3_1)
        setRadioGroupTrueFalse("1541", R.id.radioGroup4, R.id.radio4_1)

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 3
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 5
            selectQuestion()
        }
    }

    private fun setSurvey6() {
        layoutInflater.inflate(R.layout.survey6, linearBody, true)

        setCheckBoxInputNumber("1611", R.id.check1_1, "จำนวน", " ... คน", "1. ประกันสุขภาพเอกชน %s คน")
        setCheckBoxInputNumber("1612", R.id.check1_2, "จำนวน", " ... คน", "2. สิทธิข้าราชการ %s คน")
        setCheckBoxInputNumber("1613", R.id.check1_3, "จำนวน", " ... คน", "3. สิทธิประกันสังคม %s คน")
        setCheckBoxInputNumber("1614", R.id.check1_4, "จำนวน", " ... คน", "4. สิทธิสำนักงานหลักประกันสุขภาพแห่งชาติ %s คน")
        setCheckBoxInput("1615", R.id.check1_5, "อื่นๆ ... คน", " .......... , ... คน", "อื่นๆ ... %s คน")
        setCheckBoxInputNumber("1621", R.id.check2_1, "จำนวน", " ... คน", "1. สถานีอนามัย/โรงพยาบาลส่งเสริมสุขภาพตำบล %s คน")
        setCheckBoxInputNumber("1622", R.id.check2_2, "จำนวน", " ... คน", "2. โรงพยาบาลของรัฐ %s คน")
        setCheckBoxInputNumber("1623", R.id.check2_3, "จำนวน", " ... คน", "3. โรงพยาบาลเอกชน %s คน")
        setCheckBoxInputNumber("1624", R.id.check2_4, "จำนวน", " ... คน", "4. คลินิก %s คน")
        setCheckBoxInput("1625", R.id.check2_5, "อื่นๆ ... คน", " .......... , ... คน", "อื่นๆ ... %s คน")

        (findViewInBody(R.id.radioGroup3) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "1631"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio3_1 -> {
                    formUtil.showDialogInputNumber("จำนวน", "... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "มี $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        (findViewInBody(R.id.radioGroup4) as RadioGroup).setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "1641"
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

        (findViewInBody(R.id.radioGroup5) as RadioGroup).setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "1651"
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
            surveyMetricIndex = 6
            selectQuestion()
        }
    }

    private fun setSurvey7() {
        layoutInflater.inflate(R.layout.survey7, linearBody, true)

        setCheckBoxInput("1711", R.id.check1_1, "", "", "")
        setCheckBoxInputNumber("1712", R.id.check1_2, "จำนวน", " ... คน", "ได้ออกกำลังกาย %s คน")
        setCheckBoxInputNumber("1713", R.id.check1_3, "จำนวน", " ... คน", "ได้ออกแรง/ออกกำลัง %s คน")
        setCheckBoxInputNumber("1714", R.id.check1_4, "จำนวน", " ... คน", "ไม่ได้ออกกำลังกายหรือไม่ได้ออกแรง/ไม่ได้ออกกำลัง %s คน")

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

    private fun setSurvey8() {
        layoutInflater.inflate(R.layout.survey8, linearBody, true)

        setRadioGroupTrueFalse("2811", R.id.radioGroup1, R.id.radio1_1)
        setRadioGroupTrueFalse("2821", R.id.radioGroup2, R.id.radio2_1)

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

    private fun setSurvey9() {
        layoutInflater.inflate(R.layout.survey9, linearBody, true)

        setRadioGroupTrueFalse("2911", R.id.radioGroup1, R.id.radio1_1)

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 0
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 2
            selectQuestion()
        }
    }

    private fun setSurvey10() {
        layoutInflater.inflate(R.layout.survey10, linearBody, true)

        setRadioGroupTrueFalse("21011", R.id.radioGroup1, R.id.radio1_1)

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 1
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 3
            selectQuestion()
        }
    }

    private fun setSurvey11() {
        layoutInflater.inflate(R.layout.survey11, linearBody, true)

        setRadioGroupTrueFalse("21111", R.id.radioGroup1, R.id.radio1_1)
        setRadioGroupTrueFalse("21121", R.id.radioGroup2, R.id.radio2_1)
        setRadioGroupTrueFalse("21131", R.id.radioGroup3, R.id.radio3_1)
        setRadioGroupTrueFalse("21141", R.id.radioGroup4, R.id.radio4_1)
        setRadioGroupTrueFalse("21151", R.id.radioGroup5_1, R.id.radio5_1_1)
        setRadioGroupTrueFalse("21152", R.id.radioGroup5_2, R.id.radio5_2_1)
        setRadioGroupTrueFalse("21153", R.id.radioGroup5_3, R.id.radio5_3_1)
        setRadioGroupTrueFalse("21154", R.id.radioGroup5_4, R.id.radio5_4_1)
        setRadioGroupTrueFalse("21161", R.id.radioGroup6, R.id.radio6_1)
        setRadioGroupTrueFalse("21171", R.id.radioGroup7, R.id.radio7_1)
        setRadioGroupTrueFalse("21181", R.id.radioGroup8, R.id.radio8_1)

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 2
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 4
            selectQuestion()
        }
    }

    private fun setSurvey12() {
        layoutInflater.inflate(R.layout.survey12, linearBody, true)

        setRadioGroupTrueFalse("21211", R.id.radioGroup1, R.id.radio1_2)
        setRadioGroupTrueFalse("21221", R.id.radioGroup2, R.id.radio2_2)
        setRadioGroupTrueFalse("21231", R.id.radioGroup3, R.id.radio3_2)
        setRadioGroupTrueFalse("21241", R.id.radioGroup4, R.id.radio4_2)
        setRadioGroupTrueFalse("21251", R.id.radioGroup5, R.id.radio5_2)
        setRadioGroupTrueFalse("21261", R.id.radioGroup6, R.id.radio6_2)
        (findViewInBody(R.id.radioGroup7) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "21271"
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

    private fun setSurvey13() {
        layoutInflater.inflate(R.layout.survey13, linearBody, true)

        setRadioGroupTrueFalse("21311", R.id.radioGroup1_1, R.id.radio1_1_1)
        setRadioGroupTrueFalse("21312", R.id.radioGroup1_2, R.id.radio1_2_1)
        setRadioGroupTrueFalse("21313", R.id.radioGroup1_3, R.id.radio1_3_1)
        setRadioGroupTrueFalse("21314", R.id.radioGroup1_4, R.id.radio1_4_1)
        setRadioGroupTrueFalse("21321", R.id.radioGroup2, R.id.radio2_1)
        setCheckBox("313221", R.id.checkBox1_1)
        setCheckBox("313221", R.id.checkBox1_2)
        setCheckBox("313221", R.id.checkBox1_3)
        setCheckBox("313221", R.id.checkBox1_4)
        setCheckBox("313221", R.id.checkBox1_5)
        setCheckBoxInput("313221", R.id.checkBox1_6, "อื่นๆ", "......", "อื่นๆ ... %s")

        (findViewInBody(R.id.radioGroup3) as RadioGroup).setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "21331"
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

    private fun setSurvey14() {
        layoutInflater.inflate(R.layout.survey14, linearBody, true)

        setRadioGroupTrueFalse("21411", R.id.radioGroup1, R.id.radio1_2)
        setRadioGroupTrueFalse("21421", R.id.radioGroup2, R.id.radio2_2)

        (findViewInBody(R.id.radioGroup3) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "21431"
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

        setRadioGroupTrueFalse("21451", R.id.radioGroup5, R.id.radio2_2)
        setRadioGroupTrueFalse("21461", R.id.radioGroup6, R.id.radio6_2)


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

    private fun setSurvey15() {
        layoutInflater.inflate(R.layout.survey15, linearBody, true)

        (findViewInBody(R.id.radioGroup1) as RadioGroup).setOnCheckedChangeListener { group, checkedId ->
            val questionNo = "31511"
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

        (findViewInBody(R.id.radioGroup2) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "31521"
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

    private fun setSurvey16() {
        layoutInflater.inflate(R.layout.survey16, linearBody, true)

        (findViewInBody(R.id.radioGroup1) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "31611"
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

        (findViewInBody(R.id.radioGroup2) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "31621"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio2_2 -> {
                    formUtil.showDialogInputNumber("ไม่ได้เรียน จำนวน", "... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "ไม่ได้เรียน $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        (findViewInBody(R.id.radioGroup3) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "31631"
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

        setCheckBoxInputNumber("31641", R.id.checkBox4_1, "จำนวน", " ... คน", "เรียนต่อ กศน/การศึกษาผู้ใหญ่  %s คน")
        setCheckBoxInputNumber("31642", R.id.checkBox4_2, "จำนวน", " ... คน", "ศึกษาเองที่บ้าน (โฮมสคูล) %s คน")
        setCheckBoxInputNumber("31643", R.id.checkBox4_3, "จำนวน", " ... คน", "เรียนต่อต่างประเทศ %s คน")
        setCheckBoxInputNumber("31644", R.id.checkBox4_4, "จำนวน", " ... คน", "ไม่ได้เรียนต่อแต่ทำงาน %s คน")
        setCheckBoxInputNumber("31645", R.id.checkBox4_5, "จำนวน", " ... คน", "ไม่ได้เรียนต่อและไม่ได้ทำงาน %s คน")
        setCheckBoxInputNumber("31651", R.id.checkBox5, "จำนวน", " ... ชั่วโมง/สัปดาห์", "%s ชั่วโมง/สัปดาห์")

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 0
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 2
            selectQuestion()
        }
    }

    private fun setSurvey17() {
        layoutInflater.inflate(R.layout.survey17, linearBody, true)

        (findViewInBody(R.id.radioGroup1) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "31711"
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

        (findViewInBody(R.id.radioGroup2) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "31721"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio2_2 -> {
                    formUtil.showDialogInputNumber("จำนวน", "... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "ไม่ได้เรียน $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        (findViewInBody(R.id.radioGroup3) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "31731"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio3_1 -> {
                    formUtil.showDialogInputNumber("จำนวน", "... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "มี $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        setCheckBoxInputNumber("31741", R.id.checkBox4_1, "จำนวน", " ... คน", "เรียนต่อ กศน/การศึกษาผู้ใหญ่  %s คน")
        setCheckBoxInputNumber("31742", R.id.checkBox4_2, "จำนวน", " ... คน", "ศึกษาเองที่บ้าน (โฮมสคูล) %s คน")
        setCheckBoxInputNumber("31743", R.id.checkBox4_3, "จำนวน", " ... คน", "เรียนต่อต่างประเทศ %s คน")
        setCheckBoxInputNumber("31744", R.id.checkBox4_4, "จำนวน", " ... คน", "ไม่ได้เรียนต่อแต่ทำงาน %s คน")
        setCheckBoxInputNumber("31745", R.id.checkBox4_5, "จำนวน", " ... คน", "ไม่ได้เรียนต่อและไม่ได้ทำงาน %s คน")
        setCheckBoxInputNumber("31751", R.id.checkBox5, "จำนวน", " ... ชั่วโมง/สัปดาห์", "%s ชั่วโมง/สัปดาห์")

        (findViewInBody(R.id.radioGroup6) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "31761"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio6_1 -> {
                    formUtil.showDialogInputNumber("จำนวน", "... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "สนใจ จำนวน $it คน"
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

    private fun setSurvey18() {
        layoutInflater.inflate(R.layout.survey18, linearBody, true)

        (findViewInBody(R.id.radioGroup1) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "31811"
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

        (findViewInBody(R.id.radioGroup2) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "31821"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio2_2 -> {
                    formUtil.showDialogInputNumber("จำนวน", "... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "ไม่ได้รับการฝึกอบรมด้านอาชีพ $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        setCheckBoxInputNumber("31831", R.id.checkBox3_1, "จำนวน", " ... คน", "ผู้ที่จบ ม.3 ในรอบปีที่ผ่านมา %s คน")
        setCheckBoxInputNumber("31832", R.id.checkBox3_2, "จำนวน", " ... คน", "ผู้ที่จบ ม.3 ในปีอื่น ๆ %s คน")
        setCheckBoxInputNumber("31841", R.id.checkBox4, "จำนวน", " ... คน", "%s คน")

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 2
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 4
            selectQuestion()
        }
    }

    private fun setSurvey19() {
        layoutInflater.inflate(R.layout.survey19, linearBody, true)

        (findViewInBody(R.id.radioGroup1) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "31911"
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

        (findViewInBody(R.id.radioGroup2) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "31921"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio2_2 -> {
                    formUtil.showDialogInputNumber("จำนวน", "... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "ไม่ได้ $it คน"
                        family.answer[questionNo] = "$firstVal,$it"
                    }
                }
                else -> family.answer[questionNo] = firstVal
            }
            SaveData()
        }

        (findViewInBody(R.id.radioGroup3) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "31931"
            val firstVal = findViewInBody(checkedId).tag.toString()
            when (checkedId) {
                R.id.radio3_2 -> {
                    formUtil.showDialogInputNumber("จำนวน", "... คน") {
                        (findViewInBody(checkedId) as RadioButton).text = "ไม่ได้ $it คน"
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
            initView(4)
            surveyMetricIndex = 0
            selectQuestion()
        }
    }

    private fun setSurvey20() {
        layoutInflater.inflate(R.layout.survey20, linearBody, true)

        (findViewInBody(R.id.radioGroup1) as RadioGroup).setOnCheckedChangeListener { _, checkedId ->
            val questionNo = "42011"
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

        setCheckBox("42021", R.id.checkBox2_1)
        setCheckBoxInputNumber("42022", R.id.checkBox2_2, "จำนวน", " ... คน", "ไม่มีอาชีพและไม่มีรายได้ %s คน")
        setCheckBoxInputNumber("42023", R.id.checkBox2_3, "จำนวน", " ... คน", "ไม่มีอาชีพแต่มีรายได้ %s คน")
        setCheckBoxInputNumber("42031", R.id.checkBox3, "จำนวน", " ... คน", "จำนวน %s คน")

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            initView(3)
            surveyMetricIndex = 4
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 1
            selectQuestion()
        }
    }

    private fun setSurvey21() {
        layoutInflater.inflate(R.layout.survey21, linearBody, true)

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 0
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 2
            selectQuestion()
        }
    }

    private fun setSurvey22() {
        layoutInflater.inflate(R.layout.survey22, linearBody, true)
        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 1
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 3
            selectQuestion()
        }
    }

    private fun setSurvey23() {
        layoutInflater.inflate(R.layout.survey23, linearBody, true)

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 2
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            initView(5)
            surveyMetricIndex = 0
            selectQuestion()
        }
    }

    private fun setSurvey24() {
        layoutInflater.inflate(R.layout.survey24, linearBody, true)

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            initView(4)
            surveyMetricIndex = 3
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 1
            selectQuestion()
        }
    }

    private fun setSurvey25() {
        layoutInflater.inflate(R.layout.survey25, linearBody, true)
        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 0
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 2
            selectQuestion()
        }
    }

    private fun setSurvey26() {
        layoutInflater.inflate(R.layout.survey26, linearBody, true)
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

    private fun setSurvey27() {
        layoutInflater.inflate(R.layout.survey27, linearBody, true)
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

    private fun setSurvey28() {
        layoutInflater.inflate(R.layout.survey28, linearBody, true)

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 3
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 5
            selectQuestion()
        }
    }

    private fun setSurvey29() {
        layoutInflater.inflate(R.layout.survey29, linearBody, true)

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 4
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 6
            selectQuestion()
        }
    }

    private fun setSurvey30() {
        layoutInflater.inflate(R.layout.survey30, linearBody, true)

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 5
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 7
            selectQuestion()
        }
    }

    private fun setSurvey31() {
        layoutInflater.inflate(R.layout.survey31, linearBody, true)

//        findViewInBody(R.id.btnPrevious).setOnClickListener {
//            surveyMetricIndex = 6
//            selectQuestion()
//        }
//        findViewInBody(R.id.btnNext).setOnClickListener {
//            surveyMetricIndex = 8
//            selectQuestion()
//        }
    }


    private fun showDetail(Header: String?, Detail: String?) {
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