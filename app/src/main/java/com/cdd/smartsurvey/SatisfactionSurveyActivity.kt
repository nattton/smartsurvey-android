package com.cdd.smartsurvey

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_satisfaction_survey.*


class SatisfactionSurveyActivity : AppCompatActivity() {

    @SuppressLint("SourceLockedOrientationActivity", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_satisfaction_survey)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        btnNext.setOnClickListener {
            goAcceptSurvey()
        }
    }

    fun goAcceptSurvey() {
        val intent = Intent(this, AcceptSurveyActivity::class.java)
        startActivity(intent)
    }
}