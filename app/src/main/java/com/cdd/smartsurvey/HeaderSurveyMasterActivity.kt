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
        val surveyGroupID = intent.getIntExtra(GlobalValue.EXTRA_SURVEY_GROUP_ID, 0)
        familyIndex = intent.getIntExtra(GlobalValue.EXTRA_FAMILY_INDEX, 0)


        loadSurveyListGroup(surveyGroupID)
    }

    private fun loadSurveyListGroup(groupID: Int) {
        val db = DatabaseHelper(this)
        surveyGroup = db.getSurveyGroup(groupID)!!
        imgIcon.setImageBitmap(ImageUtil.convert(surveyGroup.groupImage))
        txtHeaderValue.text = surveyGroup.groupName
        txtMetricValue.text = "ประกอบด้วย ${surveyGroup.groupMetric}"
        surveyMetricsList = db.getAllSurveyMetrics(groupID.toString())
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
        if (requestCode == 100) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val surveyGroupID = data?.getIntExtra(GlobalValue.EXTRA_SURVEY_GROUP_ID, 0)
                    if (surveyGroupID != 0) {
                        loadSurveyListGroup(surveyGroupID!!)
                    }
                }
                GlobalValue.RESULT_FINISH -> {
                    val resultIntent = Intent()
                    setResult(GlobalValue.RESULT_FINISH, resultIntent)
                    finish()
                }
            }

        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}