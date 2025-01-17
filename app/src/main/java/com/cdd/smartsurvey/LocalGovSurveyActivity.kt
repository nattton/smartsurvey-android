package com.cdd.smartsurvey

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.cdd.smartsurvey.http.model.Family
import kotlinx.android.synthetic.main.activity_localgov_survey.*

class LocalGovSurveyActivity : AppCompatActivity() {
    lateinit var family: Family

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_localgov_survey)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        family = intent.getParcelableExtra(GlobalValue.EXTRA_FAMILY)
        btnSave.setOnClickListener { PassedData() }
        btnBack.setOnClickListener { onBackPressed() }
        radioGroupArea.setOnCheckedChangeListener { group, checkedId ->
            var selectArea = findViewById<RadioButton>(checkedId)
            family.harea = selectArea.tag.toString()
        }
    }

    fun PassedData() {
        val intent = Intent(this, AreaLeaveSurveyActivity::class.java).apply {
            putExtra(GlobalValue.EXTRA_FAMILY, family)
        }
        startActivity(intent)
    }
}