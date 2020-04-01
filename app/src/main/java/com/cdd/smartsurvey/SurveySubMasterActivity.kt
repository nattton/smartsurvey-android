package com.cdd.smartsurvey

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
import com.cdd.smartsurvey.utils.ImageUtil
import kotlinx.android.synthetic.main.activity_survey_sub_master.*

class SurveySubMasterActivity : AppCompatActivity() {
    var SurveyGroupID: String? = null
    var MetricNo: String? = null
    var HeaderName: String? = null
    var HeaderImage: String? = null
    var DetailData: String? = null
    var btnBack: Button? = null
    var btnDetail: Button? = null
    var LinearDetail: LinearLayout? = null
    var imgIcon: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_sub_master)
        val intent = intent
        SurveyGroupID = intent.getStringExtra(EXTRA_SurveyGroupID)
        MetricNo = intent.getStringExtra(EXTRA_MetricNo)
        HeaderName = intent.getStringExtra(EXTRA_MetricDisplay)
        HeaderImage = intent.getStringExtra(EXTRA_Image)
        DetailData = intent.getStringExtra(EXTRA_MetricDesc)
        imgIcon = findViewById<View>(R.id.imgIcon) as ImageView
        btnBack = findViewById<View>(R.id.btnBack) as Button
        btnBack!!.setOnClickListener { onBackPressed() }
        btnDetail = findViewById<View>(R.id.btnDetail) as Button
        btnDetail!!.setOnClickListener { ShowDetail(HeaderName, DetailData) }
        imgIcon!!.setImageBitmap(ImageUtil.convert(HeaderImage))
        selectQuestion()
    }

    private fun selectQuestion() {
        linearheader.addView(setHeaderFrameLayout(HeaderName))
        when (SurveyGroupID) {
            "1" -> if (MetricNo == "1") linearBody.addView(setSurvey1()) else if (MetricNo == "2") linearBody.addView(setSurvey2()) else if (MetricNo == "3") linearBody.addView(setSurvey3()) else if (MetricNo == "4") linearBody.addView(setSurvey4()) else if (MetricNo == "5") linearBody.addView(setSurvey5()) else if (MetricNo == "6") linearBody.addView(setSurvey6()) else if (MetricNo == "7") linearBody.addView(setSurvey7())
            "2" -> if (MetricNo == "1") linearBody.addView(setSurvey8()) else if (MetricNo == "2") linearBody.addView(setSurvey9()) else if (MetricNo == "3") linearBody.addView(setSurvey10()) else if (MetricNo == "4") linearBody.addView(setSurvey11()) else if (MetricNo == "5") linearBody.addView(setSurvey12()) else if (MetricNo == "6") linearBody.addView(setSurvey13()) else if (MetricNo == "7") linearBody.addView(setSurvey14())
            "3" -> if (MetricNo == "1") linearBody.addView(setSurvey15()) else if (MetricNo == "2") linearBody.addView(setSurvey16()) else if (MetricNo == "3") linearBody.addView(setSurvey10()) else if (MetricNo == "4") linearBody.addView(setSurvey11()) else if (MetricNo == "5") linearBody.addView(setSurvey12()) else if (MetricNo == "6") linearBody.addView(setSurvey13()) else if (MetricNo == "7") linearBody.addView(setSurvey14()) else if (MetricNo == "8") linearBody.addView(setSurvey15()) else if (MetricNo == "9") linearBody.addView(setSurvey16()) else if (MetricNo == "10") linearBody.addView(setSurvey17()) else if (MetricNo == "11") linearBody.addView(setSurvey18()) else if (MetricNo == "12") linearBody.addView(setSurvey19())
            "4" -> if (MetricNo == "1") linearBody.addView(setSurvey20()) else if (MetricNo == "2") linearBody.addView(setSurvey21()) else if (MetricNo == "3") linearBody.addView(setSurvey22()) else if (MetricNo == "4") linearBody.addView(setSurvey23())
            "5" -> if (MetricNo == "1") linearBody.addView(setSurvey24()) else if (MetricNo == "2") linearBody.addView(setSurvey25()) else if (MetricNo == "3") linearBody.addView(setSurvey26()) else if (MetricNo == "4") linearBody.addView(setSurvey27()) else if (MetricNo == "5") linearBody.addView(setSurvey28()) else if (MetricNo == "6") linearBody.addView(setSurvey29()) else if (MetricNo == "7") linearBody.addView(setSurvey30()) else if (MetricNo == "8") linearBody.addView(setSurvey31())
        }
    }

    fun setSurvey1(): View {
        linearheader.addView(setHeaderFrameLayout(""))
        val viewSurvey = layoutInflater.inflate(R.layout.survey1, null)
        val radioGroup1 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup1)
        val question2 = viewSurvey.findViewById<View>(R.id.question2)
        question2.visibility = View.GONE
        val radioGroup2 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup2)
        val btnNext = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup2)
        radioGroup1.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radio1_1) {
                question2.visibility = View.VISIBLE
            } else {
                question2.visibility = View.GONE
                radioGroup2.clearCheck()
            }
        }
        btnNext.setOnClickListener {
            setSurvey2()
        }

        return viewSurvey
    }

    fun setSurvey2(): View {
        val viewSurvey = layoutInflater.inflate(R.layout.survey2, null)
        val radioGroup1 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup1)
        val question2 = viewSurvey.findViewById<View>(R.id.question2)
        val radioGroup2 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup2)
        val question3 = viewSurvey.findViewById<View>(R.id.question3)
        val radioGroup3 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup2)
        val question4 = viewSurvey.findViewById<View>(R.id.question4)
        val radioGroup4 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup4)
        radioGroup1.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radio1_1) {
                question2.visibility = View.VISIBLE
            } else {
                question2.visibility = View.GONE
                radioGroup2.clearCheck()
                question3.visibility = View.VISIBLE
            }
        }
        radioGroup3.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radio3_1) {
                question4.visibility = View.GONE
                radioGroup4.clearCheck()
            } else {
                question4.visibility = View.VISIBLE
            }
        }
        return viewSurvey
    }

    fun setSurvey3(): View {
        val viewSurvey = layoutInflater.inflate(R.layout.survey3, null)
        val radioGroup1 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup1)
        val question2 = viewSurvey.findViewById<View>(R.id.question2)
        val radioGroup2 = viewSurvey.findViewById<RadioGroup>(R.id.radioGroup2)
        val question3 = viewSurvey.findViewById<View>(R.id.question3)
        radioGroup1.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radio1_1) {
                question2.visibility = View.VISIBLE
            } else {
                question2.visibility = View.GONE
                radioGroup2.clearCheck()
                question3.visibility = View.GONE
            }
        }
        radioGroup2.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radio2_1) {
            } else {
                question3.visibility = View.VISIBLE
            }
        }
        return viewSurvey
    }

    fun setSurvey4(): View {
        return layoutInflater.inflate(R.layout.survey4, null)
    }

    fun setSurvey5(): View {
        return layoutInflater.inflate(R.layout.survey5, null)
    }

    fun setSurvey6(): View {
        return layoutInflater.inflate(R.layout.survey6, null)
    }

    fun setSurvey7(): View {
        return layoutInflater.inflate(R.layout.survey7, null)
    }

    fun setSurvey8(): View {
        return layoutInflater.inflate(R.layout.survey8, null)
    }

    fun setSurvey9(): View {
        return layoutInflater.inflate(R.layout.survey9, null)
    }

    fun setSurvey10(): View {
        return layoutInflater.inflate(R.layout.survey10, null)
    }

    fun setSurvey11(): View {
        return layoutInflater.inflate(R.layout.survey11, null)
    }

    fun setSurvey12(): View {
        return layoutInflater.inflate(R.layout.survey12, null)
    }

    fun setSurvey13(): View {
        return layoutInflater.inflate(R.layout.survey13, null)
    }

    fun setSurvey14(): View {
        return layoutInflater.inflate(R.layout.survey14, null)
    }

    fun setSurvey15(): View {
        return layoutInflater.inflate(R.layout.survey15, null)
    }

    fun setSurvey16(): View {
        return layoutInflater.inflate(R.layout.survey16, null)
    }

    fun setSurvey17(): View {
        return layoutInflater.inflate(R.layout.survey17, null)
    }

    fun setSurvey18(): View {
        return layoutInflater.inflate(R.layout.survey18, null)
    }

    fun setSurvey19(): View {
        return layoutInflater.inflate(R.layout.survey19, null)
    }

    fun setSurvey20(): View {
        return layoutInflater.inflate(R.layout.survey20, null)
    }

    fun setSurvey21(): View {
        return layoutInflater.inflate(R.layout.survey21, null)
    }

    fun setSurvey22(): View {
        return layoutInflater.inflate(R.layout.survey22, null)
    }

    fun setSurvey23(): View {
        return layoutInflater.inflate(R.layout.survey23, null)
    }

    fun setSurvey24(): View {
        return layoutInflater.inflate(R.layout.survey24, null)
    }

    fun setSurvey25(): View {
        return layoutInflater.inflate(R.layout.survey25, null)
    }

    fun setSurvey26(): View {
        return layoutInflater.inflate(R.layout.survey26, null)
    }

    fun setSurvey27(): View {
        return layoutInflater.inflate(R.layout.survey27, null)
    }

    fun setSurvey28(): View {
        return layoutInflater.inflate(R.layout.survey28, null)
    }

    fun setSurvey29(): View {
        return layoutInflater.inflate(R.layout.survey29, null)
    }

    fun setSurvey30(): View {
        return layoutInflater.inflate(R.layout.survey30, null)
    }

    fun setSurvey31(): View {
        return layoutInflater.inflate(R.layout.survey31, null)
    }

    fun setHeaderTextView(Value: String?): TextView {
        val TextMaster = TextView(applicationContext)
        val paramsText = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        TextMaster.layoutParams = paramsText
        TextMaster.typeface = ResourcesCompat.getFont(applicationContext, R.font.thsarabun_bold)
        TextMaster.gravity = Gravity.CENTER_VERTICAL
        TextMaster.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
        TextMaster.text = Value
        TextMaster.textSize = 22f
        val params = TextMaster.layoutParams as MarginLayoutParams
        params.setMargins((20 * Resources.getSystem().displayMetrics.density).toInt(),
                (20 * Resources.getSystem().displayMetrics.density).toInt(),
                (20 * Resources.getSystem().displayMetrics.density).toInt(), 0)
        TextMaster.setOnClickListener { onBackPressed() }
        return TextMaster
    }

    fun setHeaderFrameLayout(Value: String?): FrameLayout {
        val FrameMaster = FrameLayout(applicationContext)
        val lp = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        )
        FrameMaster.layoutParams = lp
        FrameMaster.background = resources.getDrawable(R.drawable.bordergroup)
        FrameMaster.addView(setHeaderTextView(Value))
        val params = FrameMaster.layoutParams as MarginLayoutParams
        params.setMargins(10, 0, 10, 0)
        FrameMaster.setOnClickListener { onBackPressed() }
        return FrameMaster
    }

    fun setQuestionFrameLayout(Value: String?, choic: String?): FrameLayout {
        val FrameMaster = FrameLayout(applicationContext)
        val lp = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        )
        FrameMaster.layoutParams = lp
        FrameMaster.background = resources.getDrawable(R.drawable.bordergroup)
        FrameMaster.addView(setHeaderTextView(Value))
        val params = FrameMaster.layoutParams as MarginLayoutParams
        params.setMargins(10, 0, 10, 0)
        FrameMaster.setOnClickListener { onBackPressed() }
        return FrameMaster
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

    companion object {
        const val EXTRA_SurveyGroupID = "EXTRA_SurveyGroupID"
        const val EXTRA_MetricNo = "EXTRA_MetricNo"
        const val EXTRA_MetricDisplay = "EXTRA_MetricDisplay"
        const val EXTRA_Image = "EXTRA_Image"
        const val EXTRA_MetricDesc = "EXTRA_MetricDesc"
    }
}