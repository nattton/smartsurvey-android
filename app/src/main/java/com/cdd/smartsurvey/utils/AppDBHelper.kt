package com.cdd.smartsurvey.utils

import android.content.Context
import com.cdd.smartsurvey.GlobalValue
import com.cdd.smartsurvey.R
import com.cdd.smartsurvey.http.model.Community
import com.cdd.smartsurvey.http.model.Family
import com.cdd.smartsurvey.http.model.WaitingList
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.result.Result
import com.google.gson.Gson

class AppDBHelper(var context: Context) {

    fun loadCommunityList(callBack: (Array<Community>?) -> Unit) {
        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val userTokenKey = sharedPref.getString(context.getString(R.string.pref_user_token), "")
        val userKey = sharedPref.getString(context.getString(R.string.pref_user), "")

        val communityList: Array<Community>? = loadInAppCommunityList()
        if (communityList != null) {
            return callBack(communityList)
        } else {
            FuelManager.instance.basePath = GlobalValue.apiUrl
            Fuel.get("/mobile.php?u=${userKey}&t=${GlobalValue.apiToken}&task=getcommunity&token=${userTokenKey}")
                    .responseObject(Community.Deserializer()) { _, _, result ->
                        when (result) {
                            is Result.Success -> {
                                val (cList, _) = result
                                callBack(cList)
                                saveCommunityList(cList)
                            }
                            is Result.Failure -> {
                                callBack(null)
                            }

                        }
                    }
        }
    }

    private fun loadInAppCommunityList(): Array<Community>? {
        var communityList: Array<Community>? = null
        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val communityListJson = sharedPref.getString(context.getString(R.string.pref_community_list), "")
        if (communityListJson != "") {
            communityList = Gson().fromJson(communityListJson, Array<Community>::class.java)
        }

        return communityList
    }

    fun saveCommunityList(communityList: Array<Community>?): Boolean {
        if (communityList == null)
            return false

        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val communityListJson = Gson().toJson(communityList)
        sharedPref.edit().apply {
            putString(context.getString(R.string.pref_community_list), communityListJson)
            apply()
        }
        return true
    }

    fun loadFamily(index: Int): Family {
        var waitingList = WaitingList()
        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val waitingJson = sharedPref.getString(context.getString(R.string.pref_waiting_list), "")
        if (waitingJson != "") {
            waitingList = Gson().fromJson(waitingJson, WaitingList::class.java)
        }

        return waitingList.familyList[index]
    }

    fun saveFamily(family: Family, familyIndex: Int) {
        var waitingList = WaitingList()
        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        var waitingJson = sharedPref.getString(context.getString(R.string.pref_waiting_list), "")
        if (waitingJson != "") {
            waitingList = Gson().fromJson(waitingJson, WaitingList::class.java)
        }

        waitingList.familyList[familyIndex] = family

        waitingJson = Gson().toJson(waitingList)
        sharedPref.edit().apply {
            putString(context.getString(R.string.pref_waiting_list), waitingJson)
            apply()
        }
    }

}