package com.cdd.smartsurvey

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cdd.smartsurvey.adapter.WaitingRecyclerViewAdapter
import com.cdd.smartsurvey.http.model.WaitingList
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_waiting_upload.*


class WaitingUploadActivity : AppCompatActivity() {
    var waitingList = WaitingList(ArrayList())

    @SuppressLint("SourceLockedOrientationActivity", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting_upload)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        btnBack.setOnClickListener {
            val intent = Intent(this, FirstMenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val sharedPref = applicationContext.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val waitingJson = sharedPref.getString(getString(R.string.pref_waiting_list), "")
        if (waitingJson != "") {
            waitingList = Gson().fromJson(waitingJson, WaitingList::class.java)
        }
        listViewWaiting.layoutManager = LinearLayoutManager(this)
        listViewWaiting.setHasFixedSize(true)
        listViewWaiting.adapter = WaitingRecyclerViewAdapter(waitingList.familyList) { view, position, familyItem ->
            when (view.id) {
                R.id.btnDelete -> {
                    val ab = AlertDialog.Builder(this@WaitingUploadActivity, R.style.AlertDialogTheme)
                    ab.setTitle("แจ้งเตือน")
                    ab.setMessage("ต้องการลบข้อมูลชุดนี้?")
                    ab.setPositiveButton("ตกลง") { dialog, which ->
                        deleteFamily(position)
                        dialog.dismiss()
                    }
                    ab.setNegativeButton("ยกเลิก") { dialog, which ->
                        dialog.dismiss()
                    }
                    ab.show()
                }
                R.id.btnUpload -> {
                    uploadFamily(position)
                }
                else -> {
                    val intent = Intent(this, MemberSurveyActivity::class.java).apply {
                        putExtra(GlobalValue.EXTRA_FAMILY_INDEX, position)
                    }
                    startActivity(intent)
                }

            }
        }
    }

    private fun uploadFamily(index: Int) {
        val sharedPref = applicationContext.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val waitingJson = sharedPref.getString(getString(R.string.pref_waiting_list), "")
        if (waitingJson != "") {
            val waitingList = Gson().fromJson(waitingJson, WaitingList::class.java)
            val family = waitingList.familyList[index]

            val familyJson = Gson().toJson(family)
            Log.d("familyJson", familyJson)

            val params = listOf(
                    Pair("t", GlobalValue.apiToken),
                    Pair("task", "savesurvey"),
                    Pair("family", familyJson)
            )
            Fuel.post("mobile.php", params)
                    .also { println(it) }
                    .responseString { _, _, result ->
                        when (result) {
                            is Result.Success -> {
                                val (responseStr, _) = result
                                Log.d("UploadFamily", responseStr)

                                val ab = AlertDialog.Builder(this@WaitingUploadActivity, R.style.AlertDialogTheme)
                                ab.setTitle("แจ้งเตือน")
                                ab.setMessage(responseStr)
                                ab.show()

                            }

                        }
                    }
        }
    }

    private fun deleteFamily(index: Int) {
        val sharedPref = applicationContext.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        waitingList.familyList.removeAt(index)
        val waitingJson = Gson().toJson(waitingList)
        sharedPref.edit().apply {
            putString(getString(R.string.pref_waiting_list), waitingJson)
            apply()
        }
        listViewWaiting.adapter!!.notifyDataSetChanged()
    }

}