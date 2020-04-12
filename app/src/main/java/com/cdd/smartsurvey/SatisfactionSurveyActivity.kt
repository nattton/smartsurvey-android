package com.cdd.smartsurvey

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.cdd.smartsurvey.http.model.Family
import com.cdd.smartsurvey.utils.AppDBHelper
import kotlinx.android.synthetic.main.activity_satisfaction_survey.*


class SatisfactionSurveyActivity : AppCompatActivity() {
    lateinit var family: Family
    var familyIndex = 0
    lateinit var appDBHelper: AppDBHelper

    @SuppressLint("SourceLockedOrientationActivity", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_satisfaction_survey)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        familyIndex = intent.getIntExtra(GlobalValue.EXTRA_FAMILY_INDEX, 0)
        appDBHelper = AppDBHelper(applicationContext)
        family = appDBHelper.loadFamily(familyIndex)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewScore.text = "คะแนน : $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        btnNext.setOnClickListener {
            family.answer["satisfaction"] = seekBar.progress.toString()
            appDBHelper.saveFamily(family, familyIndex)
            goSummary()
        }
    }

    private fun goSummary() {
        val intent = Intent(this, SummaryActivity::class.java).apply {
            putExtra(GlobalValue.EXTRA_FAMILY_INDEX, familyIndex)
        }

        startActivity(intent)
    }
}