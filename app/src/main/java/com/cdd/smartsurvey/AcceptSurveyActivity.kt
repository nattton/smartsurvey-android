package com.cdd.smartsurvey

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cdd.smartsurvey.http.model.Community
import com.cdd.smartsurvey.utils.ImageUtil
import com.kyanogen.signatureview.SignatureView
import kotlinx.android.synthetic.main.activity_accept_survey.*

class AcceptSurveyActivity : AppCompatActivity() {
    var signatureView: SignatureView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accept_survey)
        signatureView = findViewById<View>(R.id.signature_view) as SignatureView
        btnBack.setOnClickListener { onBackPressed() }
        btnClear.setOnClickListener { signatureView!!.clearCanvas() }
        btnStartSurvey.setOnClickListener {
            GlobalValue.signature = ImageUtil.convert(signatureView!!.signatureBitmap)
            openSmartSurvey()
        }
    }

    fun openSmartSurvey() {
        val community: Community = intent.getParcelableExtra(GlobalValue.EXTRA_COMMUNITY)
        val intent = Intent(this, RegisterPagesNewFamilyActivity::class.java).apply {
            putExtra(GlobalValue.EXTRA_COMMUNITY, community)
        }
        startActivity(intent)
    }
}