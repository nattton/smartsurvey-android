package com.cdd.smartsurvey

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cdd.smartsurvey.adapter.CommunityListAdapter
import com.cdd.smartsurvey.http.model.Community
import com.cdd.smartsurvey.utils.AppDBHelper
import com.cdd.smartsurvey.utils.CheckAccuracy
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_firstpages.*

class FirstPagesActivity : AppCompatActivity() {
    lateinit var appDBHelper: AppDBHelper

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstpages)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        appDBHelper = AppDBHelper(applicationContext)

        btnNewFamily.setOnClickListener {
            val community = Community()
            openNewFamily(community)
        }
        appDBHelper.loadCommunityList {
            setCommunityListAdapter(it)
        }

        btnWaitingList.setOnClickListener {
            val intent = Intent(this, WaitingUploadActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setCommunityListAdapter(communityListItems: Array<Community>?) {
        if (communityListItems == null) return
        val adapter = CommunityListAdapter(applicationContext, communityListItems)
        communityListView.adapter = adapter
        communityListView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = communityListItems.get(position)

            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@FirstPagesActivity)
            alertDialog.setTitle("ยืนยันเลือกชุมชน")
            alertDialog.setMessage(selectedItem.community_name)
            alertDialog.setPositiveButton("ตกลง") { dialog, which ->
                openNewFamily(selectedItem)
                dialog.dismiss()
            }
            alertDialog.setNegativeButton("ยกเลิก") { dialog, which ->

            }

            val dialog: Dialog = alertDialog.create()
            dialog.show()
        }
    }

    fun openNewFamily(community: Community) {
        val intent = Intent(this@FirstPagesActivity, RegisterPagesNewFamilyActivity::class.java).apply {
            putExtra(GlobalValue.EXTRA_COMMUNITY, community)
        }
        startActivity(intent)
    }

    private fun showFirstDialog(c: Context) {
        val btnAcceptHomeNo: Button
        val txtHomeno: EditText
        val mBuilder = AlertDialog.Builder(this@FirstPagesActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_homeno, null)
        mBuilder.setView(mView)
        val dialog = mBuilder.create()
        dialog.show()
        txtHomeno = mView.findViewById<View>(R.id.txtHomeNo) as EditText
        txtHomeno.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    if (!CheckAccuracy.CheckIDCard(s)) {
                        val ab = AlertDialog.Builder(this@FirstPagesActivity, R.style.AlertDialogTheme)
                        ab.setTitle("แจ้งเตือน")
                        ab.setMessage("ข้อมูลเลขที่บ้านไม่ถูกต้อง")
                        ab.setPositiveButton("ตกลง") { dialog, which -> dialog.dismiss() }
                        ab.show()
                    }
                } catch (e: Exception) {
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })
        btnAcceptHomeNo = mView.findViewById(R.id.btnAccept)
        btnAcceptHomeNo.setOnClickListener {
            GlobalValue.homeno = txtHomeno.text.toString()
            dialog.dismiss()
            val intent = Intent(this@FirstPagesActivity, AcceptSurveyActivity::class.java)
            startActivity(intent)
        }
    }
}