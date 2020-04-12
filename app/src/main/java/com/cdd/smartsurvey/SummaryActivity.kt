package com.cdd.smartsurvey

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.cdd.smartsurvey.http.model.Family
import com.cdd.smartsurvey.http.model.WaitingList
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_summary.*

class SummaryActivity : AppCompatActivity() {
    lateinit var family: Family
    var familyIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)
        familyIndex = intent.getIntExtra(GlobalValue.EXTRA_FAMILY_INDEX, 0)
        loadFamily(familyIndex)

        btnNext.setOnClickListener {
            val intent = Intent(this, AcceptSurveyActivity::class.java).apply {
                putExtra(GlobalValue.EXTRA_FAMILY_INDEX, familyIndex)
            }
            startActivity(intent)
        }
        var sumResult: String

        if (family.answer["1121"].equals("1")) {
            textPass1.pass()
            sumResult = "1"
        } else {
            sumResult = "0"
        }

        sumResult = if (family.answer["1241"].equals("1")) {
            textPass2.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["1321"].equals("1")) {
            textPass3.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["1411"].equals("1") &&
                family.answer["1421"].equals("1") &&
                family.answer["1431"].equals("1") &&
                family.answer["1441"].equals("1")) {
            textPass4.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["1511"].equals("1") &&
                family.answer["1521"].equals("1") &&
                family.answer["1531"].equals("1") &&
                family.answer["1451"].equals("1")) {
            textPass5.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        val arraySecure = arrayListOf("1611", "1612", "1613", "1614", "1615")
        var sumSecure = 0
        for (ansId in arraySecure) {
            val ans: String = family.answer[ansId].toString()
            if (ans != "") {
                val sAns = ans.split(",")
                if (sAns.count() > 1) {
                    sumSecure += sAns[1].toIntOrDefault()
                }
            }
        }

        sumResult = if (sumSecure < (1 + family.hmember.count())) {
            textPass6.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["1711"].equals("1")) {
            textPass7.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["2811"].equals("1") &&
                family.answer["2821"].equals("1")) {
            textPass8.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["2911"].equals("1")) {
            textPass9.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["21011"].equals("1")) {
            textPass10.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["21111"].equals("1") &&
                family.answer["21121"].equals("1") &&
                family.answer["21131"].equals("1") &&
                family.answer["21141"].equals("1") &&
                family.answer["21151"].equals("1") &&
                family.answer["21152"].equals("1") &&
                family.answer["21153"].equals("1") &&
                family.answer["21154"].equals("1") &&
                family.answer["21171"].equals("1") &&
                family.answer["21181"].equals("1")) {
            textPass11.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["21211"].equals("0") &&
                family.answer["21221"].equals("0") &&
                family.answer["21231"].equals("0") &&
                family.answer["21241"].equals("0") &&
                family.answer["21251"].equals("0") &&
                family.answer["21261"].equals("0")) {
            textPass12.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["21311"].equals("1") &&
                family.answer["21313"].equals("1") &&
                family.answer["21313"].equals("1") &&
                family.answer["21314"].equals("1")) {
            textPass13.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["21411"].equals("0") &&
                family.answer["21421"].equals("0") &&
                family.answer["21431"].equals("0") &&
                family.answer["21451"].equals("0") &&
                family.answer["214661"].equals("0")) {
            textPass14.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["31521"].equals("1")) {
            textPass15.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["31621"].equals("1")) {
            textPass16.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["31721"].equals("1")) {
            textPass17.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["31821"].equals("1")) {
            textPass18.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["31921"].equals("1")) {
            textPass19.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["42021"].equals("1")) {
            textPass20.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["42121"].equals("1")) {
            textPass21.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        textPass22.pass().text = family.answer["42216"]
        sumResult = sumResult + "," + family.answer["42216"]
        textPass23.pass().text = family.answer["42321"]
        sumResult = sumResult + "," + family.answer["42321"]

        sumResult = if (family.answer["52411"].equals("1")) {
            textPass24.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["52511"].equals("1")) {
            textPass25.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["52611"].equals("1")) {
            textPass26.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["52721"].equals("1")) {
            textPass27.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["52831"].equals("1")) {
            textPass28.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["52921"].equals("1")) {
            textPass29.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["5301"].equals("1")) {
            textPass30.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        sumResult = if (family.answer["53111"].equals("1") &&
                family.answer["53112"].equals("1") &&
                family.answer["53113"].equals("1") &&
                family.answer["53114"].equals("1") &&
                family.answer["53121"].equals("1") &&
                family.answer["53122"].equals("1")) {
            textPass31.pass()
            "$sumResult,1"
        } else {
            "$sumResult,0"
        }

        family.answer["summary"] = sumResult
        saveData()
    }

    private fun TextView.pass(): TextView {
        this.setText(R.string.pass)
        this.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorPass))
        return this
    }

    private fun TextView.notPass() {
        this.setText(R.string.not_pass)
        this.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorNotPass))
    }

    fun String?.toIntOrDefault(default: Int = 0): Int {
        return this?.toIntOrNull() ?: default
    }

    private fun loadFamily(index: Int) {
        var waitingList = WaitingList()
        val sharedPref = applicationContext.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val waitingJson = sharedPref.getString(getString(R.string.pref_waiting_list), "")
        if (waitingJson != "") {
            waitingList = Gson().fromJson(waitingJson, WaitingList::class.java)
        }

        family = waitingList.familyList[index]
    }

    private fun saveData() {
        var waitingList = WaitingList()
        val sharedPref = applicationContext.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        var waitingJson = sharedPref.getString(getString(R.string.pref_waiting_list), "")
        if (waitingJson != "") {
            waitingList = Gson().fromJson(waitingJson, WaitingList::class.java)
        }

        waitingList.familyList[familyIndex] = family

        waitingJson = Gson().toJson(waitingList)
        sharedPref.edit().apply {
            putString(getString(R.string.pref_waiting_list), waitingJson)
            apply()
        }
    }
}
