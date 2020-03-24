package com.cdd.smartsurvey.http.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class RegisterResponse(
        val confirmid: String,
        val message: String,
        val status: String
) {
    class Deserializer : ResponseDeserializable<RegisterResponse> {
        override fun deserialize(content: String): RegisterResponse? = Gson().fromJson(content, RegisterResponse::class.java)
    }
}