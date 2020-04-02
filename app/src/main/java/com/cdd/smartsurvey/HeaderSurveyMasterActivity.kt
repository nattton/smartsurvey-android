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
import com.cdd.smartsurvey.utils.ImageUtil
import kotlinx.android.synthetic.main.activity_survey_metric_master.*
import java.util.*

class HeaderSurveyMasterActivity : AppCompatActivity() {
    lateinit var surveyGroup: SurveyGroup
    var item: SurveyMetric? = null
    var LinearMaster: LinearLayout? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_metric_master)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        btnBack.setOnClickListener { onBackPressed() }
        surveyGroup = intent.getParcelableExtra(GlobalValue.EXTRA_SURVEY_GROUP)
        imgIcon.setImageBitmap(ImageUtil.convert(surveyGroup.groupImage))
        txtHeaderValue.text = surveyGroup.groupName
        txtMetricValue.text = "ประกอบด้วย ${surveyGroup.groupMetric}"
        val db: DatabaseHelper
        val surveyMetricsList: MutableList<SurveyMetric> = ArrayList()
        db = DatabaseHelper(this)
        surveyMetricsList.addAll(db.getAllSurveyMetrics(surveyGroup.id.toString()))

        listViewSurveyMetric.layoutManager = LinearLayoutManager(this)
        listViewSurveyMetric.setHasFixedSize(true)
        listViewSurveyMetric.adapter = SurveyMetricRecyclerViewAdapter(surveyMetricsList) { view, i, surveyMetric ->
            val intent = Intent(applicationContext, SurveySubMasterActivity::class.java).apply {
                putExtra(GlobalValue.EXTRA_SURVEY_GROUP, surveyGroup)
                putExtra(GlobalValue.EXTRA_SURVEY_METRIC, surveyMetric)
            }
            startActivity(intent)
        }

    }
}