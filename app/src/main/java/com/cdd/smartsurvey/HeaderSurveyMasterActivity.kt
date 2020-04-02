package com.cdd.smartsurvey

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.cdd.smartsurvey.SurveySubMasterActivity
import com.cdd.smartsurvey.adapter.SurveyMetricRecyclerViewAdapter
import com.cdd.smartsurvey.sqlite.DatabaseHelper
import com.cdd.smartsurvey.sqlite.model.SurveyGroup
import com.cdd.smartsurvey.sqlite.model.SurveyMetric
import com.cdd.smartsurvey.sqlite.model.SurveyMetricList
import com.cdd.smartsurvey.utils.ImageUtil
import kotlinx.android.synthetic.main.activity_survey_metric_master.*
import java.util.*

class HeaderSurveyMasterActivity : AppCompatActivity() {
    lateinit var surveyGroup: SurveyGroup
    var familyIndex = 0
    var item: SurveyMetric? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_metric_master)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        btnBack.setOnClickListener { onBackPressed() }
        surveyGroup = intent.getParcelableExtra(GlobalValue.EXTRA_SURVEY_GROUP)
        familyIndex = intent.getIntExtra(GlobalValue.EXTRA_FAMILY_INDEX, 0)
        imgIcon.setImageBitmap(ImageUtil.convert(surveyGroup.groupImage))
        txtHeaderValue.text = surveyGroup.groupName
        txtMetricValue.text = "ประกอบด้วย ${surveyGroup.groupMetric}"
        val db = DatabaseHelper(this)
        val surveyMetricsList = SurveyMetricList(db.getAllSurveyMetrics(surveyGroup.id.toString()))

        listViewSurveyMetric.layoutManager = LinearLayoutManager(this)
        listViewSurveyMetric.setHasFixedSize(true)
        listViewSurveyMetric.adapter = SurveyMetricRecyclerViewAdapter(surveyMetricsList.list) { view, i, surveyMetric ->
            val intent = Intent(applicationContext, SurveySubMasterActivity::class.java).apply {
                putExtra(GlobalValue.EXTRA_FAMILY_INDEX, familyIndex)
                putExtra(GlobalValue.EXTRA_SURVEY_GROUP, surveyGroup)
                putExtra(GlobalValue.EXTRA_SURVEY_METRIC_LIST, surveyMetricsList)
                putExtra(GlobalValue.EXTRA_SURVEY_METRIC_INDEX, i)
            }
            startActivity(intent)
        }

    }
}