package com.cdd.smartsurvey

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cdd.smartsurvey.adapter.SurveyMetricRecyclerViewAdapter
import com.cdd.smartsurvey.sqlite.DatabaseHelper
import com.cdd.smartsurvey.sqlite.model.SurveyGroup
import com.cdd.smartsurvey.sqlite.model.SurveyMetric
import com.cdd.smartsurvey.utils.ImageUtil
import kotlinx.android.synthetic.main.activity_survey_metric_master.*

class HeaderSurveyMasterActivity : AppCompatActivity() {
    lateinit var surveyGroup: SurveyGroup
    lateinit var surveyMetricsList: List<SurveyMetric>
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
        surveyMetricsList = db.getAllSurveyMetrics(surveyGroup.id.toString())

        listViewSurveyMetric.layoutManager = LinearLayoutManager(this)
        listViewSurveyMetric.setHasFixedSize(true)
        listViewSurveyMetric.adapter = SurveyMetricRecyclerViewAdapter(surveyMetricsList) { view, i, surveyMetric ->
            val intent = Intent(applicationContext, SurveySubMasterActivity::class.java).apply {
                putExtra(GlobalValue.EXTRA_FAMILY_INDEX, familyIndex)
                putExtra(GlobalValue.EXTRA_SURVEY_GROUP_ID, surveyGroup.id)
                putExtra(GlobalValue.EXTRA_SURVEY_METRIC_INDEX, i)
            }
            startActivityForResult(intent, 100)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            val surveyGroupID = data?.getIntExtra(GlobalValue.EXTRA_SURVEY_GROUP_ID, 0)
            if (surveyGroupID != 0) {
                val db = DatabaseHelper(this)
                surveyGroup = db.getSurveyGroup(surveyGroupID!!)!!
                surveyMetricsList = db.getAllSurveyMetrics(surveyGroupID.toString())
                (listViewSurveyMetric.adapter as SurveyMetricRecyclerViewAdapter).notifyDataSetChanged()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}