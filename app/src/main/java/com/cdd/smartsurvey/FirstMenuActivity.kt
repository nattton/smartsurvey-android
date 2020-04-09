package com.cdd.smartsurvey

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cdd.smartsurvey.RegisterPagesActivity
import com.cdd.smartsurvey.RegisterPagesNewFamilyActivity
import kotlinx.android.synthetic.main.activity_firstmenu.*

class FirstMenuActivity : AppCompatActivity() {
    var btnMenuSurvey: Button? = null
    var btnMenuTumbon: Button? = null
    var btnEcard: Button? = null
    var btnProfile: Button? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstmenu)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        btnProfile = findViewById<View>(R.id.btnProfile) as Button
        btnProfile!!.setOnClickListener { openRegister() }
        btnMenuSurvey = findViewById<View>(R.id.btnSurveyMenu) as Button
        btnMenuSurvey!!.setOnClickListener { openSurvey() }
        btnMenuTumbon = findViewById<View>(R.id.btnTumbonMenu) as Button
        btnMenuTumbon!!.setOnClickListener { openTumbon() }
        btnEcard = findViewById<View>(R.id.btnECard) as Button
        btnEcard!!.setOnClickListener { openEcard() }
        btnLogout.setOnClickListener {

            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@FirstMenuActivity)
            alertDialog.setTitle("ยืนยันการออกจากระบบ")
            alertDialog.setMessage("")
            alertDialog.setPositiveButton("ตกลง") { dialog, _ ->

                val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    clear()
                    commit()
                }

                dialog.dismiss()

                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            }
            alertDialog.setNegativeButton("ยกเลิก") { dialog, _ ->
                dialog.dismiss()
            }

            val dialog: Dialog = alertDialog.create()
            dialog.show()
        }
    }

    fun openRegister() {
        val intent = Intent(this, RegisterPagesActivity::class.java)
        startActivity(intent)
    }

    fun openSurvey() {
        val intent = Intent(this, FirstPagesActivity::class.java)
        startActivity(intent)
    }

    fun openTumbon() {
        val intent = Intent(this, RegisterPagesNewFamilyActivity::class.java)
        startActivity(intent)
    }

    fun openEcard() {
        val intent = Intent(this, ECardActivity::class.java)
        startActivity(intent)
    }
}