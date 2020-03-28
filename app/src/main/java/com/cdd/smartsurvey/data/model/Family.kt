package com.cdd.smartsurvey.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UploadList(
        var familyList: ArrayList<Family>
) : Parcelable

@Parcelize
data class Family(
        var community: String,
        var prefix: String,
        var fname: String,
        var lname: String,
        var idcard: String,
        var gender: String,
        var birthdate: String,
        var hname: String,
        var hid: String,
        var hnum: String,
        var haddr: String,
        var htumbon: String,
        var hamphur: String,
        var hprovince: String,
        var harea: String,
        var hlive: String,
        var hjob: String,
        var hmember: ArrayList<Member>,
        var answer: Answer
) : Parcelable

@Parcelize
data class Member(
        var prefix: String,
        var firstname: String,
        var lastname: String,
        var gender: String,
        var idcard: String,
        var birthdate: String,
        var jobname: String,
        var education: String,
        var religion: String,
        var relation: String,
        var health: String
) : Parcelable

@Parcelize
data class Answer(
        var survey1: String,
        var survey2: String,
        var survey3: String
) : Parcelable
