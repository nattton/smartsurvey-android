package com.cdd.smartsurvey

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.cdd.smartsurvey.data.model.Family
import com.cdd.smartsurvey.data.model.WaitingList
import com.cdd.smartsurvey.sqlite.DatabaseHelper
import com.cdd.smartsurvey.sqlite.model.SurveyGroup
import com.cdd.smartsurvey.utils.ImageUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_start_survey.*
import java.util.*

class StartSurveyAcitivity : AppCompatActivity() {
    lateinit var family: Family
    var familyIndex = 0
    var linearMain: LinearLayout? = null
    var btnHealth: Button? = null
    var btnEnvironment: Button? = null
    var btnEducation: Button? = null
    var btnPayment: Button? = null
    var btnPopular: Button? = null
    var btnOther: Button? = null
    var frameLayout: FrameLayout? = null
    var imageViewIcon: ImageView? = null
    var txtdisplay: TextView? = null
    var buttonData: Button? = null
    var imageViewProgress: ImageView? = null
    var txtProgress: TextView? = null
    var item: SurveyGroup? = null
    var sc: CharSequence? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_survey)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        familyIndex = intent.getIntExtra(GlobalValue.EXTRA_FAMILY_INDEX, 0)
        loadFamily(familyIndex)

        linearMain = findViewById<View>(R.id.LinearMain) as LinearLayout
        val db: DatabaseHelper
        val surveyGroupsList: MutableList<SurveyGroup> = ArrayList()
        db = DatabaseHelper(this)
        surveyGroupsList.addAll(db.allSurveyGroups)
        for (i in surveyGroupsList.indices) {
            item = surveyGroupsList[i]
            frameLayout = FrameLayout(this)
            val frame_params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT)
            frameLayout!!.layoutParams = frame_params
            frameLayout!!.setBackgroundResource(R.drawable.bordergroup)
            imageViewIcon = ImageView(this)
            val imageicon_params = FrameLayout.LayoutParams(200,
                    200)
            imageicon_params.gravity = Gravity.CENTER_VERTICAL or Gravity.TOP
            imageicon_params.setMargins((21 * Resources.getSystem().displayMetrics.density).toInt(),
                    (21 * Resources.getSystem().displayMetrics.density).toInt(), 0, 0)
            imageViewIcon!!.layoutParams = imageicon_params
            imageViewIcon!!.scaleType = ImageView.ScaleType.FIT_XY
            imageViewIcon!!.setImageBitmap(ImageUtil.convert(item!!.groupImage))
            txtdisplay = TextView(this)
            val txtdisplay_params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT)
            txtdisplay_params.gravity = Gravity.LEFT or Gravity.TOP
            txtdisplay_params.setMargins((100 * Resources.getSystem().displayMetrics.density).toInt(),
                    (10 * Resources.getSystem().displayMetrics.density).toInt(), 0, 0)
            txtdisplay!!.layoutParams = txtdisplay_params
            txtdisplay!!.typeface = ResourcesCompat.getFont(applicationContext, R.font.thsarabun_bold)
            txtdisplay!!.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)
            txtdisplay!!.setTextColor(resources.getColor(R.color.colorPrimary))
            txtdisplay!!.text = item!!.groupDisplay
            buttonData = Button(this)
            val buttondata_params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT)
            buttondata_params.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
            buttondata_params.setMargins(0, 0, 0, (20 * Resources.getSystem().displayMetrics.density).toInt())
            buttonData!!.layoutParams = buttondata_params
            buttonData!!.typeface = ResourcesCompat.getFont(applicationContext, R.font.thsarabun_bold)
            buttonData!!.text = "เริ่มสำรวจ"
            buttonData!!.setTextColor(Color.parseColor("#FFFFFF"))
            buttonData!!.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)
            buttonData!!.setBackgroundResource(R.drawable.custom_menubutton)
            buttonData!!.id = item!!.id
            buttonData!!.tag = item
            buttonData!!.setOnClickListener { v -> openHeaderSurvey(v.tag as SurveyGroup) }
            imageViewProgress = ImageView(this)
            val imageViewProgress_params = FrameLayout.LayoutParams((40 * Resources.getSystem().displayMetrics.density).toInt(),
                    FrameLayout.LayoutParams.MATCH_PARENT)
            imageViewProgress_params.gravity = Gravity.END
            imageViewProgress_params.setMargins(0, 0, (20 * Resources.getSystem().displayMetrics.density).toInt(),
                    (6 * Resources.getSystem().displayMetrics.density).toInt())
            imageViewProgress!!.layoutParams = imageViewProgress_params
            imageViewProgress!!.adjustViewBounds = true
            imageViewProgress!!.scaleType = ImageView.ScaleType.FIT_CENTER
            imageViewProgress!!.setImageResource(R.drawable.custom_cicle_progress)
            txtProgress = TextView(this)
            val txtProgress_params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT)
            txtProgress_params.gravity = Gravity.RIGHT
            txtProgress_params.setMargins(0, (40 * Resources.getSystem().displayMetrics.density).toInt(),
                    (32 * Resources.getSystem().displayMetrics.density).toInt(), 0)
            txtProgress!!.layoutParams = txtProgress_params
            txtProgress!!.typeface = ResourcesCompat.getFont(applicationContext, R.font.thsarabun_bold)
            txtProgress!!.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15f)
            txtProgress!!.setTextColor(Color.parseColor("#FFFFFF"))
            txtProgress!!.text = "0%"
            frameLayout!!.addView(imageViewIcon)
            frameLayout!!.addView(txtdisplay)
            frameLayout!!.addView(buttonData)
            frameLayout!!.addView(imageViewProgress)
            frameLayout!!.addView(txtProgress)
            linearMain!!.addView(frameLayout)
        }
        btnBack.setOnClickListener { onBackPressed() }
        btnSend.setOnClickListener { goWaitingUpload() }
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

    fun goWaitingUpload() {

//        var waitingList = WaitingList(ArrayList())
//        val sharedPref = applicationContext.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
//        var waitingJson = sharedPref.getString(getString(R.string.pref_waiting_list), "")
//        if (waitingJson != "") {
//            waitingList = Gson().fromJson(waitingJson, WaitingList::class.java)
//        }
//
//        waitingList.familyList.add(family)
//
//        waitingJson = Gson().toJson(waitingList)
//        sharedPref.edit().apply {
//            putString(getString(R.string.pref_waiting_list), waitingJson)
//            apply()
//        }

        val intent = Intent(this, WaitingUploadActivity::class.java)
        startActivity(intent)
    }

    fun openHeaderSurvey(sc: SurveyGroup) {
        val intent = Intent(this, HeaderSurveyMasterActivity::class.java).apply {
            putExtra(GlobalValue.EXTRA_FAMILY_INDEX, familyIndex)
            putExtra(GlobalValue.EXTRA_SURVEY_GROUP, sc)
        }
        startActivity(intent)
    }
}