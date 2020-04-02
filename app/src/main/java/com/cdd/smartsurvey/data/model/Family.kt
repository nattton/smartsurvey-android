package com.cdd.smartsurvey.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WaitingList(
        var familyList: ArrayList<Family> = ArrayList()
) : Parcelable

@Parcelize
data class Family(
        var community: String = "",
        var prefix: String = "",
        var fname: String = "",
        var lname: String = "",
        var idcard: String = "",
        var gender: String = "",
        var birthdate: String = "",
        var hname: String = "",
        var hid: String = "",
        var hnum: String = "",
        var haddr: String = "",
        var htumbon: String = "",
        var hamphur: String = "",
        var hprovince: String = "",
        var harea: String = "",
        var hlive: String = "",
        var hjob: String = "",
        var hmember: ArrayList<Member> = ArrayList(),
        var answer: HashMap<String, String> = HashMap()
) : Parcelable

