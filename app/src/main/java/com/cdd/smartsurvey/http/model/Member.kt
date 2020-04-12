package com.cdd.smartsurvey.http.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Member(
        var prefix: String = "",
        var firstname: String = "",
        var lastname: String = "",
        var gender: String = "",
        var idcard: String = "",
        var birthdate: String = "",
        var jobname: String = "",
        var education: String = "",
        var religion: String = "",
        var relation: String = "",
        var health: String = "",
        var ability: String = "",
        var informant: String = "",
        var welfare_card: String = ""
) : Parcelable