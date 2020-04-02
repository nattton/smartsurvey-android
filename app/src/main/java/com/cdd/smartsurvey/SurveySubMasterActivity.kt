package com.cdd.smartsurvey

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.cdd.smartsurvey.data.model.Family
import com.cdd.smartsurvey.data.model.WaitingList
import com.cdd.smartsurvey.sqlite.model.SurveyGroup
import com.cdd.smartsurvey.sqlite.model.SurveyMetric
import com.cdd.smartsurvey.sqlite.model.SurveyMetricList
import com.cdd.smartsurvey.utils.ImageUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_survey_sub_master.*
import kotlinx.android.synthetic.main.survey1.*
import java.util.ArrayList

class SurveySubMasterActivity : AppCompatActivity() {
    lateinit var surveyGroup: SurveyGroup
    lateinit var surveyMetricList: SurveyMetricList
    lateinit var surveyMetric: SurveyMetric
    var surveyMetricIndex: Int = 0
    lateinit var family: Family
    var familyIndex = 0
    var LinearDetail: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_sub_master)
        surveyGroup = intent.getParcelableExtra(GlobalValue.EXTRA_SURVEY_GROUP)
        surveyMetricList = intent.getParcelableExtra(GlobalValue.EXTRA_SURVEY_METRIC_LIST)
        surveyMetricIndex = intent.getIntExtra(GlobalValue.EXTRA_SURVEY_METRIC_INDEX, 0)
        familyIndex = intent.getIntExtra(GlobalValue.EXTRA_FAMILY_INDEX, 0)
        loadFamily(familyIndex)

        btnBack.setOnClickListener { onBackPressed() }
        btnDetail.setOnClickListener { ShowDetail(surveyGroup.groupName, surveyMetric.metric_description) }
        imgIcon.setImageBitmap(ImageUtil.convert(surveyGroup.groupImage))
        selectQuestion()
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
        surveyMetric = surveyMetricList.list[surveyMetricIndex]
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

        scrollViewBody.pageScroll(0)
    }

    fun findViewInBody(id: Int): View {
        return linearBody.findViewById(id)
    }

    fun setSurvey1() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey1, null)
        linearBody.addView(viewSurvey)
        val txtHeader = viewSurvey.findViewById<TextView>(R.id.txtHeader)
        txtHeader.text = surveyMetric.metric_display
        val radioGroup1 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup1)
        val question2 = viewSurvey.findViewById<View>(R.id.question2)
        val radioGroup2 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup2)
        val btnNext = viewSurvey.findViewById<Button>(R.id.btnNext)

        findViewInBody(R.id.btnNext).setOnClickListener {
            family.answer["1111"] = findViewInBody(radioGroup1.checkedRadioButtonId).tag.toString()
            family.answer["1112"] = findViewInBody(radioGroup2.checkedRadioButtonId).tag.toString()
            SaveData()
            surveyMetricIndex = 1
            selectQuestion()
        }
    }

    fun setSurvey2() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey2, null)
        linearBody.addView(viewSurvey)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display
        val radioGroup1 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup1)
        val question2 = viewSurvey.findViewById<View>(R.id.question2)
        val radioGroup2 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup2)
        val question3 = viewSurvey.findViewById<View>(R.id.question3)
        val radioGroup3 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup2)
        val question4 = viewSurvey.findViewById<View>(R.id.question4)
        val radioGroup4 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup4)

        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 0
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            family.answer["1121"] = findViewInBody(radioGroup1.checkedRadioButtonId).tag.toString()
            surveyMetricIndex = 2
            selectQuestion()
        }
    }

    fun setSurvey3() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey3, null)
        linearBody.addView(viewSurvey)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display
        val radioGroup1 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup1)
        val question2 = viewSurvey.findViewById<View>(R.id.question2)
        val radioGroup2 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup2)
        val question3 = viewSurvey.findViewById<View>(R.id.question3)
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
        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 4
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {
            surveyMetricIndex = 6
            selectQuestion()
        }
    }

    fun setSurvey7() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey7, null)
        linearBody.addView(viewSurvey)
        (findViewInBody(R.id.txtHeader) as TextView).text = surveyMetric.metric_display
        findViewInBody(R.id.btnPrevious).setOnClickListener {
            surveyMetricIndex = 5
            selectQuestion()
        }
        findViewInBody(R.id.btnNext).setOnClickListener {

        }
    }

    fun setSurvey8() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey8, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey9() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey9, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey10(): View {
        return layoutInflater.inflate(R.layout.survey10, null)
    }

    fun setSurvey11() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey11, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey12() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey12, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey13() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey13, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey14() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey14, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey15() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey15, null)
        linearBody.addView(viewSurvey)
    }

    fun setSurvey16() {
        linearBody.removeAllViews()
        val viewSurvey = layoutInflater.inflate(R.layout.survey16, null)
        linearBody.addView(viewSurvey)
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