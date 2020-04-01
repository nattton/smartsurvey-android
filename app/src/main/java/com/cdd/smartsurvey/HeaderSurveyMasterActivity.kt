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
import com.cdd.smartsurvey.SurveySubMasterActivity
import com.cdd.smartsurvey.sqlite.DatabaseHelper
import com.cdd.smartsurvey.sqlite.model.SurveyMetric
import com.cdd.smartsurvey.utils.ImageUtil
import java.util.*

class HeaderSurveyMasterActivity : AppCompatActivity() {
    var taggroup: String? = null
    var imgicon: String? = null
    var headervalue: String? = null
    var metricvalue: String? = null
    var item: SurveyMetric? = null
    var imgIcon: ImageView? = null
    var HeaderValue: TextView? = null
    var MetricValue: TextView? = null
    var btnBack: Button? = null
    var LinearMaster: LinearLayout? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_metric_master)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        btnBack = findViewById<View>(R.id.btnBack) as Button
        btnBack!!.setOnClickListener { onBackPressed() }
        val intent = intent
        taggroup = intent.getStringExtra("taggroup")
        imgicon = intent.getStringExtra("imgicon")
        headervalue = intent.getStringExtra("headervalue")
        metricvalue = intent.getStringExtra("metricvalue")
        imgIcon = findViewById<View>(R.id.imgIcon) as ImageView
        HeaderValue = findViewById<View>(R.id.txtHeaderValue) as TextView
        MetricValue = findViewById<View>(R.id.txtMetricValue) as TextView
        LinearMaster = findViewById<View>(R.id.linearBody) as LinearLayout
        imgIcon!!.setImageBitmap(ImageUtil.convert(imgicon))
        HeaderValue!!.text = headervalue
        MetricValue!!.text = "ประกอบด้วย $metricvalue"
        val db: DatabaseHelper
        val surveyMetricsList: MutableList<SurveyMetric> = ArrayList()
        db = DatabaseHelper(this)
        surveyMetricsList.addAll(db.getAllSurveyMetrics(taggroup!!))
        for (i in surveyMetricsList.indices) {
            item = surveyMetricsList[i]
            LinearMaster!!.addView(setFrameLayout(item!!.tbl_survey_group_masterid, item!!.metric_no, item!!.metric_display, imgicon, item!!.metric_description))
        }
    }

    fun setTextView(surveyGroupID: String?, metricNo: String?, metricDisplay: String?, intValue: String?, metricDesc: String?): TextView {
        val TextMaster = TextView(applicationContext)
        val paramsText = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        TextMaster.layoutParams = paramsText
        TextMaster.typeface = ResourcesCompat.getFont(applicationContext, R.font.thsarabun_bold)
        TextMaster.gravity = Gravity.CENTER_VERTICAL
        TextMaster.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
        TextMaster.text = metricDisplay
        TextMaster.textSize = 20f
        val params = TextMaster.layoutParams as MarginLayoutParams
        params.setMargins((20 * Resources.getSystem().displayMetrics.density).toInt(),
                (20 * Resources.getSystem().displayMetrics.density).toInt(),
                (20 * Resources.getSystem().displayMetrics.density).toInt(), 0)
        TextMaster.setOnClickListener {
            val intent = Intent(applicationContext, SurveySubMasterActivity::class.java)
            intent.putExtra(SurveySubMasterActivity.EXTRA_SurveyGroupID, surveyGroupID)
            intent.putExtra(SurveySubMasterActivity.EXTRA_MetricNo, metricNo)
            intent.putExtra(SurveySubMasterActivity.EXTRA_MetricDisplay, metricDisplay)
            intent.putExtra(SurveySubMasterActivity.EXTRA_Image, intValue)
            intent.putExtra(SurveySubMasterActivity.EXTRA_MetricDesc, metricDesc)
            startActivity(intent)
        }
        return TextMaster
    }

    fun setFrameLayout(surveyGroupID: String?, metricNo: String?, metricDisplay: String?, intValue: String?, metricDesc: String?): FrameLayout {
        val FrameMaster = FrameLayout(applicationContext)
        val lp = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        )
        FrameMaster.layoutParams = lp
        FrameMaster.background = resources.getDrawable(R.drawable.bordergroup)
        FrameMaster.addView(setTextView(surveyGroupID, metricNo, metricDisplay, intValue, metricDesc))
        val params = FrameMaster.layoutParams as MarginLayoutParams
        params.setMargins((10 * Resources.getSystem().displayMetrics.density).toInt(), 0,
                (10 * Resources.getSystem().displayMetrics.density).toInt(), 0)
        FrameMaster.setOnClickListener {
            val intent = Intent(applicationContext, SurveySubMasterActivity::class.java)
            intent.putExtra(SurveySubMasterActivity.EXTRA_SurveyGroupID, surveyGroupID)
            intent.putExtra(SurveySubMasterActivity.EXTRA_MetricNo, metricNo)
            intent.putExtra(SurveySubMasterActivity.EXTRA_MetricDisplay, metricDisplay)
            intent.putExtra(SurveySubMasterActivity.EXTRA_Image, intValue)
            intent.putExtra(SurveySubMasterActivity.EXTRA_MetricDesc, metricDesc)
            startActivity(intent)
        }
        return FrameMaster
    }
}