package com.cdd.smartsurvey.http.model

import android.os.Parcelable
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Community(
        val community_id: String,
        val community_name: String,
        val family_amount: String,
        val message: String,
        val moo: String,
        val status: String,
        val subdistrict_code: String,
        val subdistrict_name: String,
        val survey_amount: Int
) : Parcelable {
    class Deserializer : ResponseDeserializable<Array<Community>> {
        override fun deserialize(content: String): Array<Community>? = Gson().fromJson(content, Array<Community>::class.java)
    }
}